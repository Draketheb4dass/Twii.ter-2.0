package com.codepath.apps.restclienttemplate.fragments;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.codepath.apps.restclienttemplate.activities.TimelineActivity;

/**
 * Created by drake on 7/26/18
 */
public class TweetsPagerAdapter extends FragmentPagerAdapter {
    private String tabTitle[] = new String[] {"Home", "Mentions" };
    private Context context;

    public TweetsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }
    //Return number of fragment
    @Override
    public int getCount() {
        return 2;
    }

    //Return fragment to use depending on position
    @Override
    public Fragment getItem(int position) {
        if(position == 0 ){
            return new HomeTimelineFragment();
        }else if (position == 1) {
            return new MentionsTimelineFragment();
        }else {
            return null;
        }

    }

    //return Title
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitle[position];
    }
}
