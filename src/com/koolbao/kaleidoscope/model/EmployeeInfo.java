package com.koolbao.kaleidoscope.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "employee_info")
public class EmployeeInfo extends Model {
	// 职工ID
	@Column(name = "employee_id")
	public int _employee_id;
	// 姓名
	@Column(name = "name")
	public String _name;
	/*// 花名
	@Column(name = "nickname")
	public String _nickname;
	// 密码
	@Column(name = "password")
	public String _password;
	// 性别
	@Column(name = "sex")
	public String _sex;
	// 籍贯
	@Column(name = "native")
	public String _native;
	// 政治面貌
	@Column(name = "politics")
	public String _politics;
	// 生日
	@Column(name = "birthday")
	public String _birthday;
	// 手机
	@Column(name = "tel")
	public String _tel;
	// QQ
	@Column(name = "qq")
	public String _qq;
	// 个人邮箱
	@Column(name = "company_email")
	public String _personal_email;
	// 公司邮箱
	@Column(name = "company_email")
	public String _company_email;
	// 实习到岗日期
	@Column(name = "internship_start")
	public String _internship_start;
	// 试用到岗日期
	@Column(name = "probation_start")
	public String _probation_start;
	// 转正日期
	@Column(name = "regular_worker_date")
	public String _regular_worker_date;
	// 职位
	@Column(name = "position")
	public String _position;
	// 所属部门
	@Column(name = "department")
	public String _department;
	// 离职日期
	@Column(name = "dimission_date")
	public String _dimission_date;
	// 录入时间
	@Column(name = "create_time")
	public String _create_time;*/
	
	@Override
	public String toString() {
		return _employee_id + ", " + _name;
	}
}
