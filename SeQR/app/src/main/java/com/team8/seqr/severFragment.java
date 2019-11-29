package com.team8.seqr;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.Toast;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import androidx.viewpager.widget.ViewPager;

import android.app.Fragment.*;
import android.app.FragmentController.*;


import com.google.android.material.tabs.TabLayout;

public class severFragment extends Fragment {
    NavController navController;

    private String secretKey;

    private TabLayout mTablayout;
    private ViewPager viewPager;
    private Context mContext;
    private TabPagerAdapter mContentPagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_sever, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        secretKey = getArguments().getString("secretKey");
        Toast.makeText(getActivity(), "Secret Key: "+ secretKey, Toast.LENGTH_LONG).show();

        viewPager = (ViewPager) getView().findViewById(R.id.vp_main);
        mTablayout = (TabLayout) getView().findViewById(R.id.tb_main);
        mContext = getActivity().getApplicationContext();
        
        mTablayout.addTab(mTablayout.newTab().setText("Tab1"));
        mTablayout.addTab(mTablayout.newTab().setText("Tab2"));
        mTablayout.addTab(mTablayout.newTab().setText("Tab3"));

        mContentPagerAdapter = new TabPagerAdapter(getActivity().getSupportFragmentManager(), mTablayout.getTabCount());

        viewPager.setAdapter(mContentPagerAdapter);

        mTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

}
