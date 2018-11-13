package com.updateplease.model;

/**
 * Created by gboss on 05/11/18.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TaskRemainerResponse {

  @SerializedName("response")
  @Expose
  private String response;
  @SerializedName("response_data")
  @Expose
  private TaskResponseData responseData;

  public String getResponse() {
    return response;
  }

  public void setResponse(String response) {
    this.response = response;
  }

  public TaskResponseData getTaskResponseData() {
    return responseData;
  }

  public void setTaskResponseData(TaskResponseData responseData) {
    this.responseData = responseData;
  }

}
