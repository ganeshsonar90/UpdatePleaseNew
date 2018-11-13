package com.updateplease.model;

/**
 * Created by gboss on 05/11/18.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReminderTask {

  @SerializedName("id")
  @Expose
  private Integer id;
  @SerializedName("fname")
  @Expose
  private String fname;
  @SerializedName("lname")
  @Expose
  private String lname;
  @SerializedName("reminder_desc")
  @Expose
  private String reminderDesc;
  @SerializedName("reminder_mobile_no")
  @Expose
  private String reminderMobileNo;
  @SerializedName("reminder_date_from")
  @Expose
  private String reminderDateFrom;
  @SerializedName("reminder_date_to")
  @Expose
  private String reminderDateTo;
  @SerializedName("reminder_status")
  @Expose
  private Integer reminderStatus;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getFname() {
    return fname;
  }

  public void setFname(String fname) {
    this.fname = fname;
  }

  public String getLname() {
    return lname;
  }

  public void setLname(String lname) {
    this.lname = lname;
  }

  public String getReminderDesc() {
    return reminderDesc;
  }

  public void setReminderDesc(String reminderDesc) {
    this.reminderDesc = reminderDesc;
  }

  public String getReminderMobileNo() {
    return reminderMobileNo;
  }

  public void setReminderMobileNo(String reminderMobileNo) {
    this.reminderMobileNo = reminderMobileNo;
  }

  public String getReminderDateFrom() {
    return reminderDateFrom;
  }

  public void setReminderDateFrom(String reminderDateFrom) {
    this.reminderDateFrom = reminderDateFrom;
  }

  public String getReminderDateTo() {
    return reminderDateTo;
  }

  public void setReminderDateTo(String reminderDateTo) {
    this.reminderDateTo = reminderDateTo;
  }

  public Integer getReminderStatus() {
    return reminderStatus;
  }

  public void setReminderStatus(Integer reminderStatus) {
    this.reminderStatus = reminderStatus;
  }

}
