package com.updateplease.network;

import com.google.gson.JsonObject;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by gboss on 02/10/18.
 */

public interface ApiInterface {


  @POST("callapi_v2.php")
  Call<ResponseBody> signUpUserPostRawJSON(@Body JsonObject locationPost);

  @POST("callapi_v2.php")
  Call<ResponseBody> loginUserPostRawJSON(@Body JsonObject locationPost);


  @POST("callapi_v2.php")
  Call<ResponseBody> addTaskPostRawJSON(@Body JsonObject locationPost);


}
