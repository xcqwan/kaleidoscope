package com.koolbao.kaleidoscope;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.koolbao.kaleidoscope.backend.Backend;
import com.koolbao.kaleidoscope.model.LeaveInfo;
import com.koolbao.kaleidoscope.model.back.LeaveBack;
import com.koolbao.kaleidoscope.utils.CommonUtils;
import com.koolbao.kaleidoscope.utils.SharedPreferencesUtils;
import com.koolbao.kaleidoscope.utils.ToastUtils;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LeaveFormActivity extends BaseActivity {
	private EditText start_date_et;
	private EditText end_date_et;
	private EditText tel_et;
	private EditText duration_et;
	private EditText type_et;
	private EditText reason_et;
	private Button submit_btn;
	
	private Callback<LeaveBack> leaveCallBack = new Callback<LeaveBack>() {
		
		@Override
		public void success(LeaveBack leaveBack, Response arg1) {
			Log.i("test", leaveBack.toString());
			if (leaveBack.status == 0) {
				LeaveInfo leave = leaveBack.getLeave();
				leave.save();
				
				Intent intent = new Intent(LeaveFormActivity.this, LeaveListActivity.class);
				startActivity(intent);
				LeaveFormActivity.this.finish();
				overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
			} else {
				ToastUtils.AlarmDIY(getApplicationContext(), "提交请假条失败，请稍后再试！");
			}
		}
		
		@Override
		public void failure(RetrofitError arg0) {
			ToastUtils.errorNetWork(getApplicationContext());
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_leaveform);
		
		initCustom();
		initListener();
	}

	private void initListener() {
		submit_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Map<String, String> form = checkForm();
				if (form != null) {
					Log.i("test", form.toString());
					Backend.getInstance().postLeaveForm(form, leaveCallBack);
				}
			}
		});
	}
	
	private Map<String, String> checkForm() {
		Map<String, String> form = new HashMap<String, String>();
		String start_date = start_date_et.getText().toString();
		String end_date = end_date_et.getText().toString();
		String tel = tel_et.getText().toString();
		String duration = duration_et.getText().toString();
		String type = type_et.getText().toString();
		String reason = reason_et.getText().toString();
		
		Pattern pattern = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}");
		Matcher matcher = pattern.matcher(start_date);
		boolean dateFlag = matcher.matches();
		if (!dateFlag) {
			ToastUtils.AlarmDIY(this, "开始时间格式错误");
			return null;
		}
		matcher = pattern.matcher(end_date);
		dateFlag = matcher.matches();
		if (!dateFlag) {
			ToastUtils.AlarmDIY(this, "结束时间格式错误");
			return null;
		}
		if (tel.length() != 11 || !StringUtils.isNumeric(tel)) {
			ToastUtils.AlarmDIY(this, "紧急联系电话错误");
			return null;
		}
		if (duration.isEmpty()) {
			ToastUtils.AlarmDIY(this, "请假时长错误");
			return null;
		}
		
		int i;
		String[] type_arr = CommonUtils.type_arr;
		for ( i= 1; i < type_arr.length; i++) {
			if (type.equals(type_arr[i])) {
				break;
			}
		}
		if (i >= type_arr.length) {
			ToastUtils.AlarmDIY(this, "请假类型错误");
			return null;
		}
		if (reason.isEmpty()) {
			ToastUtils.AlarmDIY(this, "请假原因不得为空");
			return null;
		}
		
		form.put("start_time", start_date);
		form.put("end_time", end_date);
		form.put("tel", tel);
		form.put("duration", duration);
		form.put("type", i + "");
		form.put("reason", reason);
		form.put("employee_id", SharedPreferencesUtils.with(this).getString("employee_id", ""));
		
		return form;
	}

	private void initCustom() {
		start_date_et = (EditText) findViewById(R.id.start_date_et);
		end_date_et = (EditText) findViewById(R.id.end_date_et);
		tel_et = (EditText) findViewById(R.id.tel_et);
		duration_et = (EditText) findViewById(R.id.duration_et);
		type_et = (EditText) findViewById(R.id.type_et);
		reason_et = (EditText) findViewById(R.id.reason_et);
		submit_btn = (Button) findViewById(R.id.submit_btn);
	}
}
