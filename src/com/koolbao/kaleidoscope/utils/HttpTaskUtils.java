package com.koolbao.kaleidoscope.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import android.util.Log;

public class HttpTaskUtils {
	
	/**
	 * HttpPost请求
	 * @param path
	 * 		访问地址
	 * @param param
	 * 		参数
	 * @return
	 * @throws Exception
	 */
	public static String requestByHttpPost(String path, Map<String, String> param) throws Exception {
		// 新建HttpPost对象
		HttpPost httpPost = new HttpPost(path);
		// Post参数
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		for (String key : param.keySet()) {	
			params.add(new BasicNameValuePair(key, param.get(key)));
		}
		// 设置字符集
		HttpEntity entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
		// 设置参数实体
		httpPost.setEntity(entity);
		// 获取HttpClient对象
		HttpClient httpClient = new DefaultHttpClient();
		 // 请求超时
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
        // 读取超时
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 5000);
		int try_times = 0;
		while (try_times < 3) {
			// 获取HttpResponse实例
			HttpResponse httpResp = httpClient.execute(httpPost);
			// 判断是够请求成功
			if (httpResp.getStatusLine().getStatusCode() == 200) {
				// 获取返回的数据
				HttpEntity respEntity = httpResp.getEntity();
				String resp = EntityUtils.toString(respEntity, "UTF-8");
				return resp;
			} else {
				Log.i("test", "HttpPost方式请求失败" + httpResp.getStatusLine().getStatusCode());
				try_times ++;
			}
		}
		return null;
	}
}
