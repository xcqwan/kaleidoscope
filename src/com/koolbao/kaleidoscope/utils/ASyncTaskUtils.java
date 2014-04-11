package com.koolbao.kaleidoscope.utils;

import java.util.Map;

import com.koolbao.kaleidoscope.BusProvider;
import com.koolbao.kaleidoscope.event.ASyncCallBackEvent;
import com.koolbao.kaleidoscope.event.ASyncPreExecuteEvent;

import android.os.AsyncTask;
import android.util.Log;

public class ASyncTaskUtils extends AsyncTask<Void, Void, String> {
	private String mUrl;
	private int mFinishAction;
	private Map<String, String> mParam;

	public ASyncTaskUtils(String url, int finishAction, Map<String, String> param) {
		this.mUrl = url;
		this.mFinishAction = finishAction;
		this.mParam = param;
	}
	
	@Override
	protected void onPreExecute() {
		BusProvider.getInstance().post(new ASyncPreExecuteEvent());
	}
	
	@Override
	protected String doInBackground(Void... params) {
		try {
			return HttpTaskUtils.requestByHttpPost(mUrl, mParam);
		} catch (Exception e) {
			Log.i("test", e.getMessage());
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(String result) {
		BusProvider.getInstance().post(new ASyncCallBackEvent(mFinishAction, result));
	}
}
