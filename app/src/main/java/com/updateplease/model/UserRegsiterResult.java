package com.updateplease.model;

/**
 * Created by gboss on 03/11/18.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserRegsiterResult {

  @SerializedName("msg")
  @Expose
  private String msg;
  @SerializedName("user_id")
  @Expose
  private String userId;
  @SerializedName("user_token")
  @Expose
  private String userToken;
  @SerializedName("otp")
  @Expose
  private String otp;

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getUserToken() {
    return userToken;
  }

  public void setUserToken(String userToken) {
    this.userToken = userToken;
  }

  public String getOtp() {
    return otp;
  }

  public void setOtp(String otp) {
    this.otp = otp;
  }

}
