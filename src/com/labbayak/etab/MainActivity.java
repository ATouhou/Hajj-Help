package com.labbayak.etab;

import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.labbayak.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Locale myLocale;
	Spinner LanguageSpinner;
	CheckBox Ok_Valunteer, Ok_Piligrim, Ok_Hajjoperator, Ok_HotelOperator;
	ImageView eye;
	float initialX, initialY;
	public ProgressDialog progressDialog;
	EditText Username, Password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Username = (EditText) findViewById(R.id.et_username);
		Password = (EditText) findViewById(R.id.et_pwd);

		LanguageSpinner = (Spinner) findViewById(R.id.sp_language);
		// madapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
		LanguageSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				// TODO Auto-generated method stub

				if (pos == 1) {
					setLocale("en");
				} else if (pos == 2) {
					setLocale("ar");
				}
			}

			private void setLocale(String lang) {
				// TODO Auto-generated method stub
				myLocale = new Locale(lang);
				Resources res = getResources();
				DisplayMetrics dm = res.getDisplayMetrics();
				Configuration conf = res.getConfiguration();
				conf.locale = myLocale;
				res.updateConfiguration(conf, dm);

				Intent refresh = new Intent(getApplicationContext(),
						MainActivity.class);
				startActivity(refresh);
				finish();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	public void btnClick_SignIn(View v) {

		progressDialog = ProgressDialog.show(MainActivity.this, "",
				"please wait...");
		// Request a string response
		String e = Username.getText().toString().trim();
		String p = Password.getText().toString().trim();

		final JSONObject UserDetails = new JSONObject();

		try {

			UserDetails.put("EmailID", e);
			UserDetails.put("Password", p);

		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		;

		String Url = "http://labbayak.begoniainfosys.in/api/signin?fields="
				+ UserDetails;
		

		StringRequest stringRequest = new StringRequest(Request.Method.GET,
				Url, new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						
					try {
						
						JSONObject	jobj = new JSONObject(response);					
						
						String success = jobj.getString("success");
						if(success != ""){
							Toast.makeText(getApplicationContext(), "Login Ssuccess",
									Toast.LENGTH_SHORT).show();							
							Intent i = new Intent(getApplicationContext(),
									HomeActivity.class);
							startActivity(i);
							progressDialog.dismiss();
						}
						else{
							Toast.makeText(getApplicationContext(), "Details Not match",
									Toast.LENGTH_LONG).show();
						}
						
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						Toast.makeText(getApplicationContext(), e.toString(),
								Toast.LENGTH_LONG).show();
					}
						
						
						
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {

						Toast.makeText(getApplicationContext(),
								error.toString(), Toast.LENGTH_LONG).show();

					}
				});

		// Add the request to the queue
		Volley.newRequestQueue(this).add(stringRequest);
	}

	public void btn_GuestUserClicked(View v) {
		Intent i = new Intent(MainActivity.this, HomeActivity.class);
		i.putExtra("keyName", "GU");
		startActivity(i);
	}

	public void tvClick_ForgotPwd(View v) {

		Intent i = new Intent(getApplicationContext(), ForgotPwd_Activity.class);
		startActivity(i);

	}

	public void btnClick_SignUp(View v) {

		displayUsertypePopup();
	}

	private void displayUsertypePopup() {
		// TODO Auto-generated method stub

		LayoutInflater inflater = getLayoutInflater();
		View alertLayout = inflater.inflate(R.layout.usertypepopup, null);

		final CheckBox piligrim = (CheckBox) alertLayout
				.findViewById(R.id.cb_piligrim);
		final CheckBox Volunteer = (CheckBox) alertLayout
				.findViewById(R.id.cb_volunteer);
		final CheckBox HajjOperator = (CheckBox) alertLayout
				.findViewById(R.id.cb_hajjoperator);
		final CheckBox HotelOperator = (CheckBox) alertLayout
				.findViewById(R.id.cb_hoteloperator);

		piligrim.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {

					Intent i = new Intent(MainActivity.this,
							UserRegistration_Activity.class);
					i.putExtra("keyName", "piligrim");
					startActivity(i);
					// Toast.makeText(getApplicationContext(), "piligrim",
					// Toast.LENGTH_SHORT).show();
				}

				else {

				}
			} // etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
				// }
		});
		Volunteer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							Intent i = new Intent(MainActivity.this,
									UserRegistration_Activity.class);
							i.putExtra("keyName", "Volunteer");
							startActivity(i);
							// Toast.makeText(getApplicationContext(),
							// "Volunteer",
							// Toast.LENGTH_SHORT).show();
						}

						else {
							
						}
					} // etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
						// }
				});

		HajjOperator
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {

							Intent i = new Intent(MainActivity.this,
									UserRegistration_Activity.class);
							i.putExtra("keyName", "HajjOperator");
							startActivity(i);
							// Toast.makeText(getApplicationContext(),
							// "Hajj Operator",
							// Toast.LENGTH_SHORT).show();
						} else {

						}
					} // etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
						// }
				});

		HotelOperator
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							Intent i = new Intent(MainActivity.this,
									UserRegistration_Activity.class);
							i.putExtra("keyName", "HotelOperator");
							startActivity(i);
							// Toast.makeText(getApplicationContext(),
							// "Hotel Operator",
							// Toast.LENGTH_SHORT).show();
						}

						else {

						}
					} // etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
						// }
				});

		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Select Usertype");
		alert.setView(alertLayout);
		alert.setCancelable(false);
		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				});

		AlertDialog dialog = alert.create();
		dialog.show();
	}

}
