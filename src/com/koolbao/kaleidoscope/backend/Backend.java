package com.koolbao.kaleidoscope.backend;

import com.koolbao.kaleidoscope.model.back.EmployeeBack;
import com.koolbao.kaleidoscope.utils.CommonUtils;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public class Backend {
	private static Backend singlton = null;
	private RestAdapter restAdapter;

	private Server serverInstance;

	public static Backend getInstance() {
		if (singlton == null) {
			singlton = new Backend();
		}
		return singlton;
	}

	private Backend() {
		restAdapter = new RestAdapter.Builder().setEndpoint(CommonUtils.DEFAULT_SERVER).build();
		restAdapter.setLogLevel(LogLevel.FULL);
		serverInstance = restAdapter.create(Server.class);
	}
	
	public void postLoginForm(String nickname, String password, Callback<EmployeeBack> callback) {
		serverInstance.postLogin(nickname, password, callback);
	}
	
	interface Server {
		@FormUrlEncoded
		@POST("/staff_management_app/login")
		void postLogin(@Field("nickname") String nickname, @Field("password") String password, Callback<EmployeeBack> callback);
	}
}
