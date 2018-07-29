package com.codepath.apps.restclienttemplate;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
        setContentView(R.layout.activity_profile);

        String screenName = getIntent().getStringExtra("screen_name");

        //create the user fragment
        UserTimelineFragment userTimelineFragment = UserTimelineFragment.newInstance(screenName);

        //display the user timeline fragment inside the container(dynamically)
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //make change
        ft.replace(R.id.flContainer, userTimelineFragment);

        //commit
        ft.commit();

        client = TweeterApp.getRestClient(getBaseContext());
        JsonHttpResponseHandler handler = new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //deserialize user object
                try {
                    User user = User.fromJSON(response);
                    //set the title of the ActionBar based on the user info
                    Objects.requireNonNull(getSupportActionBar()).setTitle(user.screenName);
                    //populate the user headline
                    populateUserHeadline(user);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        };
        if (screenName != null) { // user clicked on a tweet, not the profile menu option
            client.getUserInfo(screenName, handler);
        } else {
            client.getMyInfo(handler);

        }

    }

    public void populateUserHeadline(User user) {
        //todo add banner
        TextView tvName = findViewById(R.id.tvNameDetail);
        TextView tvTagline = findViewById(R.id.tvTagline);
        TextView tvFollowers = findViewById(R.id.tvFollowers);
        TextView tvFollowing = findViewById(R.id.tvFollowing);

        ImageView ivProfileImage = findViewById(R.id.ivProfileImageDetail);
        tvName.setText(user.name);
        tvTagline.setText(user.tagLine);
        tvFollowers.setText(user.followersCount + " Followers");
        tvFollowing.setText(user.followingCount + " Following");
        //Load image with Glide
        Glide.with(this)
                .load(user.profileImageUrl)
                .into(ivProfileImage);


    }
}