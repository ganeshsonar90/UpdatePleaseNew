package com.updateplease.presenter;

import android.text.TextUtils;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.updateplease.R;
import com.updateplease.helper.Functions;
import com.updateplease.model.ErrorResponse;
import com.updateplease.model.SignupResponse;
import com.updateplease.network.ApiClient;
import com.updateplease.network.ApiInterface;
import com.updateplease.view.SignUpMvpView;
import java.io.IOException;
import okhttp3.ResponseBody;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignUpPresenter implements Presenter<SignUpMvpView> {

  public static String TAG = "SignUpPresenter";

  private SignUpMvpView signupMvpView;


  @Override
  public void attachView(SignUpMvpView view) {
    this.signupMvpView = view;
  }

  @Override
  public void detachView() {
    this.signupMvpView = null;

  }

  public void signUpUserPostRaw(
      String firstName, String lastName,
      String mobileNumber, String email) {

    signupMvpView.showProgressIndicator(true);

    if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName)
        || TextUtils.isEmpty(mobileNumber) || TextUtils.isEmpty(email)) {

      Toast.makeText(signupMvpView.getContext(), "Please enter the required values!",
          Toast.LENGTH_LONG).show();
      signupMvpView.showProgressIndicator(false);
      return;
    }

    if (mobileNumber.length() < 10) {
      Toast.makeText((signupMvpView.getContext()), "Mobile number is invalid!", Toast.LENGTH_SHORT)
          .show();
      signupMvpView.showProgressIndicator(false);
      return;
    }

    // Check for empty data in the form
    if (Functions.isValidEmailAddress(email)) {
      // login user
      //loginProcess(email, password);
    } else {
      Toast.makeText((signupMvpView.getContext()), "Email is not valid!", Toast.LENGTH_SHORT)
          .show();
      signupMvpView.showProgressIndicator(false);
      return;
    }

    ApiInterface apiService =
        ApiClient.getClient().create(ApiInterface.class);


    /*{
      "api_key": "ffffe5a1bb313abbc54d874346183f5a",
        "api": "register_user",
        "request_data":
      {
        "fname" : "fname",
          "lname" : "lname",
          "mobile_no" : "YOUR MOBILE NO",
          "email_addr" : "test@gmail.com"

      }
    }*/

    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("api_key", "ffffe5a1bb313abbc54d874346183f5a");
    jsonObject.addProperty("api", "validate_user_using_mobile_no");

    JsonObject requestDataObj = new JsonObject();
    requestDataObj.addProperty("fname", firstName);
    requestDataObj.addProperty("lname", lastName);
    requestDataObj.addProperty("mobile_no", mobileNumber);
    requestDataObj.addProperty("email_addr", email);

    jsonObject.add("request_data", requestDataObj);

    Call<ResponseBody> call = apiService.signUpUserPostRawJSON(jsonObject);
    call.enqueue(new Callback<ResponseBody>() {
      @Override
      public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        signupMvpView.showProgressIndicator(false);
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

                      SignupResponse signupResponseObj = gson
                          .fromJson(signUpResult, SignupResponse.class);

                      if (signupResponseObj != null) {
                        if (signupResponseObj.getResponseData().getUserRegsiterResult() != null
                            && signupResponseObj.getResponseData().getUserRegsiterResult().getMsg()
                            != null) {

                          Toast.makeText(signupMvpView.getContext(),
                              signupResponseObj.getResponseData().getUserRegsiterResult().getMsg(),
                              Toast.LENGTH_LONG).show();


                        } else if (signupResponseObj.getResponseData().getUserLoginResult() != null
                            && signupResponseObj.getResponseData().getUserLoginResult().getMsg()
                            != null) {

                          Toast.makeText(signupMvpView.getContext(),
                              signupResponseObj.getResponseData().getUserLoginResult().getMsg(),
                              Toast.LENGTH_LONG).show();


                        }
                      }


                    } else if (responseStatus.equalsIgnoreCase("error")) {

                      ErrorResponse errorResponseObj = gson
                          .fromJson(signUpResult, ErrorResponse.class);
                      if (errorResponseObj != null
                          && errorResponseObj.getResponseData().getErrorDescription() != null) {

                        Toast.makeText(signupMvpView.getContext(),
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


          }


        }


      }

      @Override
      public void onFailure(Call<ResponseBody> call, Throwable t) {
        // Log error here since request failed

        signupMvpView.showProgressIndicator(false);

        Toast.makeText(signupMvpView.getContext(),
            signupMvpView.getContext().getResources().getText(R.string.something_went_wrong),
            Toast.LENGTH_LONG).show();

      }
    });
  }


}
