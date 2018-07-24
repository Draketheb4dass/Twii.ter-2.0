package com.codepath.apps.restclienttemplate.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.activities.DetailActivity;
import com.codepath.apps.restclienttemplate.models.Twiit;

import org.parceler.Parcels;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by drake on 7/19/18
 */
public class TwiitAdapter extends RecyclerView.Adapter<TwiitAdapter.ViewHolder>{
    private List<Twiit> mTwiits;
    Context context;
    //passing the tweets array to the constructor
    public TwiitAdapter(List<Twiit> twiits) {
        mTwiits = twiits;
    }

    //for each row, inflate the layout and cache ref into ViewHolder
    @NonNull
    @Override
    public  ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View twiitView = inflater.inflate(R.layout.item_twiit,parent,false);
        ViewHolder viewHolder = new ViewHolder(twiitView);
        return viewHolder;
    }

    //Bind the values based on the position of the element
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //get the data according to position
        Twiit twiit = mTwiits.get(position);
        //populate the views according to position
        holder.tvUserName.setText(twiit.user.name);
        holder.tvBody.setText(twiit.body);
        holder.tvTimestamp.setText(getRelativeTimeAgo(twiit.createdAt));
        Glide.with(context)
                .load(twiit.user.profileImageUrl)
                .apply(new RequestOptions().transforms(new CenterCrop(),
                        new RoundedCorners(10)))
                .into(holder.ivProfileImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DetailActivity.class);
                i.putExtra("twiit", Parcels.wrap(twiit));
                context.startActivity(i);
            }
        });
    }

    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(),
                    DateUtils.SECOND_IN_MILLIS,
                    DateUtils.FORMAT_ABBREV_RELATIVE).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
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
        public TextView tvTimestamp;

        public ViewHolder(View itemView) {
            super(itemView);
        //perform findViewById lookups
            ivProfileImage =itemView.findViewById(R.id.ivProfileImageDetail);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvBody = itemView.findViewById(R.id.tvBodyDetail);
            tvTimestamp = itemView.findViewById(R.id.tvTimeStamp);
        }
    }
}
