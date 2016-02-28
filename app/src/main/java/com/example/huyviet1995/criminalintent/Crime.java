package com.example.huyviet1995.criminalintent;

import android.text.format.DateFormat;

import java.util.Date;
import java.util.UUID;

/**
 * Created by huyviet1995 on 2/25/16.
 */
public class Crime {
    private UUID mID;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;
    private String[] formatString;

    public Crime() {
        //generate unique identifier;
        mID = UUID.randomUUID();
        mDate = new Date();
    }

    public CharSequence getDate() {
        CharSequence sequence = DateFormat.format("EEEE, MMM dd, yyyy.", mDate.getTime());
        return sequence;
    }

    public void setDate(Date date) {mDate = date;}


    public UUID getID() {
        return mID;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle (String Title) {
        mTitle = Title;
    }

    public void setSolved(boolean solved) {
        this.mSolved = solved;
    }
}