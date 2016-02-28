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

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {mDate = date;}

    public String setFormatDate (Date date) {
        //extract the info from the date format
        formatString = date.toString().split(" ");
        String day = "Monday";
        String month= formatString[1];
        String date1 = formatString[2];
        String year = formatString[5];
        //Convert day to readable format
        switch (day) {
            case "MON": day = "Monday"; break;
            case "TUE": day = "Tuesday"; break;
            case "WED": day = "Wednesday"; break;
            case "THU": day = "Thursday"; break;
            case "FRI": day = "Friday"; break;
            case "SAT": day = "Saturday"; break;
            case "SUN": day = "Sunday"; break;
        }
        return day+", "+month+" "+date1+", "+year;
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