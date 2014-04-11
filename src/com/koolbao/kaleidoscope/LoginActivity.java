package com.koolbao.kaleidoscope;

import java.util.HashMap;
import java.util.Map;

import com.koolbao.kaleidoscope.event.ASyncCallBackEvent;
import com.koolbao.kaleidoscope.event.ASyncPreExecuteEvent;
import com.koolbao.kaleidoscope.utils.ASyncTaskUtils;
import com.koolbao.kaleidoscope.utils.CommonUtils;
import com.koolbao.kaleidoscope.utils.ToastUtils;
import com.squareup.otto.Subscribe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener {
	private EditText user_nick_et;
	private EditText phone_et;
	private Button submit_btn;
	
	private ASyncTaskUtils asyncTask; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		 initCustom();
		 initListener();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		BusProvider.getInstance().register(this);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		BusProvider.getInstance().unregister(this);
		
		if (asyncTask != null) {
			asyncTask.cancel(true);
			asyncTask = null;
		}
	}
	
	private void initListener() {
		submit_btn.setOnClickListener(this);
	}

	private void initCustom() {
		user_nick_et = (EditText) findViewById(R.id.user_nick_et);
		phone_et = (EditText) findViewById(R.id.phone_et);
		submit_btn = (Button) findViewById(R.id.submit_btn);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == submit_btn.getId()) {
			if (checkForm()) {
				Map<String, String> param = new HashMap<String, String>();
				param.put("tel", phone_et.getText().toString());
				param.put("password", user_nick_et.getText().toString());
				
				if (asyncTask != null) {
					asyncTask.cancel(true);
				}
				asyncTask = new ASyncTaskUtils(CommonUtils.LOGIN_URL, CommonUtils.LOGIN_ACTION, param);
				asyncTask.execute();
			}
		}
	}
	
	@Subscribe
	public void onASyncPreExecute(ASyncPreExecuteEvent event) {
		Log.i("test", "need mask");
	}
	
	@Subscribe
	public void onASyncCallBack(ASyncCallBackEvent event) {
		if (event.action != CommonUtils.LOGIN_ACTION) {
			return;
		}
		String resultJSON = event.resp;
		if (resultJSON == null) {
			ToastUtils.errorNetWork(LoginActivity.this);
			return;
		}
		Log.i("test", event.toString());
		//处理登录请求结果
		
		startActivity(new Intent(LoginActivity.this, ContentActivity.class));
		overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
		finish();
	}

	private boolean checkForm() {
		String user_nick = user_nick_et.getText().toString();
		String tel = phone_et.getText().toString();
		if (user_nick.isEmpty() || tel.isEmpty()) {
			Toast.makeText(this, "花名或手机号不能为空", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}
}
