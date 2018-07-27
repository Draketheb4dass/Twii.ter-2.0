package com.codepath.apps.restclienttemplate.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.adapters.TwiitAdapter;
import com.codepath.apps.restclienttemplate.models.Twiit;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by drake on 7/26/18
 */
public class TweetsListFragment extends Fragment {
    TwiitAdapter twiitAdapter;
    ArrayList<Twiit> twiits;
    RecyclerView rvTwiits;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //inflate layout
        View v = inflater.inflate(R.layout.fragments_twiits_list, container, false);
        // find the recyclerView
        rvTwiits = v.findViewById(R.id.rvTweet);
        //init the arrayList (data source)
        twiits = new ArrayList<>();
        //construct the adapter form data source
        twiitAdapter = new TwiitAdapter((twiits));
        //RecyclerView setup (layout manager, use adapter)
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getContext());
        rvTwiits.setLayoutManager(linearLayoutManager);
        rvTwiits.setAdapter(twiitAdapter);
        return v;
    }

    public void addItems(JSONArray response){
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
}
