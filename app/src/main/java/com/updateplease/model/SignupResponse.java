package com.updateplease.model;

/**
 * Created by gboss on 03/11/18.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignupResponse {

  @SerializedName("response")
  @Expose
  private String response;
  @SerializedName("response_data")
  @Expose
  private SignUpResponseData responseData;

  public String getResponse() {
    return response;
  }

  public void setResponse(String response) {
    this.response = response;
  }

  public SignUpResponseData getResponseData() {
    return responseData;
  }

  public void setResponseData(SignUpResponseData responseData) {
    this.responseData = responseData;
  }

}
