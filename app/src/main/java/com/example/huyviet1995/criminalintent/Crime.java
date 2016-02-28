package com.example.huyviet1995.criminalintent;

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

    public Crime() {
        //generate unique identifier;
        mID = UUID.randomUUID();
        mDate = new Date();
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }
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