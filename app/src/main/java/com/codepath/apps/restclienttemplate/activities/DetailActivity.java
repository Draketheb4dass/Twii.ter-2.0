package com.codepath.apps.restclienttemplate.activities;

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
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

public class DetailActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener{
    ImageView ivProfileImage;
    TextView tvUserName;
    TextView tvBody;
    TextView tvName;
    ImageView ivDetailImage;
    ImageButton ibReply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Get Parcel
        Tweet tweet = Parcels
                .unwrap(getIntent()
                        .getParcelableExtra("tweet"));

        //Bind Views
        ivProfileImage = findViewById(R.id.ivProfileImageDetail);
        Glide.with(getBaseContext())
                .load(tweet.user.profileImageUrl)
                .apply(new RequestOptions().transforms(new CenterCrop(),
                new RoundedCorners(10)))
                .into(ivProfileImage);

        tvUserName = findViewById(R.id.tvUsernameDetail);
        tvUserName.setText("@" + tweet.user.name);


        tvBody = findViewById(R.id.tvBodyDetail);
        tvBody.setText(tweet.body);
        tvName = findViewById(R.id.tvNameDetail);
        tvName.setText(tweet.user.name);
        ivDetailImage = findViewById(R.id.ivDetailImage);

        ibReply = findViewById(R.id.ib_Reply);
        ibReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DetailActivity.this, "reply", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }
}
