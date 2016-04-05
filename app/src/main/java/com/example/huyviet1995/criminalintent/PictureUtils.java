package com.example.huyviet1995.criminalintent;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

/**
 * Created by huyviet1995 on 4/4/16.
 */
public class PictureUtils {
    public static Bitmap getScaledBitmap(String path, Activity activity) {
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        return getScaledBitMap(path,size.x,size.y);
    }

    public static Bitmap getScaledBitMap(String path, int destWidth, int destHeight) {
        //read the dimensions of the image on disk
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path,options);
        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;
        //figure out how much to scale down by
        int inSampleSize =1;
        if (srcHeight > destHeight || srcWidth>destWidth) {
            if (srcWidth>srcHeight) {
                inSampleSize = Math.round(srcHeight/destHeight);
            } else {
                inSampleSize = Math.round(srcWidth/destWidth);
            }
        }
        options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;
        //read in and create final bitmap
        return BitmapFactory.decodeFile(path,options);
    }
}
