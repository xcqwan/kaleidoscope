package com.koolbao.kaleidoscope.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtils {
	private static SharedPreferencesUtils instance;
	private SharedPreferences sharedPreferences;
	
	public static SharedPreferencesUtils with(Context context) {
		if (instance == null) {
			instance = new SharedPreferencesUtils(context);
		}
		return instance;
	}
	
	public SharedPreferencesUtils(Context contex) {
		sharedPreferences = contex.getSharedPreferences(CommonUtils.SHARED_NAME, Context.MODE_PRIVATE);
	}
	
	public void putInt(String key, int value){
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putInt(key, value);
		editor.commit();
	}
	
	public void putString(String key, String value){
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	public String getString(String key, String defauleValue) {
		return sharedPreferences.getString(key, defauleValue);
	}
	
	public int getInteger(String key, int defauleValue) {
		return sharedPreferences.getInt(key, defauleValue);
	}
}
