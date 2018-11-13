package com.updateplease.model;

/**
 * Created by gboss on 03/11/18.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ErrorResponse {

  @SerializedName("response")
  @Expose
  private String response;
  @SerializedName("response_data")
  @Expose
  private ResponseData responseData;

  public String getResponse() {
    return response;
  }

  public void setResponse(String response) {
    this.response = response;
  }

  public ResponseData getResponseData() {
    return responseData;
  }

  public void setResponseData(ResponseData responseData) {
    this.responseData = responseData;
  }

}

