package com.codepath.apps.restclienttemplate.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.codepath.apps.restclienttemplate.TwiiterApp;
import com.codepath.apps.restclienttemplate.TwiiterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by drake on 7/26/18
 */
public class HomeTimelineFragment extends TweetsListFragment {
    TwiiterClient client;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwiiterApp.getRestClient(getContext());
        populateTimeline();

    }

    public void populateTimeline() {
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("TwiiterClient", response.toString());
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                //Log.d("TwiiterClient", response.toString());
                //iterate through the JSON array
                // for each entry, deserialize JSON object
                addItems(response);


            }

            @Override
            public void onFailure(int statusCode,
                                  Header[] headers,
                                  String responseString,
                                  Throwable throwable) {
                Log.d("TwiiterClient", responseString);
                throwable.printStackTrace();
            }

            @Override
            public void onFailure(int statusCode,
                                  Header[] headers,
                                  Throwable throwable,
                                  JSONObject errorResponse) {
                Log.d("TwiiterClient", errorResponse.toString());
            }

            @Override
            public void onFailure(int statusCode,
                                  Header[] headers,
                                  Throwable throwable,
                                  JSONArray errorResponse) {
                Log.d("TwiiterClient", errorResponse.toString());
            }
        });


    }
}
