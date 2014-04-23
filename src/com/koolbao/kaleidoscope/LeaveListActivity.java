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
import com.koolbao.kaleidoscope.adapter.LeaveAdapter;
import com.koolbao.kaleidoscope.backend.Backend;
import com.koolbao.kaleidoscope.model.LeaveInfo;
import com.koolbao.kaleidoscope.model.back.LeaveListBack;
import com.koolbao.kaleidoscope.utils.SharedPreferencesUtils;
import com.koolbao.kaleidoscope.utils.ToastUtils;
import com.koolbao.kaleidoscope.widget.XListView;
import com.koolbao.kaleidoscope.widget.XListView.IXListViewListener;

import android.os.Bundle;
import android.util.Log;

public class LeaveListActivity extends BaseActivity implements IXListViewListener {
	private XListView leave_lv;
	private LeaveAdapter adapter;
	
	private String lastRefreshTime = "刚刚";	
	private int page_current = 0;
	private String page_avg = "10";
	
	private Callback<LeaveListBack> leaveListCallBack = new Callback<LeaveListBack>() {
		
		@Override
		public void success(LeaveListBack leaveListBack, Response arg1) {
			Log.i("test", leaveListBack.toString());
			if (leaveListBack.page_current == 1) {
				new Delete().from(LeaveInfo.class).execute();
			}
			
			//批量插入
			ActiveAndroid.beginTransaction();
			try {
				for (LeaveInfo leave : leaveListBack.tabledata) {
					leave.save();
				}
				ActiveAndroid.setTransactionSuccessful();
			} finally {
				ActiveAndroid.endTransaction();
			}
			
			int position = leave_lv.getFirstVisiblePosition();
			initList();
			leave_lv.setSelection(position);
			leave_lv.setPullLoadEnable(leaveListBack.page_current < leaveListBack.page_count);
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
		setContentView(R.layout.activity_leavelist);
		
		leave_lv = (XListView) findViewById(R.id.leave_lv);
		leave_lv.setPullLoadEnable(false);
		leave_lv.setXListViewListener(this);
		
		initList();
		onRefresh();
	}
	
	private void initList() {
		List<LeaveInfo> leaveList = new Select().from(LeaveInfo.class)
				.where("employee_id = ?", SharedPreferencesUtils.with(this).getString("employee_id", ""))
				.orderBy("start_time DESC, end_time DESC").execute();
		
		adapter = new LeaveAdapter(this, leaveList);
		leave_lv.setAdapter(adapter);
		lastRefreshTime = DateFormatUtils.format(new Date(), "yyyy/MM/dd HH:mm:ss");
		onLoad();
	}
	
	private void onLoad() {
		leave_lv.stopRefresh();
		leave_lv.stopLoadMore();
		leave_lv.setRefreshTime(lastRefreshTime);
	}
	
	@Override
	public void onRefresh() {
		page_current = 0;
		String employee_id = SharedPreferencesUtils.with(this).getString("employee_id", null);
		Backend.getInstance().postLeaveList(employee_id, page_avg, String.valueOf(++page_current), leaveListCallBack);
	}

	@Override
	public void onLoadMore() {
		String employee_id = SharedPreferencesUtils.with(this).getString("employee_id", null);
		Backend.getInstance().postLeaveList(employee_id, page_avg, String.valueOf(++page_current), leaveListCallBack);
	}
}
