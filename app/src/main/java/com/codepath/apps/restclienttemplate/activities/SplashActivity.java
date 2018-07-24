package com.codepath.apps.restclienttemplate.activities;


import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;

import com.codepath.apps.restclienttemplate.activities.LoginActivity;


/**
 * Created by drake on Jul 20 2018.
 * This splash screen use no layout file, but theme file @ drawable/
 */


public class SplashActivity extends AppCompatActivity {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume(){
        super.onResume();
        View decorView = getWindow().getDecorView();
        //Hide status bar
        int uiOptions= View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        //Hide action Bar
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

    }
}


