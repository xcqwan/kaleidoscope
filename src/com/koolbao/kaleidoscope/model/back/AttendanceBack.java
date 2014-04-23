package com.koolbao.kaleidoscope.model.back;

import com.koolbao.kaleidoscope.model.AttendanceInfo;

public class AttendanceBack {
	String employee_id;
	String date;
	String on_duty;
	String off_duty;
	String type;
	public int status;
	
	public AttendanceInfo getAttendance() {
		AttendanceInfo attendance = new AttendanceInfo();
		attendance.employee_id = employee_id;
		attendance.date = date;
		attendance.on_duty = on_duty;
		attendance.off_duty = off_duty;
		attendance.type = type;
		
		return attendance;
	}
	
	@Override
	public String toString() {
		return "employee_id : " + employee_id + ", date : " + date + ", on_duty : " + on_duty + ", off_duty : " + off_duty + ", type : " + type + ", status : " + status;
	}
}
