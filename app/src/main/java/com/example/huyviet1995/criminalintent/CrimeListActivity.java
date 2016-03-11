package com.example.huyviet1995.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by huyviet1995 on 3/1/16.
 */
public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();

    }
}
