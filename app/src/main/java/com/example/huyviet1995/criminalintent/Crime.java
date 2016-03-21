package com.example.huyviet1995.criminalintent;

import android.text.format.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
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
    private String mSuspect;
    private String mSuspectPhone;
    private String mSuspectID;

    public Crime() {
        //generate unique identifier;
        this(UUID.randomUUID());
    }

    public Crime(UUID id) {
        mID = id;
        mDate = new Date();
    }
    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 {mDate = date;}


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
    public boolean isSolved() {
        return mSolved;
    }
    public String getSuspect(){
        return mSuspect;
    }
    public void setSuspect(String suspect) {
        mSuspect = suspect;
    }

    public String getSuspectID() {
        return mSuspectID;
    }

    public void setSuspectID(String suspectID) {
        mSuspectID = suspectID;
    }
    public String getPhone() {
        return mSuspectPhone;
    }
    public void setPhone(String phone) {
        mSuspectPhone = phone;
    }

    
}