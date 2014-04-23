package com.koolbao.kaleidoscope.model.back;

import com.koolbao.kaleidoscope.model.LeaveInfo;

public class LeaveBack {
	public String employee_id;
	public String start_time;
	public String end_time;
	public String tel;
	public String duration;
	public String type;
	public String reason;
	public int status;
	
	public LeaveInfo getLeave() {
		LeaveInfo leave = new LeaveInfo();
		leave.employee_id = employee_id;
		leave.start_time = start_time;
		leave.end_time = end_time;
		leave.tel = tel;
		leave.duration = duration;
		leave.type = type;
		leave.reason = reason;
		return leave;
	}
	
	@Override
	public String toString() {
		return "status : " + status + ", employee_id : " + employee_id + ", start_time : " + start_time + ", end_time : " + end_time + ", tel : " + tel + ", duration : " + duration + ", type : " + type + ", reason : " + reason;
	}
}
