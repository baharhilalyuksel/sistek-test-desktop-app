package com.sistek.desktopapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import okhttp3.Credentials;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RestClient {

	String cookie;

	public String loginToWebApp(String username, String password) {
		
		String credential = Credentials.basic(username, password);
		
		OkHttpClient client = new OkHttpClient().newBuilder()
				  .build();
				Request request = new Request.Builder()
				  .url("http://localhost:8080/api/users/login")
				  .method("GET", null)
				  .addHeader("Authorization", credential)
				  .build();
				try {
					Response response = client.newCall(request).execute();
					cookie = response.headers().get("Set-Cookie");
					if(response.code() == 200) {
						return "SUCCESS";
					}
					return "LOGIN FAILED";
				} catch (IOException e) {
					e.printStackTrace();
					return "LOGIN FAILED";
				}
		
	}

	public String sendBarcode(String barcode) {
		
		OkHttpClient client = new OkHttpClient().newBuilder()
				  .build();
				MediaType mediaType = MediaType.parse("text/plain");
				RequestBody body = RequestBody.create(mediaType, "");
				Request request = new Request.Builder()
				  .url("http://localhost:8080/api/barcodes/" + barcode)
				  .method("POST", body)
				  .addHeader("Cookie", cookie)
				  .build();
				try {
					Response response = client.newCall(request).execute();
					if (response.code() == 200) {
						return "SUCCESS";
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		return "FAILED";
	}

	public List<String> listBarcodes() {
		
		List<String> barcodes = new ArrayList<String>();
		
		OkHttpClient client = new OkHttpClient().newBuilder()
				  .build();
				Request request = new Request.Builder()
				  .url("http://localhost:8080/api/barcodes/")
				  .method("GET", null)
				  .addHeader("Cookie", cookie)
				  .build();
				try {
					Response response = client.newCall(request).execute();
					JSONArray jsonResultArray = new JSONArray(response.body().string());
					for (int i = 0; i < jsonResultArray.length(); i++) {
						String barcode = jsonResultArray.getJSONObject(i).getString("barcode");
			            barcodes.add(barcode);
					}
					return barcodes;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		return barcodes;
		
	}

}
