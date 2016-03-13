package com.example.huyviet1995.criminalintent;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.RadialGradient;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;

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
    private static String ARG_CRIME_ID = "crime_id";
    private static final String DIALOG_DATE = "DialogDate";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_MESSAGE = 1;


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
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime, menu);
    }


    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_item_delete:
                FragmentManager fragmentManager = getFragmentManager();
                DeleteMessageFragment deleteMessageFragment = new DeleteMessageFragment();
                deleteMessageFragment.setTargetFragment(CrimeFragment.this, REQUEST_MESSAGE);
                deleteMessageFragment.show(fragmentManager,"DELETE_MESSAGE");
            return true;
            /*If the user press the up button when there is no title, a dialog will appear asking for a title*/
            case android.R.id.home:
                if (mTitleField.getText().toString().trim().isEmpty()) {
                    FragmentManager fragmentManager1 = getFragmentManager();
                    EmptyTitleNoticeFragment dialog = new EmptyTitleNoticeFragment();
                    dialog.show(fragmentManager1, "EMPTY_TITLE");
                    return true;
                }


            default:
                return super.onOptionsItemSelected(item);
        }

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
        if (requestCode == REQUEST_MESSAGE) {
            mIsChosen = data.getBooleanExtra(DeleteMessageFragment.CHOICE,false);
            if (mIsChosen) {
                CrimeLab.get(getActivity()).removeCrime(mCrime);
                getActivity().finish();
            }

        }
    }
    private void updateDate() {
        mDateButton.setText(mCrime.getDate().toString());
    }
}
