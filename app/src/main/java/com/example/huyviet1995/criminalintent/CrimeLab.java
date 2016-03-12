package com.example.huyviet1995.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by huyviet1995 on 3/1/16.
 */
public class CrimeLab {
    private static CrimeLab sCrimeLab;
    private List<Crime> mCrimes;
    public static CrimeLab get (Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);

        }
        return sCrimeLab;
    }

    public void addCrime(Crime c) {
        mCrimes.add(c);
    }

    public void removeCrime(Crime c) {
        mCrimes.remove(c);
    }

    private CrimeLab (Context context) {
        mCrimes = new ArrayList<>();
    }
    public List<Crime> getCrimes() {
        return mCrimes;
    }
    public Crime getCrime (UUID id)  {
        for (Crime crime:mCrimes) {
            if (crime.getID().equals(id)) {
                return crime;
            }
        }
        return null;
    }
}
