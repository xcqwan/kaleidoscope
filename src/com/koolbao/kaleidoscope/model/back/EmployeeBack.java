package com.koolbao.kaleidoscope.model.back;

import com.koolbao.kaleidoscope.model.EmployeeInfo;

public class EmployeeBack {
	String employee_id;
	String name;
	String nickname;
	String password;
	String sex;
	String native_place;
	String politics;
	String birthday;
	String tel;
	String qq;
	String personal_email;
	String company_email;
	String internship_start;
	String probation_start;
	String regular_worker_date;
	String position;
	String department;
	String dimission_date;
	String create_time;
	public int status;
	
	public EmployeeInfo getEmployee() {
		EmployeeInfo employee = new EmployeeInfo();
		employee.employee_id = employee_id;
		employee.name = name;
		employee.nickname = nickname;
		employee.password = password;
		employee.sex = sex;
		employee.native_place = native_place;
		employee.politics = politics;
		employee.birthday = birthday;
		employee.tel = tel;
		employee.qq = qq;
		employee.personal_email = personal_email;
		employee.company_email = company_email;
		employee.internship_start = internship_start;
		employee.probation_start = probation_start;
		employee.regular_worker_date = regular_worker_date;
		employee.position = position;
		employee.department = department;
		employee.dimission_date = dimission_date;
		employee.create_time = create_time;
		
		return employee;
	}
}
