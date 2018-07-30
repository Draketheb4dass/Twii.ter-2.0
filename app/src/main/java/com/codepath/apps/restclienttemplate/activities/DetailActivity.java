package com.codepath.apps.restclienttemplate.activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.codepath.apps.restclienttemplate.ProfileActivity;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TweeterClient;
import com.codepath.apps.restclienttemplate.fragments.ComposeFragment;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class DetailActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener{
    ImageView ivProfileImage;
    TextView tvUserName;
    TextView tvBody;
    TextView tvName;
    ImageView ivDetailImage;
    ImageButton ibReply;
    ImageButton ibRetweet;
    ImageButton ibFav;
    ImageButton ibShare;

    TweeterClient client;
    long tweeId;
    Tweet tweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Get Parcel
        tweet = Parcels
                .unwrap(getIntent()
                        .getParcelableExtra("tweet"));

        //Bind Views
        ivProfileImage = findViewById(R.id.ivProfileImageDetail);
        Glide.with(getBaseContext())
                .load(tweet.user.profileImageUrl)
                .apply(new RequestOptions().transforms(new CenterCrop(),
                new RoundedCorners(10)))
                .into(ivProfileImage);

        ivProfileImage.setOnClickListener(v -> {
            Intent i = new Intent(getBaseContext(), ProfileActivity.class);
            i.putExtra("screen_name", tweet.user.screenName);
            startActivity(i);
        });

        tvUserName = findViewById(R.id.tvUsernameDetail);
        tvUserName.setText("@" + tweet.user.name);


        tvBody = findViewById(R.id.tvBodyDetail);
        tvBody.setText(tweet.body);
        tvName = findViewById(R.id.tvNameDetail);
        tvName.setText(tweet.user.name);
        ivDetailImage = findViewById(R.id.ivDetailImage);

        ibReply = findViewById(R.id.ibReply);
        ibReply.setOnClickListener(this::onClick);
        ibRetweet = findViewById(R.id.ibRetweet);
        ibRetweet.setOnClickListener(this::onClick);
        ibFav = findViewById(R.id.ibFav);
        ibFav.setOnClickListener(this::onClick);
        ibShare = findViewById(R.id.ibShare);
        ibShare.setOnClickListener(this::onClick);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case  R.id.ibReply: {
                tweeId = tweet.tweetId;
                //Call Compose fragment and pass data to reply to tweet
                FragmentManager fm = getSupportFragmentManager();
                ComposeFragment composeFragment =
                        ComposeFragment.newInstance("Send a twiit");
                Bundle arguments = new Bundle();
                arguments.putLong("tweetId", tweeId);
                arguments.putString("toUser", tweet.user.screenName);
                composeFragment.setArguments(arguments);
                composeFragment.show(fm, "fragment_compose");
                break;
            }

            case R.id.ibRetweet: {
                //Retweet a tweet
                Toast.makeText(DetailActivity.this,
                        "Retweet", Toast.LENGTH_SHORT).show();
                tweeId = tweet.tweetId;
                onPostRetweet(tweeId);
                break;
            }

            case R.id.ibFav: {
                Toast.makeText(DetailActivity.this,
                        "Like", Toast.LENGTH_SHORT).show();
                break;
            }

            case R.id.ibShare: {
                Toast.makeText(DetailActivity.this,
                        "Shared", Toast.LENGTH_SHORT).show();
                break;
            }

        }
    }

    public void onPostRetweet(long tweetId) {
        client = new TweeterClient(this);
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

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }
}
