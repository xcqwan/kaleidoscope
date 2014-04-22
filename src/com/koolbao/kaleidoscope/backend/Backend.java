package com.koolbao.kaleidoscope.backend;

import java.util.Map;

import com.koolbao.kaleidoscope.model.back.AttendanceBack;
import com.koolbao.kaleidoscope.model.back.AttendanceListBack;
import com.koolbao.kaleidoscope.model.back.EmployeeBack;
import com.koolbao.kaleidoscope.model.back.LeaveBack;
import com.koolbao.kaleidoscope.model.back.LeaveListBack;
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
		restAdapter = new RestAdapter.Builder().setEndpoint(
				CommonUtils.DEFAULT_SERVER).build();
		restAdapter.setLogLevel(LogLevel.FULL);
		serverInstance = restAdapter.create(Server.class);
	}

	public void postLoginForm(String nickname, String password,
			Callback<EmployeeBack> callback) {
		serverInstance.postLogin(nickname, password, callback);
	}

	public void postAttendance(String date, String employee_id, String content,
			Callback<AttendanceBack> callback) {
		serverInstance.postAttendance(date, employee_id, content, callback);
	}

	public void postAttendanceList(String employee_id, String page_avg,
			String page_current, Callback<AttendanceListBack> callback) {
		serverInstance.postAttendanceList(employee_id, page_avg, page_current,
				callback);
	}

	public void postLeaveForm(Map<String, String> form,
			Callback<LeaveBack> callback) {
		String employee_id = form.get("employee_id");
		String start_time = form.get("start_time");
		String end_time = form.get("end_time");
		String tel = form.get("tel");
		String duration = form.get("duration");
		String type = form.get("type");
		String reason = form.get("reason");
		serverInstance.postLeaveForm(employee_id, start_time, end_time, tel,
				duration, type, reason, callback);
	}
	
	public void postLeaveList(String employee_id, String page_avg,
			String page_current, Callback<LeaveListBack> callback) {
		serverInstance.postLeaveList(employee_id, page_avg, page_current, callback);
	}

	interface Server {
		@FormUrlEncoded
		@POST("/staff_management_app/login")
		void postLogin(@Field("nickname") String nickname,
				@Field("password") String password,
				Callback<EmployeeBack> callback);

		@FormUrlEncoded
		@POST("/staff_management_app/attendance")
		void postAttendance(@Field("date") String date,
				@Field("employee_id") String employee_id,
				@Field("content") String content,
				Callback<AttendanceBack> callback);

		@FormUrlEncoded
		@POST("/staff_management_app/attendance_list")
		void postAttendanceList(@Field("employee_id") String employee_id,
				@Field("page_avg") String page_avg,
				@Field("page_current") String page_current,
				Callback<AttendanceListBack> callback);

		@FormUrlEncoded
		@POST("/staff_management_app/leave")
		void postLeaveForm(@Field("employee_id") String employee_id,
				@Field("start_time") String start_time,
				@Field("end_time") String end_time, @Field("tel") String tel,
				@Field("duration") String duration, @Field("type") String type,
				@Field("reason") String reason, Callback<LeaveBack> callback);
		
		@FormUrlEncoded
		@POST("/staff_management_app/leave_list")
		void postLeaveList(@Field("employee_id") String employee_id,
				@Field("page_avg") String page_avg,
				@Field("page_current") String page_current,
				Callback<LeaveListBack> callback);
	}
}
