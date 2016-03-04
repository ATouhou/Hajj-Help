package com.labbayak.etab;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.labbayak.R;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Prayertimings_Activity extends Activity {

	EditText ChooseCity, ChooseCountry;
	String CityChoosenET, CountryChoosenET;
	RequestQueue queue;
	JSONObject jobj;
	TextView Fajar, Shorook, Asar, Isha, Mighrib, Zuher, City;
	String data;
	String str_fajar, str_shorook, str_zuher, str_ajar, str_maghrib, str_isha,
			str_cityname;
	public ProgressDialog progressDialog;

	// String Url = "";
	// String login =
	// "http://labbayak.begoniainfosys.in/api/signin?fields={\"EmailID\":\"avinash@begoniainfosys.com\",\"Password\":\"opensesame\"}";
	// String reg =
	// "http://labbayak.begoniainfosys.in/api/signup?fields={\"UserName\":\"AvinashT\",\"EmailID\":\"avivvvnash@begoniainfosys.com\",\"PassportNumber\":\"ASDGFT54KLM9\",\"GenderID\":\"M\",\"NationalityName\":\"Indian\",\"MobileNumber\":\"+919704952888\",\"CountryName\":\"India\",\"StateName\":\"Telangana\",\"City\":\"Hyderabad\",\"Password\":\"opensesame\",\"ImageBase\":\"\",\"IsVolunteer\":1,\"UserType\":\"pilgrim\",\"Profesion\":\"ProjectManager\"}";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.prayertimings);
		queue = Volley.newRequestQueue(this);

		progressDialog = ProgressDialog.show(Prayertimings_Activity.this, "",
				"Loading...");

		Displayprayertime();

	}

	private void Displayprayertime() {
		// TODO Auto-generated method stub

		String Url = "http://muslimsalat.com/makkah/daily.json?key=22b55b773a35c31612be2ec665fa2d5b";

		JsonObjectRequest jsObjRequest = new JsonObjectRequest(
				Request.Method.GET, Url, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						String Resultstr = response.toString();

						try {
							jobj = new JSONObject(Resultstr);

							JSONArray JArray = jobj.getJSONArray("items");
							for (int i = 0; i < JArray.length(); i++) {
								JSONObject jsonObject = JArray.getJSONObject(i);

								Fajar = (TextView) findViewById(R.id.tv_fajar);
								Shorook = (TextView) findViewById(R.id.tv_shorook);
								Asar = (TextView) findViewById(R.id.tv_asar);
								Isha = (TextView) findViewById(R.id.tv_isha);
								Mighrib = (TextView) findViewById(R.id.tv_maghrib);
								Zuher = (TextView) findViewById(R.id.tv_zuher);
								City = (TextView) findViewById(R.id.tv_cityname);

								str_fajar = jsonObject.getString("fajr")
										.toString();
								str_shorook = jsonObject.getString("shurooq")
										.toString();
								str_zuher = jsonObject.getString("dhuhr")
										.toString();
								str_ajar = jsonObject.getString("asr")
										.toString();
								str_maghrib = jsonObject.getString("maghrib")
										.toString();
								str_isha = jsonObject.getString("isha")
										.toString();
								str_cityname = jobj.getString("city")
										.toString();

								City.setText(str_cityname);
								Fajar.setText(str_fajar);
								Shorook.setText(str_shorook);
								Zuher.setText(str_zuher);
								Asar.setText(str_ajar);
								Mighrib.setText(str_maghrib);
								Isha.setText(str_isha);
								City.setText("Makkah");
								progressDialog.dismiss();

							}
						} catch (JSONException e) {
							e.printStackTrace();
						}

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError e) {
						// TODO Auto-generated method stub
						Toast.makeText(getApplicationContext(),
								"Oops Error Fetching Data ", Toast.LENGTH_LONG)
								.show();
					}
				});
		queue.add(jsObjRequest);
	}

	public void btn_PrayerSelectcity(View v) {

		DisplayprayertimebyCity();

	}

	private void DisplayprayertimebyCity() {
		// TODO Auto-generated method stub

		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.custompopupforcity);
		dialog.setTitle("Change City...");

		// set the custom dialog components - text, image and button

		Button dialogOk = (Button) dialog.findViewById(R.id.btn_ok);
		Button dialogcancel = (Button) dialog
				.findViewById(R.id.btn__dialogcancel);
		dialogcancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				dialog.dismiss();
			}
		});
		ChooseCity = (EditText) dialog.findViewById(R.id.et_citynameinput);

		// if button is clicked, close the custom dialog
		dialogOk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				CityChoosenET = ChooseCity.getText().toString().trim();

				// Toast.makeText(getApplicationContext(), "Enter Cityname",
				// Toast.LENGTH_SHORT).show();

				String Url = "http://muslimsalat.com/" + CityChoosenET
						+ "/daily.json?key=22b55b773a35c31612be2ec665fa2d5b";
				JsonObjectRequest jsObjRequest = new JsonObjectRequest(
						Request.Method.GET, Url, null,
						new Response.Listener<JSONObject>() {

							@Override
							public void onResponse(JSONObject response) {
								// TODO Auto-generated method stub
								// Toast.makeText(getApplicationContext(),
								// response.toString(),
								// Toast.LENGTH_LONG).show();
								String Resultstr = response.toString();
								// Toast.makeText(getApplicationContext(),
								// "Oops Error Fetching Data " +Resultstr,
								// Toast.LENGTH_LONG).show();

								try {
									jobj = new JSONObject(Resultstr);

									JSONArray JArray = jobj
											.getJSONArray("items");
									for (int i = 0; i < JArray.length(); i++) {
										JSONObject jsonObject = JArray
												.getJSONObject(i);

										Fajar = (TextView) findViewById(R.id.tv_fajar);
										Shorook = (TextView) findViewById(R.id.tv_shorook);
										Asar = (TextView) findViewById(R.id.tv_asar);
										Isha = (TextView) findViewById(R.id.tv_isha);
										Mighrib = (TextView) findViewById(R.id.tv_maghrib);
										Zuher = (TextView) findViewById(R.id.tv_zuher);
										City = (TextView) findViewById(R.id.tv_cityname);

										str_fajar = jsonObject
												.getString("fajr").toString();
										str_shorook = jsonObject.getString(
												"shurooq").toString();
										str_zuher = jsonObject.getString(
												"dhuhr").toString();
										str_ajar = jsonObject.getString("asr")
												.toString();
										str_maghrib = jsonObject.getString(
												"maghrib").toString();
										str_isha = jsonObject.getString("isha")
												.toString();
										str_cityname = jobj.getString("city")
												.toString();

										City.setText(str_cityname);
										Fajar.setText(str_fajar);
										Shorook.setText(str_shorook);
										Zuher.setText(str_zuher);
										Asar.setText(str_ajar);
										Mighrib.setText(str_maghrib);
										Isha.setText(str_isha);

										//
									}
								} catch (JSONException e) {
									e.printStackTrace();
								}

							}
						}, new Response.ErrorListener() {

							@Override
							public void onErrorResponse(VolleyError e) {
								// TODO Auto-generated method stub
								Toast.makeText(getApplicationContext(),
										"Oops Error Fetching Data ",
										Toast.LENGTH_LONG).show();
							}
						});
				queue.add(jsObjRequest);
				dialog.dismiss();

			}

		});

		dialog.show();
	}

}
