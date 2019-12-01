package com.assignment.android.weatherapp;

import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import java.util.ArrayList;
import java.util.List;


class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> tabList = new ArrayList<>();
    private final List<String> detailList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager manager) {
        super(manager,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    //method to add the fragments - gets called in the DetailedWeatherActivity
    public void addFragment(Fragment fragment, String title) {
        tabList.add(fragment);
        detailList.add(title);
    }

    @Override
    public Fragment getItem(int position) {
        return tabList.get(position);
    }

    @Override
    public int getCount() {
        return tabList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return detailList.get(position);
    }
}
