package com.codepath.apps.restclienttemplate.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.apps.restclienttemplate.EndlessRecyclerViewScrollListener;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TweeterApp;
import com.codepath.apps.restclienttemplate.TweeterClient;
import com.codepath.apps.restclienttemplate.adapters.TweetAdapter;
import com.codepath.apps.restclienttemplate.fragments.ComposeFragment;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by drake on 7/28/18
 */
public class SearchActivity extends AppCompatActivity
        implements Toolbar.OnMenuItemClickListener{
    TweeterClient client;
    TweetAdapter tweetAdapter;
    ArrayList<Tweet> tweets;
    RecyclerView rvTweets;
    Toolbar myToolbar;
    static String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //Set custom AppBar
        myToolbar = findViewById(R.id.timeline_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setOnMenuItemClickListener(this);

        client = TweeterApp.getRestClient(getBaseContext());
        // find the recyclerView
        rvTweets = findViewById(R.id.rvTweet);
        //init the arrayList (data source)
        tweets = new ArrayList<>();
        //construct the adapter form data source
        tweetAdapter = new TweetAdapter((tweets));
        //RecyclerView setup (layout manager, use adapter)
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this);
        rvTweets.setLayoutManager(linearLayoutManager);
        // Retain an instance so that you can call `resetState()` for fresh searches
        EndlessRecyclerViewScrollListener scrollListener =
                new EndlessRecyclerViewScrollListener(linearLayoutManager) {
                    @Override
                    public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                        // Triggered only when new data needs to be appended to the list
                        // Add whatever code is needed to append new items to the bottom of the list
                        loadNextDataFromApi(page);
                    }
                };
        // Adds the scroll listener to RecyclerView
        rvTweets.addOnScrollListener(scrollListener);
        //set the adapter
        rvTweets.setAdapter(tweetAdapter);
        String query = getIntent().getStringExtra("query");
        populateSearch(query);
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return true;
    }

    // Append the next page of data into the adapter
    // This method probably sends out a network request and appends new data items to your adapter.
    public void loadNextDataFromApi(int offset) {
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        //  --> Deserialize and construct new model objects from the API response
        //  --> Append the new data objects to the existing set of items inside the array of items
        //  --> Notify the adapter of the new items made with `notifyItemRangeInserted()`
        populateSearch(query);
    }

    public void populateSearch(String query) {
        client.getSearchResult(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //Log.d("TwiiterClientObject", response.toString());
                //try {
                //    JSONObject jsonObject = response.getJSONObject("statuses");
//
                //            Tweet twiit = Tweet.fromJSON(jsonObject);
                //            tweets.add(twiit);
                //            tweetAdapter.notifyItemInserted(tweets.size() - 1);
                //        } catch (JSONException e) {
                //            e.printStackTrace();
                //        }
                try {
                    JSONArray jsonArray = response.getJSONArray("statuses");
                    Tweet tweet = Tweet.fromJSONArray(jsonArray);
                    tweets.add(tweet);
                    tweetAdapter.notifyItemInserted(tweets.size() - 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }




            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                //Log.d("TwiiterClientArray", response.toString());
                //iterate through the JSON array
                 //for each entry, deserialize JSON object
                //for (int i = 0; i < response.length(); i++ ) {
                //    //convert each object to a Tweet model
                //    //add that tweet model to our data source
                //    //notify the adapter that we've added an item
                //    try {
                //        Tweet twiit = Tweet.fromJSON(response.getJSONObject(i));
                //        tweets.add(twiit);
                //        tweetAdapter.notifyItemInserted(tweets.size() - 1);
                //    } catch (JSONException e){
                //        e.printStackTrace();
                //    }
                //}
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
        }, query);


    }

}
