package com.koolbao.kaleidoscope.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "leave_info")
public class LeaveInfo extends Model {
	// 职工ID
	@Column(name = "employee_id")
	public String employee_id;
	// 开始时间
	@Column(name = "start_time")
	public String start_time;
	// 结束时间
	@Column(name = "end_time")
	public String end_time;
	// 紧急联系电话
	@Column(name = "tel")
	public String tel;
	// 请假时长
	@Column(name = "duration")
	public String duration;
	// 请假类型
	@Column(name = "type")
	public String type;
	// 请假原因
	@Column(name = "reason")
	public String reason;
}
