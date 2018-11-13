package com.updateplease.model;

/**
 * Created by gboss on 03/11/18.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseData {

  @SerializedName("error_code")
  @Expose
  private Integer errorCode;
  @SerializedName("error_description")
  @Expose
  private String errorDescription;

  public Integer getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(Integer errorCode) {
    this.errorCode = errorCode;
  }

  public String getErrorDescription() {
    return errorDescription;
  }

  public void setErrorDescription(String errorDescription) {
    this.errorDescription = errorDescription;
  }


}
