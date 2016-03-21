package com.example.huyviet1995.criminalintent;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.huyviet1995.criminalintent.CrimeDbSchema.CrimeTable;

import java.sql.DatabaseMetaData;
import java.util.Date;
import java.util.UUID;

/**
 * Created by huyviet1995 on 3/16/16.
 */
public class CrimeCursorWrapper  extends CursorWrapper {
    public CrimeCursorWrapper(Cursor cursor) {
        super(cursor);
    }
    public Crime getCrime() {
        String uuidString = getString(getColumnIndex(CrimeTable.Cols.UUID));
        String title = getString(getColumnIndex(CrimeTable.Cols.TITLE));
        long date = getLong(getColumnIndex(CrimeTable.Cols.DATE));
        int isSolved = getInt(getColumnIndex(CrimeTable.Cols.SOLVED));
        String suspect = getString(getColumnIndex(CrimeTable.Cols.SUSPECT));
        String phone = getString(getColumnIndex(CrimeTable.Cols.PHONE));
        String suspectID = getString(getColumnIndex(CrimeTable.Cols.SUSPECTID));

        Crime crime = new Crime(UUID.fromString(uuidString));
        crime.setTitle(title);
        crime.setDate(new Date());
        crime.setSolved(isSolved != 0 );
        crime.setSuspect(suspect);
        crime.setPhone(phone);
        crime.setSuspect(suspectID);
        return crime;

    }
}
