package com.updateplease.presenter;

/**
 * Created by gboss on 04/11/18.
 */

import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.updateplease.R;
import com.updateplease.helper.AppConstant;
import com.updateplease.helper.AppPreferences;
import com.updateplease.model.ErrorResponse;
import com.updateplease.model.SignupResponse;
import com.updateplease.network.ApiClient;
import com.updateplease.network.ApiInterface;
import com.updateplease.view.LoginActivity;
import com.updateplease.view.LoginMvpView;
import com.updateplease.view.SignupActivity;
import com.updateplease.view.TaskHomeActivity;
import java.io.IOException;
import okhttp3.ResponseBody;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginPresenter implements Presenter<LoginMvpView> {

  public static String TAG = "LoginPresenter";

  private LoginMvpView loginMvpView;
  private AppPreferences appPreferences;


  @Override
  public void attachView(LoginMvpView view) {
    this.loginMvpView = view;
    appPreferences = new AppPreferences(loginMvpView.getContext());
  }

  @Override
  public void detachView() {
    this.loginMvpView = null;

  }

  public void getOtpRequest(String mobileNumber) {

    loginMvpView.showProgressIndicator(true);

    if (TextUtils.isEmpty(mobileNumber)) {

      Toast.makeText(loginMvpView.getContext(), "Please enter the Mobile number", Toast.LENGTH_LONG)
          .show();
      loginMvpView.showProgressIndicator(false);
      return;
    }

    if (mobileNumber.length() < 10) {
      Toast.makeText((loginMvpView.getContext()), "Mobile number is invalid!", Toast.LENGTH_SHORT)
          .show();
      loginMvpView.showProgressIndicator(false);
      return;
    }

    ApiInterface apiService =
        ApiClient.getClient().create(ApiInterface.class);


/*
    {
      "api_key": "ffffe5a1bb313abbc54d874346183f5a",
        "api": "validate_user_using_mobile_no",
        "request_data":
      {
        "mobile_no" : "8308968971"
      }
    }*/

    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("api_key", "ffffe5a1bb313abbc54d874346183f5a");
    jsonObject.addProperty("api", "validate_user_using_mobile_no");

    JsonObject requestDataObj = new JsonObject();
    requestDataObj.addProperty("mobile_no", mobileNumber);

    jsonObject.add("request_data", requestDataObj);

    Call<ResponseBody> call = apiService.loginUserPostRawJSON(jsonObject);
    call.enqueue(new Callback<ResponseBody>() {
      @Override
      public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        loginMvpView.showProgressIndicator(false);
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
                        if (signupResponseObj.getResponseData().getUserLoginResult() != null
                            && signupResponseObj.getResponseData().getUserLoginResult().getMsg()
                            != null) {

                          String otp = signupResponseObj.getResponseData().getUserLoginResult()
                              .getOtp();
                          loginMvpView.showOtpView(otp);

                          Toast.makeText(loginMvpView.getContext(),
                              signupResponseObj.getResponseData().getUserLoginResult().getMsg(),
                              Toast.LENGTH_LONG).show();

                          String userToken = signupResponseObj.getResponseData()
                              .getUserLoginResult()
                              .getUserToken();
                          appPreferences.putValue(AppConstant.USER_TOKEN, userToken);

                          String userId = signupResponseObj.getResponseData().getUserLoginResult()
                              .getUserId();
                          appPreferences.putValue(AppConstant.USER_ID, userId);


                        }
                      }


                    } else if (responseStatus.equalsIgnoreCase("error")) {

                      ErrorResponse errorResponseObj = gson
                          .fromJson(signUpResult, ErrorResponse.class);
                      if (errorResponseObj != null
                          && errorResponseObj.getResponseData().getErrorDescription() != null) {

                        Toast.makeText(loginMvpView.getContext(),
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

        loginMvpView.showProgressIndicator(false);

        Toast.makeText(loginMvpView.getContext(),
            loginMvpView.getContext().getResources().getText(R.string.something_went_wrong),
            Toast.LENGTH_LONG).show();

      }
    });
  }


  public void startSignUpActivity() {
    Intent sign = new Intent(loginMvpView.getContext(), SignupActivity.class);
    loginMvpView.getContext().startActivity(sign);

  }

  public void startTaskHomeActivity() {
    Intent taskHome = new Intent(loginMvpView.getContext(), TaskHomeActivity.class);
    loginMvpView.getContext().startActivity(taskHome);
    ((LoginActivity) loginMvpView.getContext()).finish();

  }

  public void verifyOtp(String userInputOtp, String responseOtp) {

    if (TextUtils.isEmpty(userInputOtp)) {

      Toast.makeText(loginMvpView.getContext(), "Please enter the Otp", Toast.LENGTH_LONG).show();
      loginMvpView.showProgressIndicator(false);
      return;
    }

    if (userInputOtp.equalsIgnoreCase(responseOtp)) {

      appPreferences.putValue(AppConstant.USER_VERIFIED, "true");

      startTaskHomeActivity();
    } else {
      Toast.makeText(loginMvpView.getContext(),
          loginMvpView.getContext().getResources().getText(R.string.invalid_otp),
          Toast.LENGTH_LONG).show();

    }


  }
}

