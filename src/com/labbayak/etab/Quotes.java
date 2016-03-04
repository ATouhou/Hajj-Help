package com.labbayak.etab;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.labbayak.R;

public class Quotes extends Activity {

	TextView q1, q2, q3, q4, q5;
	String Sq1, Sq2, Sq3, Sq4, Sq5;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quotes);

		String Url = "http://labbayak.begoniainfosys.in/api/hadith/quotes";

		StringRequest stringRequest = new StringRequest(Request.Method.GET,
				Url, new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						if (response != "") {
							String Resultstr = response.toString();
							try {
								JSONObject jobj = new JSONObject(Resultstr);

								JSONArray JArray = jobj.getJSONArray("quotes");
								for (int i = 0; i < JArray.length(); i++) {
									JSONObject jsonObject = JArray
											.getJSONObject(i);

									q1 = (TextView) findViewById(R.id.quote1);
									q2 = (TextView) findViewById(R.id.quote2);
									q3 = (TextView) findViewById(R.id.quote3);
									q4 = (TextView) findViewById(R.id.quote4);
									q5 = (TextView) findViewById(R.id.quote5);

									Sq1 = jsonObject.optString("quote")
											.toString();
									Sq2 = jsonObject.optString("quote")
											.toString();
									Sq3 = jsonObject.optString("quote")
											.toString();
									Sq4 = jsonObject.optString("quote")
											.toString();
									Sq5 = jsonObject.optString("quote")
											.toString();

									if (i == 0) {
										q1.setText(Sq1);
									}
									if (i == 1) {
										q2.setText(Sq2);
									}
									if (i == 2) {
										q3.setText(Sq3);
									}
									if (i == 3) {
										q4.setText(Sq4);
									}
									if (i == 4) {
										q5.setText(Sq5);
									}									

								}
							} catch (JSONException e) {
								e.printStackTrace();
							}

						} else {
							Toast.makeText(getApplicationContext(),
									"No Data,Try After Sometime",
									Toast.LENGTH_LONG).show();
						}

					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {

					}
				});
		Volley.newRequestQueue(this).add(stringRequest);

	}
}
