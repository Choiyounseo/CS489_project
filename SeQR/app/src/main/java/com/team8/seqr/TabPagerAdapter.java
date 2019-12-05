package com.team8.seqr;

import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import android.os.Bundle;

import com.team8.seqr.createInfo;
import com.team8.seqr.createUrl;

public class TabPagerAdapter  extends FragmentStatePagerAdapter {

    // Count number of tabs
    private String[] tabTitles = new String[]{"Information", "URL", "File"};
    private int tabCount;
    public FragmentManager fragmentManager;
    private String secretKey;


    public TabPagerAdapter(FragmentManager fm, int tabCount, String secretKey) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        this.tabCount = tabCount;
        this.secretKey = secretKey;
    }

    // need to override tab title : to use ViewPager with TabLayout
    @Override
    public CharSequence getPageTitle(int position) {
      return tabTitles[position];
    }

    @Override
    public Fragment getItem(int position) {

        // Returning the current tabs
        switch (position) {
            case 0:
                createInfo createinfo = new createInfo();
                Bundle args = new Bundle();
                // Our object is just an string(secretKey) :-P
                args.putString(createInfo.secretKey, secretKey);
                createinfo.setArguments(args);
                return createinfo;
            case 1:
                createUrl createurl = new createUrl();
                return createurl;
            case 2:
                createInfo createinfo3 = new createInfo();
                return createinfo3;
            default:
                return null;

        }


    }

    @Override
    public int getCount() {
        return tabCount;
    }


}
