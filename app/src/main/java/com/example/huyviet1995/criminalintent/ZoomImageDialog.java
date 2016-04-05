package com.example.huyviet1995.criminalintent;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import java.io.File;

/**
 * Created by huyviet1995 on 4/4/16.
 */
public class ZoomImageDialog extends DialogFragment {
    private static final String ARGS_ZOOM = "argsZoom";
    private File mPhotoFile;
    private ImageView mPhotoView;
    private Bitmap bitmap;
    public static ZoomImageDialog newInstance(File file) {
        Bundle args = new Bundle();
        args.putSerializable(ARGS_ZOOM, file);
        ZoomImageDialog fragment = new ZoomImageDialog();
        fragment.setArguments(args);
        return fragment;
    }
    public Dialog OnCreateDialog(Bundle savedInstanceState) {
        mPhotoFile = (File) getArguments().getSerializable(ARGS_ZOOM);

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.zoom_image_view, null);
        mPhotoView = (ImageView) v.findViewById(R.id.zoomImage);
        bitmap = PictureUtils.getScaledBitmap(mPhotoFile.getPath(),getActivity());
        mPhotoView.setImageBitmap(bitmap);
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("Zoom Image")
                .create();
    }

}
