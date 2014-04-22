package com.koolbao.kaleidoscope;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.koolbao.kaleidoscope.adapter.AttendanceAdapter;
import com.koolbao.kaleidoscope.backend.Backend;
import com.koolbao.kaleidoscope.model.AttendanceInfo;
import com.koolbao.kaleidoscope.model.back.AttendanceListBack;
import com.koolbao.kaleidoscope.utils.SharedPreferencesUtils;
import com.koolbao.kaleidoscope.utils.ToastUtils;
import com.koolbao.kaleidoscope.widget.XListView;
import com.koolbao.kaleidoscope.widget.XListView.IXListViewListener;
import android.os.Bundle;
import android.util.Log;

public class AttendanceActivity extends BaseActivity implements IXListViewListener {
	private XListView attendance_lv;
	private AttendanceAdapter adapter;
	
	private String lastRefreshTime = "刚刚";	
	private int page_current = 0;
	private String page_avg = "10";
	
	private Callback<AttendanceListBack> attendanceListCallBack = new Callback<AttendanceListBack>() {
		
		@Override
		public void success(AttendanceListBack attendanceListBack, Response arg1) {
			Log.i("test", attendanceListBack.toString());
			if (attendanceListBack.page_current == 1) {
				new Delete().from(AttendanceInfo.class).execute();
			}
			
			//批量插入
			ActiveAndroid.beginTransaction();
			try {
				for (AttendanceInfo attendance : attendanceListBack.tabledata) {
					attendance.save();
				}
				ActiveAndroid.setTransactionSuccessful();
			} finally {
				ActiveAndroid.endTransaction();
			}
			
			int position = attendance_lv.getFirstVisiblePosition();
			initList();
			attendance_lv.setSelection(position);
			attendance_lv.setPullLoadEnable(attendanceListBack.page_current < attendanceListBack.page_count);
			onLoad();
		}
		
		@Override
		public void failure(RetrofitError arg0) {
			ToastUtils.errorNetWork(getApplicationContext());
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_attendance);
		
		attendance_lv = (XListView) findViewById(R.id.attendance_lv);
		attendance_lv.setPullLoadEnable(false);
		attendance_lv.setXListViewListener(this);
		
		initList();
		onRefresh();
	}

	private void initList() {
		List<AttendanceInfo> attendanceList = new Select().from(AttendanceInfo.class)
				.where("employee_id = ?", SharedPreferencesUtils.with(this).getString("employee_id", ""))
				.orderBy("date DESC, off_duty DESC").execute();
		
		adapter = new AttendanceAdapter(this, attendanceList);
		attendance_lv.setAdapter(adapter);
		lastRefreshTime = DateFormatUtils.format(new Date(), "yyyy/MM/dd HH:mm:ss");
		onLoad();
	}
	
	private void onLoad() {
		attendance_lv.stopRefresh();
		attendance_lv.stopLoadMore();
		attendance_lv.setRefreshTime(lastRefreshTime);
	}
	
	@Override
	public void onRefresh() {
		page_current = 0;
		String employee_id = SharedPreferencesUtils.with(this).getString("employee_id", null);
		Backend.getInstance().postAttendanceList(employee_id, page_avg, String.valueOf(++page_current), attendanceListCallBack);
	}

	@Override
	public void onLoadMore() {
		String employee_id = SharedPreferencesUtils.with(this).getString("employee_id", null);
		Backend.getInstance().postAttendanceList(employee_id, page_avg, String.valueOf(++page_current), attendanceListCallBack);
	}
}
