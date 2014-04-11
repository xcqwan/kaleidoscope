package com.koolbao.kaleidoscope;

import com.koolbao.kaleidoscope.utils.SharedPreferencesUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ContentActivity extends Activity implements OnClickListener {
	private TextView user_nick_tv;
	private Button duty_btn;
	private Button duty_record_btn;
	private Button leave_btn;
	private Button leave_record_btn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_content);
		
		initCustom();
		initListener();
	}

	private void initListener() {
		duty_btn.setOnClickListener(this);
		duty_record_btn.setOnClickListener(this);
		leave_btn.setOnClickListener(this);
		leave_record_btn.setOnClickListener(this);
	}

	private void initCustom() {
		user_nick_tv = (TextView) findViewById(R.id.user_nick_tv);
		duty_btn = (Button) findViewById(R.id.duty_btn);
		duty_record_btn = (Button) findViewById(R.id.duty_record_btn);
		leave_btn = (Button) findViewById(R.id.leave_btn);
		leave_record_btn = (Button) findViewById(R.id.leave_record_btn);
		
		user_nick_tv.setText(SharedPreferencesUtils.with(this).getString("user_nick", "酷宝数据"));
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		if (v.getId() == duty_btn.getId()) {
			intent.setClass(this, AttendanceActivity.class);
		}
		if (v.getId() == duty_record_btn.getId()) {
			intent.setClass(this, ContentActivity.class);
		}
		if (v.getId() == leave_btn.getId()) {
			intent.setClass(this, ContentActivity.class);
		}
		if (v.getId() == leave_record_btn.getId()) {
			intent.setClass(this, ContentActivity.class);
		}
		startActivity(intent);
		overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
	}
}
