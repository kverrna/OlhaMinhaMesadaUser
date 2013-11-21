package com.OMM.application.user.requests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

import com.OMM.application.user.exceptions.ConnectionFailedException;
import com.OMM.application.user.exceptions.RequestFailedException;

public abstract class HttpConnection {

	public final static ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

		public String handleResponse(HttpResponse response) throws IOException {

			HttpEntity entity = response.getEntity();
			String result = null;

			BufferedReader buffer = new BufferedReader(new InputStreamReader(
					entity.getContent()));
			
			StringBuilder builder = new StringBuilder();
			String line = null;

			while ((line = buffer.readLine()) != null) {
				
				builder.append(line + "\n");
			}

			buffer.close();
			result = builder.toString();

			return result;
		}
	};

	public static ResponseHandler<String> getResponseHandler() {
		return responseHandler;
	}

	public static String requestParlamentar(ResponseHandler<String> response,
			String url) throws ConnectionFailedException, RequestFailedException {

		try {

			DefaultHttpClient client = new DefaultHttpClient();
			HttpGet http = new HttpGet(url);
			
			String jsonParlamentar = client.execute(http, response);

			return jsonParlamentar;

		} catch (ClientProtocolException e) {
			Log.i("Http", "Internet CPE");
			throw new RequestFailedException();

		} catch (IOException ioe) {
			Log.i("Http", "Internet ioe");
			throw new ConnectionFailedException();
		}
	}

	public static String requestCota(ResponseHandler<String> response,
			String url) {
		
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet http = new HttpGet(url);
		
		String jsonCotaParlamentar = null;

		try {
			
			jsonCotaParlamentar = client.execute(http, response);

		} catch (ClientProtocolException cpe) {
			// TODO: Exception Handling

		} catch (IOException ioe) {
			// TODO: Exception Handling
		}

		return jsonCotaParlamentar;
	}
}
