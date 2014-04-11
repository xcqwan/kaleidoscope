package com.koolbao.kaleidoscope;

import com.koolbao.kaleidoscope.NewsClient.Letter;
import com.koolbao.kaleidoscope.NewsClient.NewsLetterResp;
import com.koolbao.kaleidoscope.utils.ToastUtils;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AttendanceActivity extends ListActivity {
	ListView mListView;
	Callback<NewsLetterResp> callback = new Callback<NewsLetterResp>() {

		@Override
		public void success(NewsLetterResp resp, Response response) {
			Log.i("test", resp.toString());
			ArrayAdapter<Letter> adapter = new ArrayAdapter<NewsClient.Letter>(
					getApplicationContext(),
					android.R.layout.simple_list_item_1, resp.tabledata);
			mListView.setAdapter(adapter);
		}

		@Override
		public void failure(RetrofitError error) {
			ToastUtils.errorNetWork(AttendanceActivity.this);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mListView = getListView();
		NewsClient.getNews(callback);
	}
}
