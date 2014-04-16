package com.koolbao.kaleidoscope;

import retrofit.Callback;

import com.koolbao.kaleidoscope.backend.Backend;
import com.koolbao.kaleidoscope.event.NeedMaskASyncEndEvent;
import com.koolbao.kaleidoscope.event.NeedMaskASyncStartEvent;
import com.koolbao.kaleidoscope.model.EmployeeInfo;
import com.koolbao.kaleidoscope.model.back.EmployeeBack;
import com.koolbao.kaleidoscope.utils.BusProvider;
import com.koolbao.kaleidoscope.utils.SharedPreferencesUtils;
import com.koolbao.kaleidoscope.utils.ToastUtils;
import com.squareup.otto.Subscribe;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener {
	private EditText user_nick_et;
	private EditText psd_et;
	private Button submit_btn;
	private ProgressDialog progressDialog;
	
	private Callback<EmployeeBack> loginCallBack = new Callback<EmployeeBack>() {
		public void success(EmployeeBack employeeBack, retrofit.client.Response arg1) {
			BusProvider.getInstance().post(new NeedMaskASyncEndEvent());
			if (employeeBack.status == 0) {
				EmployeeInfo employeeinfo = employeeBack.getEmployee();
				employeeinfo.save();
				
				SharedPreferencesUtils.with(getApplicationContext()).putString("employee_id", employeeinfo.employee_id);
				SharedPreferencesUtils.with(getApplicationContext()).putString("nickname", employeeinfo.nickname);
				
				Intent intent = new Intent(LoginActivity.this, ContentActivity.class);
				startActivity(intent);
				overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
				LoginActivity.this.finish();
			} else {
				ToastUtils.errorFormPut(getApplicationContext());
			}
		};
		public void failure(retrofit.RetrofitError arg0) {
			BusProvider.getInstance().post(new NeedMaskASyncEndEvent());
			ToastUtils.errorNetWork(getApplicationContext());
		};
	};

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
	}
	
	private void initListener() {
		submit_btn.setOnClickListener(this);
	}

	private void initCustom() {
		user_nick_et = (EditText) findViewById(R.id.user_nick_et);
		psd_et = (EditText) findViewById(R.id.psd_et);
		submit_btn = (Button) findViewById(R.id.submit_btn);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == submit_btn.getId()) {
			if (checkForm()) {
				BusProvider.getInstance().post(new NeedMaskASyncStartEvent());
			}
		}
	}
	
	@Subscribe
	public void onNeedMaskASyncStart(NeedMaskASyncStartEvent event) {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(this);
		}
		
		String nickname = user_nick_et.getText().toString();
		String password = psd_et.getText().toString();
		Backend.getInstance().postLoginForm(nickname, password, loginCallBack);
		
		progressDialog.show();
	}
	
	@Subscribe
	public void onNeedMaskASyncEnd(NeedMaskASyncEndEvent event) {
		if (progressDialog == null) {
			return;
		}
		progressDialog.dismiss();
	}
	
	private boolean checkForm() {
		String user_nick = user_nick_et.getText().toString();
		String psd = psd_et.getText().toString();
		if (user_nick.isEmpty() || psd.isEmpty()) {
			Toast.makeText(this, "花名或密码不能为空", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}
}
