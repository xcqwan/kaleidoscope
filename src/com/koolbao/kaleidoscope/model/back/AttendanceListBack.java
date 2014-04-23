package com.koolbao.kaleidoscope.model.back;

import java.util.List;

import com.koolbao.kaleidoscope.model.AttendanceInfo;

public class AttendanceListBack {
	public int page_avg;
	public int page_count;
	public int page_current;
	public List<AttendanceInfo> tabledata;
	
	@Override
	public String toString() {
		return "page_avg : " + page_avg + ", page_count : " + page_count + ", page_current : " + page_current + ", tabledata : " + tabledata;
	}
}
