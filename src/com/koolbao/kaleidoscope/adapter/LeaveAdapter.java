package com.koolbao.kaleidoscope.adapter;

import java.util.List;

import com.koolbao.kaleidoscope.R;
import com.koolbao.kaleidoscope.model.LeaveInfo;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LeaveAdapter extends BaseAdapter {
	private Context mContext;
	private List<LeaveInfo> mDatas;

	public LeaveAdapter(Context context, List<LeaveInfo> data) {
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
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_leave, null);
			holder = new ViewHolder();
			holder.time_tv = (TextView) convertView.findViewById(R.id.time_tv);
			holder.type_tv = (TextView) convertView.findViewById(R.id.type_tv);
			holder.duration_tv = (TextView) convertView.findViewById(R.id.duration_tv);
			holder.reason_tv = (TextView) convertView.findViewById(R.id.reason_tv);
			holder.tel_tv = (TextView) convertView.findViewById(R.id.tel_tv);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		LeaveInfo leave = mDatas.get(position);

		holder.time_tv.setText(leave.start_time + " 至 " + leave.end_time);
		holder.type_tv.setText(leave.type);
		holder.duration_tv.setText(leave.duration + " 天");
		holder.tel_tv.setText(leave.tel);
		holder.reason_tv.setText(leave.reason);

		return convertView;
	}

	public static class ViewHolder {
		TextView time_tv;
		TextView type_tv;
		TextView duration_tv;
		TextView reason_tv;
		TextView tel_tv;
	}
}
