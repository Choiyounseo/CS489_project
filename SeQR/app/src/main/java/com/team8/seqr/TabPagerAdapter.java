package com.team8.seqr;

import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.team8.seqr.createInfo;
import com.team8.seqr.createUrl;

public class TabPagerAdapter  extends FragmentStatePagerAdapter {

    // Count number of tabs
    private int tabCount;
    public FragmentManager fragmentManager;


    public TabPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {

        // Returning the current tabs
        switch (position) {
            case 0:
                createInfo createinfo = new createInfo();
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
