package com.codepath.apps.restclienttemplate.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.apps.restclienttemplate.ProfileActivity;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.fragments.ComposeFragment;
import com.codepath.apps.restclienttemplate.fragments.TweetsPagerAdapter;

public class TimelineActivity extends AppCompatActivity
        implements Toolbar.OnMenuItemClickListener
        //ComposeFragment.ComposeListener
         {
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

        //get the view Pager
        ViewPager vpPager = findViewById(R.id.viewpager);
        //set the adapter for the pager
        vpPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager(), this));
        //setup the TabLayout to use th view pager
        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(vpPager);




       // // Retain an instance so that you can call `resetState()` for fresh searches
       // EndlessRecyclerViewScrollListener scrollListener =
       //         new EndlessRecyclerViewScrollListener(linearLayoutManager) {
       //     @Override
       //     public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
       //         // Triggered only when new data needs to be appended to the list
       //         // Add whatever code is needed to append new items to the bottom of the list
       //         loadNextDataFromApi(page);
       //     }
       // };
       // // Adds the scroll listener to RecyclerView
       // rvTwiits.addOnScrollListener(scrollListener);
       // //set the adapter


        //Floating Action Button implementation
        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            FragmentManager fm = getSupportFragmentManager();
            ComposeFragment composeFragment =
                    ComposeFragment.newInstance("Send a twiit");
            composeFragment.show(fm, "fragment_compose");
        });
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
        //populateTimeline();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        return true;
    }

    public void onProfileView(MenuItem item) {
        //launch the profile view
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
    }

             // @Override
  // public void onStatusPosted(String status) {
  //     postStatus(status);
  //     populateTimeline();
  // }

  // public void postStatus(String status) {
  //     client.postStatus(new AsyncHttpResponseHandler() {
  //         @Override
  //         public void onSuccess(int statusCode,
  //                               Header[] headers,
  //                               byte[] responseBody) {
  //         }

  //         @Override
  //         public void onFailure(int statusCode,
  //                               Header[] headers,
  //                               byte[] responseBody,
  //                               Throwable error) {
  //         }
  //     }, status);
  //     populateTimeline();
  // }

}
