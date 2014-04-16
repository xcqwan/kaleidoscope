package com.koolbao.kaleidoscope;

import com.koolbao.kaleidoscope.utils.CommonUtils;
import com.koolbao.kaleidoscope.utils.SharedPreferencesUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if (SharedPreferencesUtils.with(this).getString("employee_id", null) == null) {
			//未登录
			mHandler.sendEmptyMessageDelayed(CommonUtils.UNLOGIN, 500);
		} else {
			//已登录
			mHandler.sendEmptyMessageDelayed(CommonUtils.LOGINED, 500);
		}
	}
	
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			Intent intent;
			switch (msg.what) {
			case CommonUtils.UNLOGIN:
				intent = new Intent(MainActivity.this, LoginActivity.class);
				break;
			case CommonUtils.LOGINED:
				intent = new Intent(MainActivity.this, ContentActivity.class);
				break; 
			default:
				intent = new Intent(MainActivity.this, LoginActivity.class);
				break;
			}
			startActivity(intent);
			overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
			finish();
			super.handleMessage(msg);
		};
	};
}
