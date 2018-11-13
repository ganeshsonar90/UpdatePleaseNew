package com.updateplease.model;

/**
 * Created by gboss on 03/11/18.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignUpResponseData {

  @SerializedName("user_regsiter_result")
  @Expose
  private UserRegsiterResult userRegsiterResult;

  public UserRegsiterResult getUserRegsiterResult() {
    return userRegsiterResult;
  }

  public void setUserRegsiterResult(UserRegsiterResult userRegsiterResult) {
    this.userRegsiterResult = userRegsiterResult;
  }

  @SerializedName("user_login_result")
  @Expose
  private UserLoginResult userLoginResult;

  public UserLoginResult getUserLoginResult() {
    return userLoginResult;
  }

  public void setUserLoginResult(UserLoginResult userLoginResult) {
    this.userLoginResult = userLoginResult;
  }


}



