package com.codepath.apps.restclienttemplate.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TweeterApp;
import com.codepath.apps.restclienttemplate.TweeterClient;
import com.codepath.apps.restclienttemplate.adapters.ProfilePagerAdapter;
import com.codepath.apps.restclienttemplate.fragments.UserTimelineFragment;
import com.codepath.apps.restclienttemplate.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import cz.msebera.android.httpclient.Header;

public class ProfileActivity extends AppCompatActivity {
    TweeterClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_layout);

        String screenName = getIntent().getStringExtra("screen_name");

        UserTimelineFragment userTimelineFragment = UserTimelineFragment.newInstance(screenName);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.flContainer, userTimelineFragment);
        ft.commit();

        client = TweeterApp.getRestClient(getBaseContext());
        JsonHttpResponseHandler handler = new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //deserialize user object
                try {
                    User user = User.fromJSON(response);
                    //populate the user headline
                    populateUserHeadline(user);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        };
        // If user click on another profile or his own profile
        if (screenName != null) {
            client.getUserInfo(screenName, handler);
        } else {
            client.getMyInfo(handler);

        }

    }

    public void populateUserHeadline(User user) {
        //todo add banner
        TextView tvName = findViewById(R.id.tvNameDetail);
        TextView tvScreenName = findViewById(R.id.tvScreenName);
        TextView tvTagline = findViewById(R.id.tvTagline);
        TextView tvFollowers = findViewById(R.id.tvNumberFollowers);
        TextView tvFollowing = findViewById(R.id.tvNumberFollowing);

        ImageView ivProfileImage = findViewById(R.id.ivProfileImageDetail);
        //Load image with Glide
        Glide.with(this)
                .load(user.profileImageUrl)
                .into(ivProfileImage);

        ImageView ivBannerImage = findViewById(R.id.ivBanner);
        //Load image with Glide
        Glide.with(this)
                .load(user.profileBannerUrl)
                .into(ivBannerImage);

        tvName.setText(user.name);
        tvScreenName.setText("@" + user.screenName);
        tvTagline.setText(user.tagLine);
        tvFollowers.setText(String.valueOf(user.followersCount));
        tvFollowing.setText(String.valueOf(user.followingCount));



    }
}