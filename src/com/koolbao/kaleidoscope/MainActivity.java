package com.koolbao.kaleidoscope;

import java.util.List;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.koolbao.kaleidoscope.model.EmployeeInfo;
import com.koolbao.kaleidoscope.utils.CommonUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		/*//批量插入，使用事物
		ActiveAndroid.beginTransaction();
		try {
			for (int i = 0; i < 10; i++) {
				EmployeeInfo user = new EmployeeInfo();
				user._employee_id = 10 + i;
				user._name = "寿眉" + i;
				user.save();
			}
			ActiveAndroid.setTransactionSuccessful();
		} finally {
			ActiveAndroid.endTransaction();
		}*/
		List<EmployeeInfo> result = new Select().from(EmployeeInfo.class).execute();
		for (EmployeeInfo employeeInfo : result) {
			Log.i("test", employeeInfo.toString());
		}
		
		
		
//		if (SharedPreferencesUtils.with(this).getString("user_id", null) == null) {
//			//未登录
//			mHandler.sendEmptyMessageDelayed(CommonUtils.UNLOGIN, 500);
//		} else {
//			//已登录
//			mHandler.sendEmptyMessageDelayed(CommonUtils.LOGINED, 500);
//		}
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
