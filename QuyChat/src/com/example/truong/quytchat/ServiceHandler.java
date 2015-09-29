package com.example.truong.quytchat;

import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

public class ServiceHandler {
	static String response = null;
	public final static int GET = 1;
	public final static int POST = 2;

	public ServiceHandler() {
	}

	public String getErrorConver(String response) {
		return response = (response.startsWith("[")) ? response : response.substring(1);
	}

	/**
	 * Making service call
	 * 
	 * @url - url to make request
	 * @method - http request method
	 * */
	public String makeServiceCall(String url, int method) {
		return this.makeServiceCall(url, method, null);
	}

	/**
	 * Making service call
	 * 
	 * @url - url to make request
	 * @method - http request method
	 * @params - http request params
	 * */
	public String makeServiceCall(String url, int method, List<NameValuePair> params) {
		try {
			HttpParams httpParameters = new BasicHttpParams();

			HttpConnectionParams.setConnectionTimeout(httpParameters, 60000);
			HttpConnectionParams.setSoTimeout(httpParameters, 60000);

			HttpClient httpClient = new DefaultHttpClient(httpParameters);

			HttpEntity httpEntity = null;
			HttpResponse httpResponse = null;
			// httpResponse.setHeader("Content-type", "application/json");

			// Checking http request method type
			if (method == POST) {
				HttpPost httpPost = new HttpPost(url);
				// adding post params
				if (params != null) {
					httpPost.setEntity(new UrlEncodedFormEntity(params));
				}

				httpResponse = httpClient.execute(httpPost);

			} else if (method == GET) {
				// appending params to url
				if (params != null) {
					String paramString = URLEncodedUtils.format(params, "utf-8");
					url += "?" + paramString;
				}
				HttpGet httpGet = new HttpGet(url);
				httpResponse = httpClient.execute(httpGet);
			}
			httpEntity = httpResponse.getEntity();
			response = EntityUtils.toString(httpEntity);
			return response;
		} catch (Exception e) {
			// HighScoreJson.isError = true;
			e.printStackTrace();
			return null;
		}

	}
}
