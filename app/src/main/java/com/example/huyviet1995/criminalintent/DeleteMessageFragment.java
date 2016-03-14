package com.example.huyviet1995.criminalintent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * Created by huyviet1995 on 3/12/16.
 */
/*When user presses on the */
public class DeleteMessageFragment extends DialogFragment {
    public static final String CHOICE = "choice";
    private boolean mIsChosen;
    @Override

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_message);
        builder.setPositiveButton(R.string.positive_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mIsChosen = true;
                sendResult(Activity.RESULT_OK, mIsChosen);
            }
        });
        builder.setNegativeButton(R.string.negative_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mIsChosen = false;
                sendResult(Activity.RESULT_OK, mIsChosen);
            }
        });
        return builder.create();
    }

    private void sendResult(int resultCode, boolean message) {
        if (getTargetFragment()== null)
            return;
        Intent intent = new Intent();
        intent.putExtra(CHOICE, mIsChosen);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }

}
