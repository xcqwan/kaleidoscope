package com.koolbao.kaleidoscope;

import com.koolbao.kaleidoscope.utils.BusProvider;

import android.app.ListActivity;
import android.os.Bundle;

public class BaseListActivity extends ListActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
}
