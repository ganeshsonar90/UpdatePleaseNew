package com.updateplease.model;

/**
 * Created by gboss on 05/11/18.
 */


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TaskResponseData {

  @SerializedName("reminder_list")
  @Expose
  private List<ReminderTask> reminderTask = null;
  @SerializedName("total_reminders")
  @Expose
  private String totalReminders;
  @SerializedName("done_reminders")
  @Expose
  private String doneReminders;
  @SerializedName("problem_reminders")
  @Expose
  private String problemReminders;
  @SerializedName("inprogress_reminders")
  @Expose
  private String inprogressReminders;

  public List<ReminderTask> getReminderTask() {
    return reminderTask;
  }

  public void setReminderTask(List<ReminderTask> reminderTask) {
    this.reminderTask = reminderTask;
  }

  public String getTotalReminders() {
    return totalReminders;
  }

  public void setTotalReminders(String totalReminders) {
    this.totalReminders = totalReminders;
  }

  public String getDoneReminders() {
    return doneReminders;
  }

  public void setDoneReminders(String doneReminders) {
    this.doneReminders = doneReminders;
  }

  public String getProblemReminders() {
    return problemReminders;
  }

  public void setProblemReminders(String problemReminders) {
    this.problemReminders = problemReminders;
  }

  public String getInprogressReminders() {
    return inprogressReminders;
  }

  public void setInprogressReminders(String inprogressReminders) {
    this.inprogressReminders = inprogressReminders;
  }

}
