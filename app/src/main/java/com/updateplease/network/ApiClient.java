package com.updateplease.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by gboss on 02/10/18.
 */

public class ApiClient {

  public static final String BASE_URL = "http://www.so-cnvenkat-a666.sprybees.com/";
  private static Retrofit retrofit = null;


  public static Retrofit getClient() {
    if (retrofit==null) {
      retrofit = new Retrofit.Builder()
          .baseUrl(BASE_URL)
          .addConverterFactory(GsonConverterFactory.create())
          .build();
    }
    return retrofit;
  }
}
