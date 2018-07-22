package com.codepath.apps.restclienttemplate;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.MainThread;
import android.support.v4.app.FragmentManager;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.fragments.ComposeFragment;
import com.codepath.apps.restclienttemplate.models.Twiit;
import com.github.scribejava.apis.TwitterApi;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener{
    TwiiterClient client;
    TwiitAdapter twiitAdapter;
    ArrayList<Twiit> twiits;
    RecyclerView rvTwiits;
    private EndlessRecyclerViewScrollListener scrollListener;
    Toolbar myToolbar;
    SwipeRefreshLayout mSwipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        //Set custom AppBar
        myToolbar = findViewById(R.id.timeline_toolbar);
        myToolbar.inflateMenu(R.menu.menu_timeline);
        setSupportActionBar(myToolbar);
        myToolbar.setOnMenuItemClickListener(this);

        client = TwiiterApp.getRestClient(getBaseContext());
        // find the recyclerView
        rvTwiits = findViewById(R.id.rvTweet);
        //init the arrayList (data source)
        twiits = new ArrayList<>();
        //construct the adapter form data source
        twiitAdapter = new TwiitAdapter((twiits));
        //RecyclerView setup (layout manager, use adapter)
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this);
        rvTwiits.setLayoutManager(linearLayoutManager);
        // Retain an instance so that you can call `resetState()` for fresh searches
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                loadNextDataFromApi(page);
            }
        };
        // Adds the scroll listener to RecyclerView
        rvTwiits.addOnScrollListener(scrollListener);
        //set the adapter
        rvTwiits.setAdapter(twiitAdapter);
        populateTimeline();
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            //Filter Intent
            case R.id.action_compose:
                //Show ComposeFragment
               FragmentManager fm = getSupportFragmentManager();
               ComposeFragment composeFragment =
                        ComposeFragment.newInstance("Send a twiit");
               composeFragment.show(fm, "fragment_compose");
                return true;
        }
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
        populateTimeline();
    }

    private void populateTimeline() {
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
                for (int i = 0; i < response.length(); i++ ) {
                    //convert each object to a Twiit model
                    //add that twiit model to our data source
                    //notify the adapter that we've added an item
                    try {
                        Twiit twiit = Twiit.fromJSON(response.getJSONObject(i));
                        twiits.add(twiit);
                        twiitAdapter.notifyItemInserted(twiits.size() - 1);
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("TwiiterClient", responseString);
                throwable.printStackTrace();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.d("TwiiterClient", errorResponse.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                    Log.d("TwiiterClient", errorResponse.toString());
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        return true;
    }

}
