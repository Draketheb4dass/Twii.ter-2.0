package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.models.Twiit;

import java.util.List;

/**
 * Created by drake on 7/19/18
 */
public class TwiitAdapter extends RecyclerView.Adapter<TwiitAdapter.ViewHolder>{
    private List<Twiit> mTwiits;
    //passing the tweets array to the constructor
    public TwiitAdapter(List<Twiit> twiits) {
        mTwiits = twiits;
    }

    //for each row, inflate the layout and cache ref into ViewHolder
    @Override
    public  ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View twiitView = inflater.inflate(R.layout.item_twiit,parent,false);
        ViewHolder viewHolder = new ViewHolder(twiitView);
        return viewHolder;
    }

    //Bind the values based on the position of the element
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //get the data according to positionn
        Twiit twiit = mTwiits.get(position);
        //populate the views according to position
        holder.tvUserName.setText(twiit.user.name);
        holder.tvBody.setText(twiit.body);
    }

    @Override
    public int getItemCount() {
        return mTwiits.size();
    }

    //create Viewholder class
    public static  class  ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivProfileImage;
        public TextView tvUserName;
        public  TextView tvBody;

        public ViewHolder(View itemView) {
            super(itemView);
        //perform findViewById lookups
            ivProfileImage =itemView.findViewById(R.id.ivProfileImage);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvBody = itemView.findViewById(R.id.tvBody);
        }
    }
}
