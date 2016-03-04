package com.labbayak.etab;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

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

public class Weather extends Activity{
	
	
	String WUrl,Resultstr,str_windspeed,str_temp,str_humidity,
	str_sunrise,str_sunset,str_country,str_description,str_cityname;	
	long long_sunrise,long_sunset;	
	TextView Cityname,Temp,Humidity,Wind,Sunset,Sunrise,Country,Description;
	JSONObject  jobj,wind,sys,main;
	EditText ChooseCity,ChooseCountry;
	RequestQueue 	queue;
	Dialog dialog ;
	Button dialogOk,dialogCancel ;
	public ProgressDialog progressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weather);
		
		Cityname = (TextView)findViewById(R.id.tv_city);
		Country = (TextView)findViewById(R.id.tv_country);
		Temp = (TextView)findViewById(R.id.tv_temp);
		Humidity= (TextView)findViewById(R.id.tv_humidity);
		Wind = (TextView)findViewById(R.id.tv_wind);
		Sunset = (TextView)findViewById(R.id.tv_sunset);
		Sunrise = (TextView)findViewById(R.id.tv_sunrise);
		Description = (TextView)findViewById(R.id.tv_description);
		
		queue = Volley.newRequestQueue(this);
		
		progressDialog = ProgressDialog.show(Weather.this, "", "Loading...");
		DisplayDefaultWeather();
		
		
		}
	
	private void DisplayDefaultWeather() {
		// TODO Auto-generated method stub
		WUrl = "http://api.openweathermap.org/data/2.5/weather?q=Makkah,sa&mode=json&units=metric&appid=44db6a862fba0b067b1930da0d769e98";
		JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, WUrl, null, new Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				// TODO Auto-generated method stub
				
				Resultstr=response.toString();
				
				try {
			         jobj = new JSONObject(Resultstr);
			        
			         wind = jobj.getJSONObject("wind");
			         main = jobj.getJSONObject("main");
			         sys = jobj.getJSONObject("sys");
			   
			        JSONArray  JArray = jobj.getJSONArray("weather");
			        for(int i=0; i < JArray.length(); i++){
			            JSONObject jsonObject = JArray.getJSONObject(i);
			            
			        str_description = jsonObject.getString("description").toString();
	
			        long_sunrise = sys.optLong("sunrise");
			        long javaTimestamp = long_sunrise * 1000L;
			        Date date = new Date(javaTimestamp);
			        str_sunrise = new SimpleDateFormat("hh:mm").format(date);
			        
			        long_sunset = sys.optLong("sunset");			        
			        long javaTimestamp2 = long_sunset * 1000L;
			        Date date2 = new Date(javaTimestamp2);
			        str_sunset = new SimpleDateFormat("hh:mm").format(date2);
			        
			        str_country = sys.getString("country");			        
			        str_temp = main.getString("temp");
			        str_humidity = main.getString("humidity");
		            str_windspeed = wind.getString("speed");
		            str_cityname = jobj.getString("name");
		         	
			        Wind.setText("Wind Speed : " +str_windspeed + " m/s");
			        Humidity.setText("Humidity : "+ str_humidity +"%");
			        Temp.setText(str_temp);
			        Sunrise.setText("Sunrise : " + str_sunrise );
			        Sunset.setText("Sunset  : " + str_sunset);
			        Country.setText(" -"+ str_country);
			        Description.setText(str_description);
			        Cityname.setText(str_cityname);
			        progressDialog.dismiss();
			        
					
			        		        
			        }  
			      } catch (JSONException e)
			      {
			    	  Toast.makeText(getApplicationContext(), "Oops Error.."+ e.toString(), Toast.LENGTH_LONG).show();
			      }
				
			}				
		
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError e) {
				// TODO Auto-generated method stub
				
				Toast.makeText(getApplicationContext(), "Oops Error.."+ e.toString(), Toast.LENGTH_LONG).show();
			}
		});
		queue.add(jsObjRequest);
	}

	public void btn_Changecity(View v){
		
		DisplayweatherbyCity();
	}

	private void DisplayweatherbyCity() {
		// TODO Auto-generated method stub
		
		dialog = new Dialog(this);
		dialog.setContentView(R.layout.custompopup);
		dialog.setTitle("Change City...");
		
		
		dialogOk = (Button) dialog.findViewById(R.id.btn_ok);
		
		dialogOk.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ChooseCity = (EditText) dialog.findViewById(R.id.et_citynameinput);
				ChooseCountry = (EditText) dialog.findViewById(R.id.et_countrycode);
				
				String CityChoosenET = ChooseCity.getText().toString().trim();
				String CountryChoosenET = ChooseCountry.getText().toString().trim();
				
				WUrl = "http://api.openweathermap.org/data/2.5/weather?q=" + CityChoosenET+ CountryChoosenET+"&mode=json&units=metric&appid=44db6a862fba0b067b1930da0d769e98";
				JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, WUrl, null, new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						// TODO Auto-generated method stub
						
						Resultstr=response.toString();
						
						try {
					         jobj = new JSONObject(Resultstr);
					        
					         wind = jobj.getJSONObject("wind");
					         main = jobj.getJSONObject("main");
					         sys = jobj.getJSONObject("sys");
					   
					        JSONArray  JArray = jobj.getJSONArray("weather");
					        for(int i=0; i < JArray.length(); i++){
					            JSONObject jsonObject = JArray.getJSONObject(i);
					            
					        str_description = jsonObject.getString("description").toString();
			
					        long_sunrise = sys.optLong("sunrise");
					        long javaTimestamp = long_sunrise * 1000L;
					        Date date = new Date(javaTimestamp);
					        str_sunrise = new SimpleDateFormat("hh:mm").format(date);
					        
					        long_sunset = sys.optLong("sunset");			        
					        long javaTimestamp2 = long_sunset * 1000L;
					        Date date2 = new Date(javaTimestamp2);
					        str_sunset = new SimpleDateFormat("hh:mm").format(date2);
					        
					        str_country = sys.getString("country");			        
					        str_temp = main.getString("temp");
					        str_humidity = main.getString("humidity");
				            str_windspeed = wind.getString("speed");
				            str_cityname = jobj.getString("name");
				         	
					        Wind.setText("Wind Speed : " +str_windspeed + " m/s");
					        Humidity.setText("Humidity : "+ str_humidity +"%");
					        Temp.setText(str_temp);
					        Sunrise.setText("Sunrise : " + str_sunrise );
					        Sunset.setText("Sunset  : " + str_sunset);
					        Country.setText(" -"+ str_country);
					        Description.setText(str_description);
					        Cityname.setText(str_cityname);
					        
							
					        		        
					        }  
					      } catch (JSONException e)
					      {
					    	  Toast.makeText(getApplicationContext(), "Oops Error.."+ e.toString(), Toast.LENGTH_LONG).show();
					      }
						
					}				
				
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError e) {
						// TODO Auto-generated method stub
						
						Toast.makeText(getApplicationContext(), "Oops Error.."+ e.toString(), Toast.LENGTH_LONG).show();
					}
				});
				queue.add(jsObjRequest);
				dialog.dismiss();	
			}
		});
		dialog.show();
	}
}

