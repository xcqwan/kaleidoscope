package com.koolbao.kaleidoscope.adapter;

import java.util.List;
import com.koolbao.kaleidoscope.R;
import com.koolbao.kaleidoscope.model.AttendanceInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AttendanceAdapter extends BaseAdapter {
	Context mContext;
	List<AttendanceInfo> mDatas;
	
	public AttendanceAdapter(Context context, List<AttendanceInfo> data) {
		mContext = context;
		mDatas = data;
	}
	
	@Override
	public int getCount() {
		return mDatas.size();
	}

	@Override
	public Object getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_attendance, null);
			holder = new ViewHolder();
			holder.time_tv = (TextView) convertView.findViewById(R.id.time_tv);
			holder.type_tv = (TextView) convertView.findViewById(R.id.type_tv);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		AttendanceInfo attendance = mDatas.get(position);
		
		holder.type_tv.setText(attendance.type);
		String time = "";
		String date = attendance.date;
		if (attendance.on_duty.isEmpty() || attendance.on_duty.equals("00:00:00")) {
			time = attendance.off_duty;
			holder.type_tv.setTextColor(mContext.getResources().getColor(R.color.kb_blue));
		} else {
			time = attendance.on_duty;
			holder.type_tv.setTextColor(mContext.getResources().getColor(R.color.kb_yellow));
		}
		holder.time_tv.setText(date + " " + time);
		
		return convertView;
	}
	
	public static class ViewHolder {
		TextView time_tv;
		TextView type_tv;
	}
}
