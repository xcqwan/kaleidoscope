package com.koolbao.kaleidoscope.event;

public class ASyncCallBackEvent {
	public final int action;
	public final String resp;
	
	public ASyncCallBackEvent(int action, String resp) {
		this.action = action;
		this.resp = resp;
	}
	
	@Override
	public String toString() {
		return new StringBuilder("Action : ")
				.append(action)
				.append(", ")
				.append("Resp : ")
				.append(resp)
				.toString();
	}
}
