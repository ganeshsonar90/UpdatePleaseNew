package com.updateplease.helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by gboss on 04/11/18.
 */

public class AppPreferences {
  private SharedPreferences appSharedPrefs;
  private SharedPreferences.Editor prefsEditor;

  public AppPreferences(Context paramContext) {
    this.appSharedPrefs = paramContext.getSharedPreferences("com.updateplease", Context.MODE_PRIVATE);
    this.prefsEditor = this.appSharedPrefs.edit();
  }
  public String getValue(String key) {
    return this.appSharedPrefs.getString(key, "");
  }

  public  void putValue(String key, String value) {
    this.prefsEditor.putString(key, value);
    this.prefsEditor.commit();
  }


  public void removePreferences() {
    this.prefsEditor.clear();
    this.prefsEditor.commit();
  }

}
