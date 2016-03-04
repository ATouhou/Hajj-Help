package com.labbayak.etab;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class Singleton {

	public RequestQueue queue;
	static String Resultstr;

	public static String ResponseData(String Url) {
		// TODO Auto-generated method stub

		StringRequest stringRequest = new StringRequest(Request.Method.GET,
				Url, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {

			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {

			}
		});
	//	Volley.newRequestQueue(this).add(stringRequest);
		// Add the request to the queue
		

		return Resultstr;
	}
}
