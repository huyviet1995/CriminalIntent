package com.example.huyviet1995.criminalintent;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;

/**
 * Created by huyviet1995 on 3/12/16.
 */
public class EmptyTitleNoticeFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),getTheme());
        builder.setTitle(R.string.empty_title_notice);
        builder.setPositiveButton(R.string.positive_button, null);
        builder.setCancelable(false);

        return builder.create();
    }
}
