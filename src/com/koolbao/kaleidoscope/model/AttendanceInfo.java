package com.koolbao.kaleidoscope.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "attendance_info")
public class AttendanceInfo extends Model {
	// 职工ID
	@Column(name = "employee_id")
	public String employee_id;
	// 日期
	@Column(name = "date")
	public String date;
	// 上班时间
	@Column(name = "on_duty")
	public String on_duty;
	// 下班时间
	@Column(name = "off_duty")
	public String off_duty;
	// 类型
	@Column(name = "type")
	public String type;
}
