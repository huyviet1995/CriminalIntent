package com.example.huyviet1995.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

/**
 * Created by huyviet1995 on 3/9/16.
 */
public class CrimePagerActivity  extends AppCompatActivity{

    private ViewPager mViewPager;
    private List<Crime> mCrimes;
    private static final String EXTRA_CRIME_ID=  "com.example.huyviet1995.criminalIntent.crime_id";

    public static Intent newIntent(Context packageContext, UUID crimeId) {
        Intent intent = new Intent (packageContext, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

   @Override
    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);
        mViewPager = (ViewPager) findViewById(R.id.activity_crime_pager_view_pager);
        mCrimes = CrimeLab.get(this).getCrimes();
       UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);
       FragmentManager fragmentManager = getSupportFragmentManager();
       mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
           @Override
           public Fragment getItem(int position) {
               Crime crime = mCrimes.get(position);
               return CrimeFragment.newInstance(crime.getID());
           }

           @Override
           public int getCount() {
               return mCrimes.size();
           }
       });
       for (int i = 0; i<mCrimes.size();i++) {
           if (mCrimes.get(i).getID().equals(crimeId)) {
               mViewPager.setCurrentItem(i);
               break;
           }
       }

    }
}