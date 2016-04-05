package com.example.huyviet1995.criminalintent;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.RadialGradient;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ShareCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;
import java.net.URI;
import java.util.Date;
import java.util.UUID;

/**
 * Created by huyviet1995 on 2/25/16.
 */
public class CrimeFragment extends Fragment {

    Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;
    private boolean mIsChosen;
    private Button mReportButton;
    private Button mSuspectButton;
    private Button mCallSuspect;
    private ImageButton mPhotoButton;
    private File mPhotoFile;
    private ImageView mPhotoView;
    private boolean hasPhoneNumber;


    private static String ARG_CRIME_ID = "crime_id";
    private static final String DIALOG_DATE = "DialogDate";
    private static final String DIALOG_ZOOM ="DialogZoom";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_MESSAGE = 1;
    private static final int REQUEST_CONTACT =  2;
    private static final int REQUEST_CALL = 3;
    private static final int REQUEST_PHOTO=4;


    public static CrimeFragment newInstance (UUID crimeId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, crimeId);
        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID crimeId = (UUID)getArguments().getSerializable(ARG_CRIME_ID);
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
        setHasOptionsMenu(true);
        mPhotoFile = CrimeLab.get(getActivity()).getPhotoFile(mCrime);
    }
    @Override
    public void onPause() {
        super.onPause();
        CrimeLab.get(getActivity()).updateCrime(mCrime);
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_crime,container, false);
        mTitleField =(EditText)v.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle());

        mTitleField.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }


        });
        mDateButton = (Button)v.findViewById(R.id.crime_date);
        //set the dateButton to the new format
        updateDate();

        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getDate());
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });

        mSolvedCheckBox = (CheckBox)v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved(isChecked);
            }
        });
        //getActivity().onBackPressed();
        mReportButton = (Button)v.findViewById(R.id.crime_report);
        mReportButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
        /*Challenge 15:ShareCompat*/
                ShareCompat.IntentBuilder.from(getActivity())
                        .setType("text/plain")
                        .setText(getCrimeReport())
                        .setSubject(getString(R.string.crime_report_suspect))
                        .setChooserTitle(getString(R.string.send_report))
                        .startChooser();
            }
        });
        final Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        mSuspectButton = (Button)v.findViewById(R.id.crime_suspect);
        mSuspectButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startActivityForResult(pickContact,REQUEST_CONTACT);
            }
        });
        if (mCrime.getSuspect()!=null) {
            mSuspectButton.setText(mCrime.getSuspect());
        }
        mCallSuspect = (Button)v.findViewById(R.id.call_suspect);
        mCallSuspect.setEnabled(false);
        mCallSuspect.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_DIAL,ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                Uri number = Uri.parse("tel:"+mCrime.getPhone());
                i.setData(number);
                startActivity(i);
            }
        });
        PackageManager packageManager = getActivity().getPackageManager();
        if (packageManager.resolveActivity(pickContact,PackageManager.MATCH_DEFAULT_ONLY)==null) {
            mSuspectButton.setEnabled(false);
        }
        mPhotoButton = (ImageButton) v.findViewById(R.id.crime_camera);
        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        boolean canTakePhoto = mPhotoFile != null && captureImage.resolveActivity(packageManager) !=null;
        mPhotoButton.setEnabled(canTakePhoto);
        if (canTakePhoto) {
            Uri uri = Uri.fromFile(mPhotoFile);
            captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);

        }
        mPhotoButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startActivityForResult(captureImage,REQUEST_PHOTO);
            }
        });
        mPhotoView = (ImageView) v.findViewById(R.id.crime_photo);
        ViewTreeObserver observer = mPhotoView.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                updatePhotoView();
            }
        });
        updatePhotoView();
        mPhotoView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mPhotoView != null || mPhotoFile.exists()) {
                    FragmentManager fragmentManager = getFragmentManager();
                    ZoomImageDialog zoomImageDialog = ZoomImageDialog.newInstance(mPhotoFile);
                    zoomImageDialog.show(fragmentManager, DIALOG_ZOOM);
                }
            }
        });

        return v;
    }

    @Override
    /*When there is no title, pressing the back button will trigger a dialog notice*/
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    if (mTitleField.getText().toString().trim().isEmpty()) {
                        emptyTitleDialogShow();
                        return true;
                    }

                }
                return false;
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        /*Challenge 1: Inflate the new fragment_crime menu*/
        inflater.inflate(R.menu.fragment_crime, menu);
    }


    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            /*Extra Challenge: if user presses the delete button, a delete message will show up*/
            case R.id.menu_item_delete:
                FragmentManager fragmentManager = getFragmentManager();
                DeleteMessageFragment deleteMessageFragment = new DeleteMessageFragment();
                deleteMessageFragment.setTargetFragment(CrimeFragment.this, REQUEST_MESSAGE);
                deleteMessageFragment.show(fragmentManager,"DELETE_MESSAGE");
            return true;
            /*If the user press the up button when there is no title, a dialog will appear asking for a title*/
            case android.R.id.home:
                if (mTitleField.getText().toString().trim().isEmpty()) {
                    emptyTitleDialogShow();
                    return true;
                }

            default:
                return super.onOptionsItemSelected(item);
        }

    }


    private void emptyTitleDialogShow() {
        FragmentManager fragmentManager = getFragmentManager();
        EmptyTitleNoticeFragment dialog = new EmptyTitleNoticeFragment();
        dialog.show(fragmentManager, "EMPTY_TITLE");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mCrime.setDate(date);
            updateDate();
        }
        /*Extra challenge: if mIsChosen is true (user presses okay) then finish the activity*/
        if (requestCode == REQUEST_MESSAGE) {
            mIsChosen = data.getBooleanExtra(DeleteMessageFragment.CHOICE,false);
            if (mIsChosen) {
                /*Challenge 1: remove the crime from crime lab
                * and finish the activity when the delete button is pressed*/
                CrimeLab.get(getActivity()).deleteCrime(mCrime);
                getActivity().finish();
            }
        }
        else if (requestCode == REQUEST_CONTACT && data != null) {
            Uri contactURI = data.getData();
            String[] queryFields = new String[]{
                    ContactsContract.Contacts.DISPLAY_NAME /*ContactsContract.Contacts._ID, ContactsContract.Contacts.HAS_PHONE_NUMBER
            */};
            Cursor c = getActivity().getContentResolver().query(contactURI, queryFields, null, null, null);
            try {
                if (c.getCount() == 0) {
                    c.close();
                    return;
                }
                c.moveToFirst();
                String suspect = c.getString(0);
                mCrime.setSuspect(suspect);
                mSuspectButton.setText(suspect);
                /*get and set the suspect ID*/
                //String suspectID = c.getString(1);
                //mCrime.setSuspectID(suspectID);
                /*check if the contact has the phonenumber*/
                //if (Integer.parseInt(c.getString(2)) > 0) hasPhoneNumber = true;
                //else hasPhoneNumber = false;
                //mCallSuspect.setText(c.getString(2));
                /*if the contact has phone number, then query the phone number*/
                /*Challenge chapter 15: another implicit intent
                * I have a problem here, I think I am doing it right but whenever it runs, it gives
                * me security error and tells me to add permission
                * I have added the permission in manifest file but to no avail. Please help me*/

                /*if (hasPhoneNumber) {
                    String[] phoneQuery = new String[]{
                            ContactsContract.CommonDataKinds.Phone.NUMBER
                    };
                    Cursor phoneCursor = getActivity().getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            phoneQuery,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = "+suspectID ,
                            null,
                            null);
                    try {
                        if (phoneCursor.getCount() == 0) return;
                        phoneCursor.moveToFirst();
                        String suspectPhoneNumber = phoneCursor.getString(0);
                        mCallSuspect.setText(suspectPhoneNumber);
                        mCallSuspect.setEnabled(true);
                    }
                    finally {
                        phoneCursor.close();
                    }
                }*/
            } finally {
                c.close();
            }

        }
        else if (requestCode == REQUEST_PHOTO) {
            updatePhotoView();
        }
    }
    private void updateDate() {
        mDateButton.setText(mCrime.getDate().toString());
    }

    private String getCrimeReport() {
        String solvedString = null;
        if (mCrime.isSolved())
            solvedString = getString(R.string.crime_report_solved);
        else
        {
            solvedString=getString(R.string.crime_report_unsolved);
        }
        String dateFormat = "EEE,MMM dd";
        String dateString= DateFormat.format(dateFormat,mCrime.getDate()).toString();

        String suspect = mCrime.getSuspect();
        if (suspect == null) {
            suspect = getString(R.string.crime_report_no_suspect);
        } else {
            suspect = getString(R.string.crime_report_suspect,suspect);
        }
        String report = getString(R.string.crime_report,mCrime.getTitle(),dateString,solvedString,suspect);
        return report;

    }
    private void updatePhotoView() {
        Bitmap bitmap;
        if (mPhotoView == null || !mPhotoFile.exists()) {
            mPhotoView.setImageDrawable(null);
            Log.d("CREATION", "There is no photo available");

        }
        else if (mPhotoView == null) {
            bitmap = PictureUtils.getScaledBitmap(mPhotoFile.getPath(),getActivity());
            mPhotoView.setImageBitmap(bitmap);
        }
        else {
            bitmap = PictureUtils.getScaledBitMap(mPhotoFile.getPath(),mPhotoView.getWidth(),mPhotoView.getHeight());
            mPhotoView.setImageBitmap(bitmap);
        }
    }
}
