package com.updateplease.presenter;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.updateplease.R;
import com.updateplease.helper.AppConstant;
import com.updateplease.helper.AppPreferences;
import com.updateplease.model.ErrorResponse;
import com.updateplease.model.ReminderTask;
import com.updateplease.model.TaskRemainerResponse;
import com.updateplease.network.ApiClient;
import com.updateplease.network.ApiInterface;
import com.updateplease.view.AddTaskActivity;
import com.updateplease.view.SignupActivity;
import com.updateplease.view.TaskHomeMvpView;
import java.io.IOException;
import java.util.List;
import okhttp3.ResponseBody;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gboss on 04/11/18.
 */

public class TaskHomePresenter implements Presenter<TaskHomeMvpView> {

  public static String TAG = "TaskHomePresenter";

  private TaskHomeMvpView taskHomeMvpView;
  private AppPreferences appPreferences;


  @Override
  public void attachView(TaskHomeMvpView view) {
    this.taskHomeMvpView = view;
    appPreferences = new AppPreferences(taskHomeMvpView.getContext());
  }

  @Override
  public void detachView() {
    this.taskHomeMvpView = null;

  }

  public void getRemainderTasks(String status,
      int page, int limit, String reminder_date_from, String reminder_date_to) {

    taskHomeMvpView.showProgressIndicator(true);

    ApiInterface apiService =
        ApiClient.getClient().create(ApiInterface.class);
    

    /*{
      "api_key": "1fcaadb96a6b397b66f67f95d8a8ebd2",
        "api": "get_reminders",
        "request_data":
      {
        "user_id" : "12",
          "status" : "pending",
          "page" : "1",
          "limit" : "10",
          "reminder_date_from" : "2017-03-16",
          "reminder_date_to" : "2017-03-18"
      }
    }*/

    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("api_key", appPreferences.getValue(AppConstant.USER_TOKEN));
    jsonObject.addProperty("api", "get_reminders");

    JsonObject requestDataObj = new JsonObject();

    requestDataObj.addProperty("user_id", appPreferences.getValue(AppConstant.USER_ID));
    requestDataObj.addProperty("status", status);
    requestDataObj.addProperty("page", page);
    requestDataObj.addProperty("limit", limit);
    requestDataObj.addProperty("reminder_date_from", reminder_date_from);
    requestDataObj.addProperty("reminder_date_to", reminder_date_to);

    jsonObject.add("request_data", requestDataObj);

    Call<ResponseBody> call = apiService.signUpUserPostRawJSON(jsonObject);
    call.enqueue(new Callback<ResponseBody>() {
      @Override
      public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        taskHomeMvpView.showProgressIndicator(false);
        if (response.isSuccessful() & response.code() == 200) {
          // use response data and do some fancy stuff :)

          ResponseBody signUpResultBody = response.body();

          if (signUpResultBody != null) {

            try {
              String signUpResult = signUpResultBody.string();
              if (!TextUtils.isEmpty(signUpResult)) {
                JSONObject signUpResultObj = new JSONObject(signUpResult);

                if (signUpResultObj.has("response") && !signUpResultObj.isNull("response")) {

                  String responseStatus = signUpResultObj.getString("response");

                  if (!TextUtils.isEmpty(responseStatus)) {

                    Gson gson = new Gson();

                    if (responseStatus.equalsIgnoreCase("success")) {

                      TaskRemainerResponse taskRemainerResponseObj = gson
                          .fromJson(signUpResult, TaskRemainerResponse.class);

                      if (taskRemainerResponseObj != null
                          && taskRemainerResponseObj.getTaskResponseData() != null) {

                        List<ReminderTask> reminderTask = taskRemainerResponseObj
                            .getTaskResponseData().getReminderTask();
                        if (reminderTask != null && reminderTask.size() > 0) {
                          taskHomeMvpView.showTasks(reminderTask);
                        } else {
                          Toast.makeText(taskHomeMvpView.getContext(),
                              taskHomeMvpView.getContext().getResources()
                                  .getText(R.string.no_record_found),
                              Toast.LENGTH_LONG).show();
                        }


                      }


                    } else if (responseStatus.equalsIgnoreCase("error")) {

                      ErrorResponse errorResponseObj = gson
                          .fromJson(signUpResult, ErrorResponse.class);
                      if (errorResponseObj != null
                          && errorResponseObj.getResponseData().getErrorDescription() != null) {

                        Toast.makeText(taskHomeMvpView.getContext(),
                            errorResponseObj.getResponseData().getErrorDescription(),
                            Toast.LENGTH_LONG).show();
                      }


                    }


                  }


                }


              }


            } catch (IOException e) {
              e.printStackTrace();
            } catch (JSONException e) {
              e.printStackTrace();
            }


          } else {

          }


        }


      }

      @Override
      public void onFailure(Call<ResponseBody> call, Throwable t) {
        // Log error here since request failed

        taskHomeMvpView.showProgressIndicator(false);

        Toast.makeText(taskHomeMvpView.getContext(),
            taskHomeMvpView.getContext().getResources().getText(R.string.something_went_wrong),
            Toast.LENGTH_LONG).show();

      }
    });
  }

  public void startAddTaskActivity() {
    Intent sign = new Intent(taskHomeMvpView.getContext(), AddTaskActivity.class);
    taskHomeMvpView.getContext().startActivity(sign);

  }

}




