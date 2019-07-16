package com.example.project;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project.activity_fragments_class.Fragments.ExtraRewardListFragment;
import com.example.project.activity_fragments_class.Fragments.NormalRewardListFragment;


public class MainActivity extends Fragment {

    private View rootView;
    private ViewPager viewPager;
    private SectionsPagerAdapter sectionsPagerAdapter;
    private TabLayout tabs;



    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_main, container, false);
        sectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        viewPager = rootView.findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        tabs = rootView.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        //tabs.setTabTextColors(-9999,-1);

        return rootView;
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = new ExtraRewardListFragment();
                    break;
                case 1:
                    fragment = new NormalRewardListFragment();
                    break;
            }
            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Fragment 1";
                case 1:
                    return "Fragment 2";
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}