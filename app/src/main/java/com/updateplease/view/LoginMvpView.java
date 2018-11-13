package com.updateplease.view;

/**
 * Created by gboss on 04/11/18.
 */

public interface LoginMvpView extends MvpView {

  void showProgressIndicator(boolean show);

  void showOtpView(String otp);
}
