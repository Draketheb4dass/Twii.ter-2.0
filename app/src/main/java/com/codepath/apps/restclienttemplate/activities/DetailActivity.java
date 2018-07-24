package com.codepath.apps.restclienttemplate.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.models.Twiit;

import org.parceler.Parcels;

public class DetailActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener{
    ImageView ivProfileImage;
    TextView tvUserName;
    TextView tvBody;
    TextView tvName;
    ImageView ivDetailImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Get Parcel
        Twiit twiit = Parcels
                .unwrap(getIntent()
                        .getParcelableExtra("twiit"));

        //Bind Views
        ivProfileImage = findViewById(R.id.ivProfileImageDetail);
        Glide.with(getBaseContext())
                .load(twiit.user.profileImageUrl)
                .apply(new RequestOptions().transforms(new CenterCrop(),
                new RoundedCorners(10)))
                .into(ivProfileImage);

        tvUserName = findViewById(R.id.tvUsernameDetail);
        tvUserName.setText("@" + twiit.user.name);


        tvBody = findViewById(R.id.tvBodyDetail);
        tvBody.setText(twiit.body);
        tvName = findViewById(R.id.tvNameDetail);
        tvName.setText(twiit.user.name);
        ivDetailImage = findViewById(R.id.ivDetailImage);


    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }
}
