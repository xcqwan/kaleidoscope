package com.koolbao.kaleidoscope;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListView;

public class AttendanceActivity extends ListActivity {
	ListView mListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mListView = getListView();
	}
}
