package com.codepath.apps.restclienttemplate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.codepath.apps.restclienttemplate.models.Twiit;
import com.github.scribejava.apis.TwitterApi;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity {
    TwiiterClient client;
    TwiitAdapter twiitAdapter;
    ArrayList<Twiit> twiits;
    RecyclerView rvTwiits;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        client = TwiiterApp.getRestClient(getBaseContext());
        // find the recyclerView
        rvTwiits = findViewById(R.id.rvTweet);
        //init the arrayList (data source)
        twiits = new ArrayList<>();
        //construct the adapter form data source
        twiitAdapter = new TwiitAdapter((twiits));

        //RecyclerView setup (layout manager, use adapter)
        rvTwiits.setLayoutManager(new LinearLayoutManager(this));
        //set the adapter
        rvTwiits.setAdapter(twiitAdapter);
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
}
