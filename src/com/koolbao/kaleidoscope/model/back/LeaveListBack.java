package com.koolbao.kaleidoscope.model.back;

import java.util.List;

import com.koolbao.kaleidoscope.model.LeaveInfo;

public class LeaveListBack {
	public int page_avg;
	public int page_count;
	public int page_current;
	public List<LeaveInfo> tabledata;
	
	@Override
	public String toString() {
		return "page_avg : " + page_avg + ", page_count : " + page_count + ", page_current : " + page_current + ", tabledata : " + tabledata;
	}
}
