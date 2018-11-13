package com.updateplease.model;

/**
 * Created by gboss on 04/11/18.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserLoginResult {

  @SerializedName("msg")
  @Expose
  private String msg;
  @SerializedName("otp")
  @Expose
  private String otp;
  @SerializedName("user_id")
  @Expose
  private String userId;
  @SerializedName("user_token")
  @Expose
  private String userToken;

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public String getOtp() {
    return otp;
  }

  public void setOtp(String otp) {
    this.otp = otp;
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

}
