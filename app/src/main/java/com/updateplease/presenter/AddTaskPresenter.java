package com.updateplease.presenter;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.provider.ContactsContract.Contacts;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.updateplease.R;
import com.updateplease.helper.AppConstant;
import com.updateplease.helper.AppPreferences;
import com.updateplease.model.ErrorResponse;
import com.updateplease.network.ApiClient;
import com.updateplease.network.ApiInterface;
import com.updateplease.view.AddTaskActivity;
import com.updateplease.view.AddTaskMvpView;
import java.io.IOException;
import okhttp3.ResponseBody;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gboss on 10/11/18.
 */

public class AddTaskPresenter implements Presenter<AddTaskMvpView> {

  public static String TAG = "AddTaskPresenter";

  private AddTaskMvpView addTaskMvpView;
  private AppPreferences appPreferences;

  public static final int RequestPermissionCode = 1;
  private String reminder_when_to_repeat = "Daily";
  private String reminder_repeat_every = "2";


  @Override
  public void attachView(AddTaskMvpView view) {
    this.addTaskMvpView = view;
    appPreferences = new AppPreferences(addTaskMvpView.getContext());
  }

  @Override
  public void detachView() {
    this.addTaskMvpView = null;
  }

  public void addTask(String fname,
      String mobile_no, String reminder_description,
      String reminder_date_from, String reminder_date_to,
      String reminder_is_repeat
  ) {

    addTaskMvpView.showProgressIndicator(true);

    ApiInterface apiService =
        ApiClient.getClient().create(ApiInterface.class);

    /*
    {
    "api_key": "511fc195b64833cb2a4b14893e546bd0",
    "api": "create_reminder",
    "request_data":
    {
		"user_id" : "24",
		"assigned_to" :
		{
			"fname" : "fname",
			"lname" : "lname",
			"mobile_no" : "8898141483"
		},
		"reminder_description" : "This is a Reminder which has Some Label on it say",
		"reminder_date_from" : "2017-05-31",
		"reminder_date_to" : "2017-06-10",
		"reminder_is_repeat" : 1,
		"reminder_when_to_repeat" : "Daily",
		"reminder_repeat_every" : 2
    }
}
    */

    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("api_key", appPreferences.getValue(AppConstant.USER_TOKEN));
    jsonObject.addProperty("api", "create_reminder");

    JsonObject requestDataObj = new JsonObject();

    requestDataObj.addProperty("user_id", appPreferences.getValue(AppConstant.USER_ID));

    JsonObject assignedToObj = new JsonObject();
    assignedToObj.addProperty("fname", fname);
    assignedToObj.addProperty("lname", "");
    assignedToObj.addProperty("mobile_no", mobile_no);

    requestDataObj.add("assigned_to", assignedToObj);

    requestDataObj.addProperty("reminder_description", reminder_description);
    requestDataObj.addProperty("reminder_date_from", reminder_date_from);
    requestDataObj.addProperty("reminder_date_to", reminder_date_to);

    requestDataObj.addProperty("reminder_is_repeat", reminder_is_repeat);
    requestDataObj.addProperty("reminder_when_to_repeat", reminder_when_to_repeat);
    requestDataObj.addProperty("reminder_repeat_every", reminder_repeat_every);

    jsonObject.add("request_data", requestDataObj);

    Call<ResponseBody> call = apiService.addTaskPostRawJSON(jsonObject);
    call.enqueue(new Callback<ResponseBody>() {
      @Override
      public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        addTaskMvpView.showProgressIndicator(false);
        if (response.isSuccessful() & response.code() == 200) {
          // use response data and do some fancy stuff :)

          ResponseBody signUpResultBody = response.body();

          if (signUpResultBody != null) {

            try {
              String signUpResult = signUpResultBody.string();
              if (!TextUtils.isEmpty(signUpResult)) {
                JSONObject addTaskResultObj = new JSONObject(signUpResult);
                String msg = addTaskMvpView.getContext().getResources()
                    .getString(R.string.success_added_remainder);

                if (addTaskResultObj.has("response") && !addTaskResultObj.isNull("response")) {

                  String responseStatus = addTaskResultObj.getString("response");

                  if (!TextUtils.isEmpty(responseStatus)) {

                    Gson gson = new Gson();

                    if (responseStatus.equalsIgnoreCase("success")) {

                      if (addTaskResultObj.has("reminder_result") && !addTaskResultObj
                          .isNull("reminder_result")) {

                        JSONObject remainderResult = addTaskResultObj
                            .getJSONObject("reminder_result");

                        if (remainderResult.has("msg") && !remainderResult.isNull("msg")) {

                          msg = remainderResult.getString("msg");

                        }

                      }
                      Toast.makeText(addTaskMvpView.getContext(), msg,
                          Toast.LENGTH_LONG).show();
                      ((AddTaskActivity) addTaskMvpView.getContext()).finish();

                    } else if (responseStatus.equalsIgnoreCase("error")) {

                      ErrorResponse errorResponseObj = gson
                          .fromJson(signUpResult, ErrorResponse.class);
                      if (errorResponseObj != null
                          && errorResponseObj.getResponseData().getErrorDescription() != null) {

                        Toast.makeText(addTaskMvpView.getContext(),
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

        addTaskMvpView.showProgressIndicator(false);

        Toast.makeText(addTaskMvpView.getContext(),
            addTaskMvpView.getContext().getResources().getText(R.string.something_went_wrong),
            Toast.LENGTH_LONG).show();

      }
    });
  }


  public void showDialog(Activity activity) {
    final Dialog dialog = new Dialog(activity);
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.setCancelable(false);
    dialog.setContentView(R.layout.dialog_repeat_task);

    final TextView txt_weeks = (TextView) dialog.findViewById(R.id.txt_weeks);
    Spinner day_spin = (Spinner) dialog.findViewById(R.id.spiner_days);
    ArrayAdapter<CharSequence> adapter_day = ArrayAdapter
        .createFromResource(addTaskMvpView.getContext(), R.array.spinner_day_array,
            android.R.layout.simple_spinner_item);
    adapter_day
        .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    day_spin.setAdapter(adapter_day);
    day_spin.setOnItemSelectedListener(new OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
// On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
        reminder_repeat_every = item;
      }

      @Override
      public void onNothingSelected(AdapterView<?> adapterView) {

      }
    });

    Spinner week_spin = (Spinner) dialog.findViewById(R.id.spiner_weekly);
    ArrayAdapter<CharSequence> adapter_time = ArrayAdapter
        .createFromResource(addTaskMvpView.getContext(), R.array.spinner_week_array,
            android.R.layout.simple_spinner_item);
    adapter_time
        .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    week_spin.setAdapter(adapter_time);
    week_spin.setOnItemSelectedListener(new OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
// On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
        reminder_when_to_repeat = item;
        if (item.equalsIgnoreCase("Daily")) {
          txt_weeks.setText("days");
        } else if (item.equalsIgnoreCase("Weekly")) {
          txt_weeks.setText("weeks");
        } else if (item.equalsIgnoreCase("Monthly")) {
          txt_weeks.setText("months");
        } else if (item.equalsIgnoreCase("Yearly")) {
          txt_weeks.setText("years");
        }

      }

      @Override
      public void onNothingSelected(AdapterView<?> adapterView) {

      }
    });

    TextView dialogCancel = (TextView) dialog.findViewById(R.id.txt_cancel);
    dialogCancel.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        dialog.dismiss();
      }
    });

    TextView dialogDone = (TextView) dialog.findViewById(R.id.txt_done);
    dialogDone.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        dialog.dismiss();
      }
    });

    dialog.show();

  }

  public void pickContact() {
    Intent intent = new Intent(Intent.ACTION_PICK, Contacts.CONTENT_URI);
    ((AddTaskActivity) addTaskMvpView.getContext()).startActivityForResult(intent, 7);
  }

  public void EnableRuntimePermission() {

    if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) addTaskMvpView.getContext(),
        Manifest.permission.READ_CONTACTS)) {

      Toast.makeText((Activity) addTaskMvpView.getContext(),
          "CONTACTS permission allows us to Access CONTACTS app", Toast.LENGTH_LONG).show();

    } else {

      ActivityCompat.requestPermissions((Activity) addTaskMvpView.getContext(), new String[]{
          Manifest.permission.READ_CONTACTS}, RequestPermissionCode);

    }
  }

}





