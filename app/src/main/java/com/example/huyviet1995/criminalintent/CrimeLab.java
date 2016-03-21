package com.example.huyviet1995.criminalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.huyviet1995.criminalintent.CrimeDbSchema.CrimeTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by huyviet1995 on 3/1/16.
 */
public class CrimeLab {
    private static CrimeLab sCrimeLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;
    public static CrimeLab get (Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);

        }
        return sCrimeLab;
    }
    public void addCrime(Crime c) {
        ContentValues values = getContentValues(c);
        mDatabase.insert(CrimeTable.NAME, null, values);
    }
    /*Challenge chapter 14: delete crime*/
    public void deleteCrime(Crime c) {
        String uuidString = c.getID().toString();
        ContentValues values = getContentValues(c);
        mDatabase.delete(CrimeTable.NAME,CrimeTable.Cols.UUID+" = ? ", new String[]{uuidString});
    }

    private CrimeLab (Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new CrimeBaseHelper(mContext).getWritableDatabase();
    }
    public List<Crime> getCrimes() {
        List<Crime> crimes = new ArrayList<>();
        CrimeCursorWrapper cursor = queryCrimes(null,null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                crimes.add(cursor.getCrime());
                cursor.moveToNext();
            }

        } finally {
            cursor.close();
        }
        return crimes;
    }
    public Crime getCrime (UUID id) {
        CrimeCursorWrapper cursor = queryCrimes(
        CrimeTable.Cols.UUID + "= ?",
                new String[]{id.toString()}
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getCrime();
        } finally {
            cursor.close();
        }

    }
    public void updateCrime(Crime crime) {
        String uuidString = crime.getID().toString();
        ContentValues values = getContentValues(crime);

        mDatabase.update(CrimeTable.NAME, values, CrimeTable.Cols.UUID +"= ?", new String[]{uuidString});
    }

    private static ContentValues getContentValues(Crime crime) {
        ContentValues values = new ContentValues();
        values.put(CrimeTable.Cols.UUID, crime.getID().toString());
        values.put(CrimeTable.Cols.TITLE, crime.getTitle());
        values.put(CrimeTable.Cols.DATE, crime.getDate().getTime());
        values.put(CrimeTable.Cols.SOLVED, crime.isSolved() ? 1 : 0);
        values.put(CrimeTable.Cols.SUSPECT,crime.getSuspect());
        values.put(CrimeTable.Cols.PHONE, crime.getPhone());
        values.put(CrimeTable.Cols.SUSPECTID, crime.getSuspectID());
        return values;
    }
    private CrimeCursorWrapper queryCrimes (String whereClause, String[]  whereArgs) {
        Cursor cursor = mDatabase.query(
                CrimeTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new CrimeCursorWrapper(cursor);
    }
}
