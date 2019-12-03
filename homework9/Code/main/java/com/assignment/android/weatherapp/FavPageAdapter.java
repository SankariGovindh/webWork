package com.assignment.android.weatherapp;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import java.util.ArrayList;

public class FavPageAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> mFragments = new ArrayList<>();
        private ArrayList<String> placeList = new ArrayList<>();
        private long baseId = 0;

        public FavPageAdapter(FragmentManager fm)
        {
            super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        //method to add the fragments - gets called in the mainActivity
        public void addFragment(Fragment fragment, String title)
        {
            Log.d("Logging the city in the fragment:",title);
            mFragments.add(fragment);
            placeList.add(title);
        }

        //method to remove the fragments - gets called in the mainActivity
        public void removeFragment(int position){
            mFragments.remove(position);
            placeList.remove(position);
        }

        @Override
        public Fragment getItem(int position)
        {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            Log.d("logging the count size:",String.valueOf(mFragments.size()));
            return mFragments.size();
        }

       /* @Override
        public long getItemId(int position) {
            // give an ID different from position when position has been changed
            return baseId + position;
        }

        *//**
         * Notify that the position of a fragment has been changed.
         * Create a new ID for each position to force recreation of the fragment
         * @param n number of items which have been changed
         *//*

        public void notifyChangeInPosition(int n) {
            // shift the ID returned by getItemId outside the range of all previous fragments
            baseId += getCount() + n;
        }*/

}
