package com.updateplease.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.updateplease.R;
import com.updateplease.helper.Functions;
import com.updateplease.presenter.LoginPresenter;

public class LoginActivity extends AppCompatActivity implements LoginMvpView {

  @BindView(R.id.toolbar)
  Toolbar mToolbar;
  @BindView(R.id.img_app)
  ImageView mImgApp;
  @BindView(R.id.txt_mobile)
  TextView mTxtMobile;
  @BindView(R.id.edt_mobile)
  EditText mEdtMobile;
  @BindView(R.id.btn_otp)
  Button mBtnOtp;
  @BindView(R.id.txt_sign_up)
  TextView mTxtSignUp;
  @BindView(R.id.txt_dont_account)
  TextView mTxtDontAccount;
  @BindView(R.id.progressbar)
  ProgressBar mProgressbar;
  @BindView(R.id.view)
  View mLineView;
  private LoginPresenter presenter;
  private boolean otpView=false;
  private String responseOtp;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    ButterKnife.bind(this);
    //Set up presenter
    presenter = new LoginPresenter();
    presenter.attachView(this);

    setSupportActionBar(mToolbar);

    getSupportActionBar().setTitle(R.string.app_name);


  }

  @Override
  public Context getContext() {
    return this;
  }


  @Override
  public void showProgressIndicator(boolean show) {
    if (show) {
      mProgressbar.setVisibility(View.VISIBLE);
    } else {
      mProgressbar.setVisibility(View.GONE);
    }

  }

  @Override
  public void showOtpView(String responseOtp) {
    this.responseOtp=responseOtp;
    otpView=true;
    mTxtMobile.setText(getResources().getText(R.string.enter_otp));
    mEdtMobile.setHint(getResources().getText(R.string.enter_otp));
    mEdtMobile.setText("");
    mBtnOtp.setText(getResources().getText(R.string.btn_continue));
    mTxtSignUp.setVisibility(View.GONE);
    mTxtDontAccount.setVisibility(View.GONE);
    mLineView.setVisibility(View.GONE);

  }

  @OnClick({R.id.btn_otp, R.id.txt_sign_up})
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.btn_otp:
        // Hide Keyboard
        Functions.hideSoftKeyboard(LoginActivity.this);
        if (otpView){
          String userInputOtp = mEdtMobile.getText().toString().trim();
          presenter.verifyOtp(userInputOtp,responseOtp);
        }else {
          String mobileNumber = mEdtMobile.getText().toString().trim();
          presenter.getOtpRequest(mobileNumber);
        }


        break;
      case R.id.txt_sign_up:
        presenter.startSignUpActivity();

        break;
    }
  }

  @Override
  protected void onDestroy() {
    presenter.detachView();
    super.onDestroy();
  }
}
