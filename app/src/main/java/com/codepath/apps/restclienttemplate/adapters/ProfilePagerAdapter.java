package com.codepath.apps.restclienttemplate.adapters;

/*
 * Created by drake on 7/30/18
 */

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.codepath.apps.restclienttemplate.activities.TimelineActivity;
import com.codepath.apps.restclienttemplate.fragments.HomeTimelineFragment;

/**
 * Created by drake on 7/26/18
 */
public class ProfilePagerAdapter extends FragmentPagerAdapter {
    private String tabTitle = "Timeline";
    private Context context;

    public ProfilePagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }
    //Return number of fragment
    @Override
    public int getCount() {
        return 1;
    }

    //Return fragment to use depending on position
    @Override
    public Fragment getItem(int position) {
        if(position == 0 ){
            return new HomeTimelineFragment();
        }else {
            return null;
        }

    }

    //return Title
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitle;
    }
}

