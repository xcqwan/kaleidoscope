package com.koolbao.kaleidoscope;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.koolbao.kaleidoscope.backend.Backend;
import com.koolbao.kaleidoscope.event.NeedMaskASyncEndEvent;
import com.koolbao.kaleidoscope.event.NeedMaskASyncStartEvent;
import com.koolbao.kaleidoscope.model.AttendanceInfo;
import com.koolbao.kaleidoscope.model.back.AttendanceBack;
import com.koolbao.kaleidoscope.utils.BusProvider;
import com.koolbao.kaleidoscope.utils.CommonUtils;
import com.koolbao.kaleidoscope.utils.SharedPreferencesUtils;
import com.koolbao.kaleidoscope.utils.ToastUtils;
import com.squareup.otto.Subscribe;
import com.zombie.wonhot.LayoutConstants;
import com.zombie.wonhot.WonhotLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

public class ContentActivity extends BaseActivity implements OnClickListener {
	private WonhotLayout wonhot;
	private TextView user_nick_tv;
	private int duty_btn;
	private int duty_record_btn;
	private int leave_btn;
	private int leave_record_btn;
	private ProgressDialog progressDialog;
	
	private String content;
	
	Callback<AttendanceBack> attendanceCallBack = new Callback<AttendanceBack>() {
		
		@Override
		public void success(AttendanceBack attendanceBack, Response arg1) {
			BusProvider.getInstance().post(new NeedMaskASyncEndEvent());
			Log.i("test", attendanceBack.toString());
			switch (attendanceBack.status) {
			case 0:
				AttendanceInfo attendanceinfo = attendanceBack.getAttendance();
				attendanceinfo.save();
				
				ToastUtils.AlarmDIY(getApplicationContext(), attendanceinfo.type + "打卡成功!");
				break;
			case 1:
				ToastUtils.AlarmDIY(getApplicationContext(), "请扫描打卡专用二维码！");
				break;
			case 2:
				ToastUtils.AlarmDIY(getApplicationContext(), "重复打卡，不作死就不会死！");
				break;
			case 3:
				ToastUtils.AlarmDIY(getApplicationContext(), "打卡失败，今日已累计打卡成功2次！");
				break;
			default:
				ToastUtils.AlarmDIY(getApplicationContext(), "扫码失败，请稍后再试！");
				break;
			}
		}
		
		@Override
		public void failure(RetrofitError arg0) {
			BusProvider.getInstance().post(new NeedMaskASyncEndEvent());
			ToastUtils.errorNetWork(getApplicationContext());
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_content);
		
		initCustom();
	}

	private void initCustom() {
		user_nick_tv = (TextView) findViewById(R.id.user_nick_tv);
		
		wonhot = new WonhotLayout(this);
		wonhot.setAlignCode(LayoutConstants.CENTERBOTTOM);
		wonhot.setCenterStyle(R.drawable.composer_button, R.drawable.composer_icn_plus);
		duty_btn = wonhot.addItem(R.drawable.composer_camera);
		duty_record_btn = wonhot.addItem(R.drawable.composer_place);
		leave_btn = wonhot.addItem(R.drawable.composer_sleep);
		leave_record_btn = wonhot.addItem(R.drawable.composer_thought);
		
		Point wSize = new Point();
		getWindowManager().getDefaultDisplay().getSize(wSize);
		wonhot.init(wSize.x / 2 - 50, 500, 0);
		wonhot.setButtonsOnClickListener(this);
		addContentView(wonhot, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		
		user_nick_tv.setText("欢迎你，" + SharedPreferencesUtils.with(this).getString("nickname", "酷宝数据"));
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == duty_btn) {
//			startActivity(new Intent(ContentActivity.this, IntegralWallActivity.class));
//			wonhot.animate().setDuration(1000).x(0).y(20);
			Intent intent = new Intent(ContentActivity.this, CaptureActivity.class);
			startActivityForResult(intent, CommonUtils.SCAN_CALLBACK_CODE);
		}
		if (v.getId() == duty_record_btn) {
			Intent intent = new Intent(ContentActivity.this, AttendanceActivity.class);
			startActivity(intent);
		}
		if (v.getId() == leave_btn) {
			Intent intent = new Intent(ContentActivity.this, LeaveFormActivity.class);
			startActivity(intent);
		}
		if (v.getId() == leave_record_btn) {
			Intent intent = new Intent(ContentActivity.this, LeaveListActivity.class);
			startActivity(intent);
		}
		overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CommonUtils.SCAN_CALLBACK_CODE && resultCode == RESULT_OK) {
			content = data.getStringExtra("result");
			
			//待解决问题，这里无法产生事件
//			BusProvider.getInstance().post(new NeedMaskASyncStartEvent());
			if (progressDialog == null) {
				progressDialog = new ProgressDialog(this);
			}
			
			String employee_id = SharedPreferencesUtils.with(this).getString("employee_id", null);
			Backend.getInstance().postAttendance(employee_id, content, attendanceCallBack);
			
			progressDialog.show();
		}
	}
	
	@Subscribe
	public void onNeedMaskASyncStart(NeedMaskASyncStartEvent event) {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(this);
		}
		
		String employee_id = SharedPreferencesUtils.with(this).getString("employee_id", null);
		Backend.getInstance().postAttendance(employee_id, content, attendanceCallBack);
		
		progressDialog.show();
	}
	
	@Subscribe
	public void onNeedMaskASyncEnd(NeedMaskASyncEndEvent event) {
		if (progressDialog != null) {
			progressDialog.dismiss();
		}
	}
}
