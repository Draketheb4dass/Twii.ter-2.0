package com.codepath.apps.restclienttemplate.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.codepath.apps.restclienttemplate.TweeterApp;
import com.codepath.apps.restclienttemplate.TweeterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by drake on 7/26/18
 */
public class UserTimelineFragment extends TweetsListFragment {
    TweeterClient client;
    public static UserTimelineFragment newInstance(String screenName){
        UserTimelineFragment userTimelineFragment =new UserTimelineFragment();
        Bundle args = new Bundle();
        args.putString("screen_name", screenName);
        userTimelineFragment.setArguments(args);
        return userTimelineFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TweeterApp.getRestClient(getContext());
        populateTimeline();

    }

    public void populateTimeline() {
        //come from the activity
        String screenName = getArguments().getString("screen_name");
        client.getUserTimeline(screenName, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("TweeterClient", response.toString());
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                //Log.d("TweeterClient", response.toString());
                //iterate through the JSON array
                // for each entry, deserialize JSON object
                addItems(response);


            }

            @Override
            public void onFailure(int statusCode,
                                  Header[] headers,
                                  String responseString,
                                  Throwable throwable) {
                Log.d("TweeterClient", responseString);
                throwable.printStackTrace();
            }

            @Override
            public void onFailure(int statusCode,
                                  Header[] headers,
                                  Throwable throwable,
                                  JSONObject errorResponse) {
                Log.d("TweeterClient", errorResponse.toString());
            }

            @Override
            public void onFailure(int statusCode,
                                  Header[] headers,
                                  Throwable throwable,
                                  JSONArray errorResponse) {
                Log.d("TweeterClient", errorResponse.toString());
            }
        });


    }
}
