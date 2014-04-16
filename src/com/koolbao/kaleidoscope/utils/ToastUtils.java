package com.koolbao.kaleidoscope.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {
	
	public static void errorNetWork(Context context) {
		Toast.makeText(context, "网络请求失败, 请查看网络连接或者稍后再试！", Toast.LENGTH_SHORT).show();
	}
	
	public static void errorFormPut(Context context) {
		Toast.makeText(context, "花名或密码输入错误！", Toast.LENGTH_SHORT).show();
	}
}
