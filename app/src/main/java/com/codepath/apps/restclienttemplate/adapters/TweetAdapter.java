package com.codepath.apps.restclienttemplate.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TweeterClient;
import com.codepath.apps.restclienttemplate.activities.DetailActivity;
import com.codepath.apps.restclienttemplate.fragments.ComposeFragment;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.parceler.Parcels;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

/**
 * Created by drake on 7/19/18
 */
public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder>{
    private List<Tweet> mTweets;
    Context context;
    long tweetId;
    Tweet tweet;
    TweeterClient client;
    //passing the tweets array to the constructor
    public TweetAdapter(List<Tweet> tweets) {
        mTweets = tweets;
    }

    //for each row, inflate the layout and cache ref into ViewHolder
    @NonNull
    @Override
    public  ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View tweetView = inflater.inflate(R.layout.item_tweet,parent,false);
        ViewHolder viewHolder = new ViewHolder(tweetView);
        return viewHolder;
    }

    //Bind the values based on the position of the element
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //get the data according to position
        Tweet tweet = mTweets.get(position);
        //populate the views according to position
        holder.tvUserName.setText(tweet.user.name);
        holder.tvBody.setText(tweet.body);
        holder.tvTimestamp.setText(getRelativeTimeAgo(tweet.createdAt));
        holder.tvScreenName.setText("@" + tweet.user.screenName);
        Glide.with(context)
                .load(tweet.user.profileImageUrl)
                .apply(new RequestOptions().transforms(new CenterCrop(),
                        new RoundedCorners(10)))
                .into(holder.ivProfileImage);

        holder.itemView.setOnClickListener(view -> {
            Intent i = new Intent(context, DetailActivity.class);
            i.putExtra("tweet", Parcels.wrap(tweet));
            context.startActivity(i);
        });

        //reply to tweet
        holder.ibReply.setOnClickListener(v -> {
            tweetId = tweet.tweetId;
            //Call Compose fragment and pass data to reply to tweet
            FragmentManager fm = ((AppCompatActivity) context).getSupportFragmentManager();
            ComposeFragment composeFragment =
                    ComposeFragment.newInstance("Send a twiit");
            Bundle arguments = new Bundle();
            arguments.putLong("tweetId", tweetId);
            arguments.putString("toUser", tweet.user.screenName);
            composeFragment.setArguments(arguments);
            composeFragment.show(fm, "fragment_compose");
        });

        //retweet
        holder.ibRetweet.setOnClickListener(v -> {
            Toast.makeText(context,
                    "Retweet", Toast.LENGTH_SHORT).show();
            tweetId = tweet.tweetId;
            onPostRetweet(tweetId);
        });

        //Fav
        holder.ibFav.setOnClickListener(v -> {
           //Toast.makeText(context,
           //        "Like", Toast.LENGTH_SHORT).show();
           //tweetId = tweet.tweetId;
           //if((holder.ibFav).getTag().toString().trim().equals("on")){
           //    holder.ibFav.setTag("off");
           //    onPostLike(tweetId);
           //}
           //else if((holder.ibFav).getTag().toString().trim().equals("off")){
           //    holder.ibFav.setTag("on");
           //    onPostUnlike(tweetId);
           //}
        });


        //Share
        holder.ibShare.setOnClickListener(v -> Toast.makeText(context,
                "Shared", Toast.LENGTH_SHORT).show());


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

    public void onPostRetweet(long tweetId) {
        client = new TweeterClient(context);
        client.postRetweet(new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode,
                                  Header[] headers,
                                  byte[] responseBody) {

            }

            @Override
            public void onFailure(int statusCode,
                                  Header[] headers,
                                  byte[] responseBody,
                                  Throwable error) {

            }
        }, tweetId);

    }

    public void onPostLike(long tweetId) {
        client = new TweeterClient(context);
        client.postLike(new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode,
                                  Header[] headers,
                                  byte[] responseBody) {

            }

            @Override
            public void onFailure(int statusCode,
                                  Header[] headers,
                                  byte[] responseBody,
                                  Throwable error) {

            }
        }, tweetId);

    }

    public void onPostUnlike(long tweetId) {
        client = new TweeterClient(context);
        client.postUnlike(new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode,
                                  Header[] headers,
                                  byte[] responseBody) {

            }

            @Override
            public void onFailure(int statusCode,
                                  Header[] headers,
                                  byte[] responseBody,
                                  Throwable error) {

            }
        }, tweetId);

    }


    @Override
    public int getItemCount() {
        return mTweets.size();
    }

    //create Viewholder class
    public static  class  ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivProfileImage;
        public TextView tvUserName;
        public  TextView tvBody;
        public TextView tvTimestamp;
        public TextView tvScreenName;

        ImageButton ibReply;
        ImageButton ibRetweet;
        CheckBox ibFav;
        ImageButton ibShare;

        public ViewHolder(View itemView) {
            super(itemView);
        //perform findViewById lookups
            ivProfileImage =itemView.findViewById(R.id.ivProfileImageDetail);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvBody = itemView.findViewById(R.id.tvBodyDetail);
            tvTimestamp = itemView.findViewById(R.id.tvTimeStamp);
            tvScreenName = itemView.findViewById(R.id.tvScreenName);

            ibReply = itemView.findViewById(R.id.ibReply);
            ibRetweet = itemView.findViewById(R.id.ibRetweet);
            ibFav = itemView.findViewById(R.id.ibFav);
            ibShare = itemView.findViewById(R.id.ibShare);
            }
    }
}
