package com.updateplease.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import com.updateplease.R;
import com.updateplease.helper.AppConstant;
import com.updateplease.helper.AppPreferences;

public class SplashActivity extends AppCompatActivity {

  private String isUserVerified;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    AppPreferences preferences = new AppPreferences(getApplicationContext());
    isUserVerified = preferences.getValue(AppConstant.USER_VERIFIED);
    startAfterSomeTime();

  }

  private void startAfterSomeTime() {
    Handler mHandler = new Handler();
    mHandler.postDelayed(new Runnable() {

      @Override
      public void run() {
        //start your activity here
        if (isUserVerified.equalsIgnoreCase("true")) {
          startTaskActivity();
        } else {
          startLoginActivity();
        }
      }

    }, 1000L);
  }

  private void startTaskActivity() {
    Intent task = new Intent(this, TaskHomeActivity.class);
    startActivity(task);
    finish();
  }

  private void startLoginActivity() {
    Intent task = new Intent(this, LoginActivity.class);
    startActivity(task);
    finish();
  }

}
