package com.koolbao.kaleidoscope.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "employee_info")
public class EmployeeInfo extends Model {
	// 职工ID
	@Column(name = "employee_id")
	public String employee_id;
	// 姓名
	@Column(name = "name")
	public String name;
	// 花名
	@Column(name = "nickname")
	public String nickname;
	// 密码
	@Column(name = "password")
	public String password;
	// 性别
	@Column(name = "sex")
	public String sex;
	// 籍贯
	@Column(name = "native_place")
	public String native_place;
	// 政治面貌
	@Column(name = "politics")
	public String politics;
	// 生日
	@Column(name = "birthday")
	public String birthday;
	// 手机
	@Column(name = "tel")
	public String tel;
	// QQ
	@Column(name = "qq")
	public String qq;
	// 个人邮箱
	@Column(name = "personal_email")
	public String personal_email;
	// 公司邮箱
	@Column(name = "company_email")
	public String company_email;
	// 实习到岗日期
	@Column(name = "internship_start")
	public String internship_start;
	// 试用到岗日期
	@Column(name = "probation_start")
	public String probation_start;
	// 转正日期
	@Column(name = "regular_worker_date")
	public String regular_worker_date;
	// 职位
	@Column(name = "position")
	public String position;
	// 所属部门
	@Column(name = "department")
	public String department;
	// 离职日期
	@Column(name = "dimission_date")
	public String dimission_date;
	// 录入时间
	@Column(name = "create_time")
	public String create_time;
	
	@Override
	public String toString() {
		return employee_id + ", " + name;
	}
}
