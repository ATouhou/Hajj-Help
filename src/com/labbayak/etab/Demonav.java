package com.labbayak.etab;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.labbayak.R;
import com.labbayak.utils.DirectionsJSONParser;

public class Demonav extends Activity {//implements LocationListener {
	 private static String TAG = Demonav.class.getSimpleName();

	// static final LatLng TutorialsPoint = new LatLng(17.4012 , 78.3757);
	private GoogleMap googleMap;
	private double doubleLatitude_curent, doubleLatitude_ser;
	private double doubleLongitude_curent, doubleLongitude_ser;
	private Button btnSaveDetails, sharelocation;
	private Button btnSearchLocation;
	RequestQueue queue;
	Marker marker;
	String str_nation, str_makthabnum, destinationurl;
	 // Progress dialog
    private ProgressDialog pDialog;
    private GPSTracker gps;
	double latitude, longitude;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hotelnavigator);

		// Getting Google Play availability status
		int status = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getBaseContext());

		// Showing status
		if (status != ConnectionResult.SUCCESS) { // Google Play Services are
													// not available
			int requestCode = 10;
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this,
					requestCode);
			dialog.show();

		} else { // Google Play Services are available

			gps = new GPSTracker(Demonav.this);

			if (gps.canGetLocation()) {

				latitude = gps.getLatitude();
				longitude = gps.getLongitude();

			
			pDialog = new ProgressDialog(this);
	        pDialog.setMessage("Please wait...");
	        pDialog.setCancelable(false);
	        
			btnSaveDetails = (Button) findViewById(R.id.saveLocation);
			btnSearchLocation = (Button) findViewById(R.id.searchLocation);
			sharelocation = (Button) findViewById(R.id.share);

			googleMap = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.fragment1)).getMap();
			// Enabling MyLocation Layer of Google Map
			googleMap.setMyLocationEnabled(true);
			
			googleMap.clear();
			// Getting latitude of the current location
			doubleLatitude_curent = latitude;

			// Getting longitude of the current location
			doubleLongitude_curent = longitude;

			// Creating a LatLng object for the current location
			LatLng latLng = new LatLng(latitude, longitude);
			// create marker
			MarkerOptions marker = new MarkerOptions().position(new LatLng(
					latitude, longitude)); // .title("Current Position" + latitude +
											// "," + longitude);
			//marker.title("my position");
			// adding marker
			marker.icon(BitmapDescriptorFactory
					.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
			googleMap.addMarker(marker);
			
			googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
					    new LatLng(latitude, longitude), 16));
			

		btnSaveDetails.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SaveDetails();
			}
		});
		btnSearchLocation.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				searchCity();
			}
		});

		sharelocation.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Sharelocation();

			}
		});

			if (str_makthabnum != "" && str_nation != "") {
	
				googleMap.setInfoWindowAdapter(new InfoWindowAdapter() {
	
					// Use default InfoWindow frame
					public View getInfoWindow(Marker arg0) {
						return null;
					}
	
					// Defines the contents of the InfoWindow
					public View getInfoContents(Marker arg0) {
	
						// Getting view from the layout file info_window_layout
						final View v = getLayoutInflater().inflate(
								R.layout.markerpopup, null);
	
	
						// Getting reference to the TextView to set latitude
						TextView makthab = (TextView) v.findViewById(R.id.tv_mkn);
	
						// Getting reference to the TextView to set longitude
						TextView tnationality = (TextView) v
								.findViewById(R.id.natin);
						// Setting the latitude
						tnationality.setText(str_nation);
						makthab.setText(str_makthabnum);
	
					
						return v;
	
					}
				});
	
			} else {
	
			}
			
			}else{
				// can't get location
				// GPS or Network is not enabled
				// Ask user to enable GPS/network in settings
				AlertDialog.Builder alertDialog = new AlertDialog.Builder(Demonav.this);

				alertDialog.setCancelable(false);
				// Setting Dialog Title
				alertDialog.setTitle("GPS is settings");

				// Setting Dialog Message
				alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

				// On pressing Settings button
				alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int which) {
						Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
						startActivity(intent);
					}
				});

				// on pressing cancel button
				alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						//Stop Service
						dialog.cancel();
						Intent intent = new Intent(Demonav.this,HomeActivity.class);
						startActivity(intent);
						finish();
					}
				});

				// Showing Alert Message
				alertDialog.show();		
				}
	}//end of else google play service 
}//end of onCreate



	private void SaveDetails() {
		// TODO Auto-generated method stub

		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.arafathpopup);
		dialog.setTitle("Enetr Details");

		final EditText choosemakthab = (EditText) dialog
				.findViewById(R.id.et_makthabnum);
		final EditText chooseNation = (EditText) dialog
				.findViewById(R.id.et_nationality);
		Button dialogOk = (Button) dialog.findViewById(R.id.btn_ok);
		Button dialogCancel = (Button) dialog.findViewById(R.id.btn__dialogcancel);

		dialogCancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});

		dialogOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				String makthabnum = choosemakthab.getText().toString();
				String nationality = chooseNation.getText().toString();

				final JSONObject Arafathdetails = new JSONObject();

				try {
					String uid = "1";
					Arafathdetails.put("location", makthabnum);
					Arafathdetails.put("nationality", nationality);
					Arafathdetails.put("latitude", doubleLatitude_curent);
					Arafathdetails.put("longitude", doubleLongitude_curent);
					Arafathdetails.put("userid", uid);

				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				};
				
						String Url = "http://labbayak.begoniainfosys.in/api/arafat/store?fields="
						+ Arafathdetails;

				StringRequest stringRequest = new StringRequest(
						Request.Method.GET, Url,
						new Response.Listener<String>() {
							@Override
							public void onResponse(String response) {

								Log.d("Reponse", response);
								Toast.makeText(getApplicationContext(),
										response, Toast.LENGTH_LONG).show();

							}
						}, new Response.ErrorListener() {
							@Override
							public void onErrorResponse(VolleyError error) {

								// Error handling

								Toast.makeText(getApplicationContext(),
										error.toString(), Toast.LENGTH_LONG)
										.show();

							}
						});
				// Add the request to the queue
				Volley.newRequestQueue(Demonav.this).add(
						stringRequest);

				dialog.dismiss();
			}

		});
		dialog.show();

	}

	private void searchCity() {

		final EditText makthabnumber = (EditText) findViewById(R.id.search);
		Button search = (Button) findViewById(R.id.searchLocation);

		search.setOnClickListener(new OnClickListener() {
		 JSONObject Searchdetails_Obj = new JSONObject();

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String mknum = makthabnumber.getText().toString();

				try {

					Searchdetails_Obj.put("searchTerm", mknum);

				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				String urlSearch = "http://labbayak.begoniainfosys.in/api/arafat/search?fields="
						+ Searchdetails_Obj;
				
				
				searchLocationApi(urlSearch);				
			}			
		});

	}
	private void searchLocationApi(String url) {
		// TODO Auto-generated method stub
		
		 showpDialog();
		StringRequest stringRequest = new StringRequest(
				Request.Method.GET, url,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
                        Log.d(TAG, response.toString());

						String Resultstr = response.toString();
						JSONObject jobj = null;
						String str_lat=null;
						String str_lon=null;
						String reValue="sucesses";

						try {
							 jobj = new JSONObject(Resultstr);
							 Iterator<String> iter = jobj.keys();
				                while (iter.hasNext()) {
				                    String key = iter.next();
									if(key.equalsIgnoreCase("failure")){
										reValue="failure";
										Toast.makeText(Demonav.this,jobj.getString("failure"),
												Toast.LENGTH_LONG).show();

									}else if(key.equalsIgnoreCase("location")){
										str_makthabnum = jobj.getString("location");
									}else if(key.equalsIgnoreCase("latitude")){
										str_lat = jobj.getString("latitude");
									}else if(key.equalsIgnoreCase("longitude")){
										str_lon =  jobj.getString("longitude");

									}else if(key.equalsIgnoreCase("nationality")){
										str_nation = jobj.getString("nationality");
									}else{
										reValue="Exception";
									}
								
				                }
//									 str_lat = jobj.getString("latitude")
//											.toString()
//											+ ","
//											+ jobj.getString("longitude")
//													.toString();
//
//									String str_long = jobj.getString(
//											"longitude").toString();
//
//									str_makthabnum = jobj.getString("location")
//											.toString();
//									str_nation = jobj.getString("nationality")
//											.toString();
									if(reValue.equalsIgnoreCase("sucesses")){
									String latilongi=str_lat+","+str_lon;

									String[] latLng = latilongi.split(",");
									double latitude = Double
											.parseDouble(latLng[0]);
									double longitude = Double
											.parseDouble(latLng[1]);

									// doubleLongitude_ser =
									// Double.parseDouble(str_lat);
									// doubleLatitude_ser =
									// Double.parseDouble(str_long);

									Toast.makeText(
											Demonav.this,
											str_lat + str_lon,
											Toast.LENGTH_LONG).show();

									LatLng originlatLng = new LatLng(
											doubleLatitude_curent,
											doubleLongitude_curent);
									LatLng destlatLng = new LatLng(latitude,
											longitude);// (23.2223, 78.4456);

									String url = getDirectionsUrl(originlatLng,
											destlatLng);

									DownloadTask downloadTask = new DownloadTask();

									// Start downloading json data from Google
									// Directions API
									downloadTask.execute(url);
									MarkerOptions marker = new MarkerOptions()
											.position(new LatLng(latitude,
													longitude)); // .title("Current Position"
																	// +
																	// latitude
																	// + "," +
																	// longitude);

									// adding marker
									marker.icon(BitmapDescriptorFactory
											.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
									googleMap.addMarker(marker); 
						}
				                
						} catch (JSONException e) {
							e.printStackTrace();
						}catch (Exception e) {
							// TODO: handle exception
						}
						  hidepDialog();
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {

						// Error handling

						Toast.makeText(getApplicationContext(),
								error.toString(), Toast.LENGTH_LONG)
								.show();
						 // hide the progress dialog
                        hidepDialog();
					}
				});
		// Add the request to the queue
		Volley.newRequestQueue(Demonav.this).add(
				stringRequest);
	}
	private String getDirectionsUrl(LatLng origin, LatLng dest) {

		// Origin of route
		String str_origin = "origin=" + origin.latitude + ","
				+ origin.longitude;

		// Destination of route
		String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

		// Sensor enabled
		String sensor = "sensor=false";

		// Building the parameters to the web service
		String parameters = str_origin + "&" + str_dest + "&" + sensor;

		// Output format
		String output = "json";

		// Building the url to the web service
		destinationurl = "https://maps.googleapis.com/maps/api/directions/"
				+ output + "?" + parameters;

		return destinationurl;
	}

	/** A method to download json data from url */
	private String downloadUrl(String strUrl) throws IOException {
		String data = "";
		InputStream iStream = null;
		HttpURLConnection urlConnection = null;
		try {
			URL url = new URL(strUrl);

			// Creating an http connection to communicate with url
			urlConnection = (HttpURLConnection) url.openConnection();

			// Connecting to url
			urlConnection.connect();

			// Reading data from url
			iStream = urlConnection.getInputStream();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					iStream));

			StringBuffer sb = new StringBuffer();

			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			data = sb.toString();

			br.close();

		} catch (Exception e) {
			Log.d("Exception while downloading url", e.toString());
		} finally {
			iStream.close();
			urlConnection.disconnect();
		}
		return data;
	}

	// Fetches data from url passed
	private class DownloadTask extends AsyncTask<String, Void, String> {

		// Downloading data in non-ui thread
		@Override
		protected String doInBackground(String... url) {

			// For storing data from web service
			String data = "";

			try {
				// Fetching the data from web service
				data = downloadUrl(url[0]);
			} catch (Exception e) {
				Log.d("Background Task", e.toString());
			}
			return data;
		}

		// Executes in UI thread, after the execution of
		// doInBackground()
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			ParserTask parserTask = new ParserTask();

			// Invokes the thread for parsing the JSON data
			parserTask.execute(result);
		}
	}

	/** A class to parse the Google Places in JSON format */
	private class ParserTask extends
			AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

		// Parsing the data in non-ui thread
		@Override
		protected List<List<HashMap<String, String>>> doInBackground(
				String... jsonData) {

			JSONObject jObject;
			List<List<HashMap<String, String>>> routes = null;

			try {
				jObject = new JSONObject(jsonData[0]);
				DirectionsJSONParser parser = new DirectionsJSONParser();

				// Starts parsing data
				routes = parser.parse(jObject);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return routes;
		}

		// Executes in UI thread, after the parsing process
		@Override
		protected void onPostExecute(List<List<HashMap<String, String>>> result) {
			ArrayList<LatLng> points = null;
			PolylineOptions lineOptions = null;
			MarkerOptions markerOptions = new MarkerOptions();
			String distance = "";
			String duration = "";

			if (result.size() < 1) {
				Toast.makeText(getBaseContext(), "No Points",
						Toast.LENGTH_SHORT).show();
				return;
			}

			// Traversing through all the routes
			for (int i = 0; i < result.size(); i++) {
				points = new ArrayList<LatLng>();
				lineOptions = new PolylineOptions();

				// Fetching i-th route
				List<HashMap<String, String>> path = result.get(i);

				// Fetching all the points in i-th route
				for (int j = 0; j < path.size(); j++) {
					HashMap<String, String> point = path.get(j);

					if (j == 0) { // Get distance from the list
						distance = (String) point.get("distance");
						continue;
					} else if (j == 1) { // Get duration from the list
						duration = (String) point.get("duration");
						continue;
					}

					double lat = Double.parseDouble(point.get("lat"));
					double lng = Double.parseDouble(point.get("lng"));
					LatLng position = new LatLng(lat, lng);

					points.add(position);
				}

				// Adding all the points in the route to LineOptions
				lineOptions.addAll(points);
				lineOptions.width(5);
				lineOptions.color(Color.RED);
			}

			// Drawing polyline in the Google Map for the i-th route
			googleMap.addPolyline(lineOptions);
		}
	}

	private void Sharelocation() {
		// TODO Auto-generated method stub
		
		Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
		sharingIntent.setType("text/plain");
		StringBuffer smsBody = new StringBuffer();
	    smsBody.append("http://maps.google.com/?q="); 
	    smsBody.append(doubleLatitude_curent);
	    smsBody.append(",");
	    smsBody.append(doubleLongitude_curent);
	    smsBody.append("&z=17");
//		sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
//				"Current Location");
		sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, smsBody.toString());
		startActivity(Intent.createChooser(sharingIntent, "shareBody"));
		startActivity(sharingIntent);

	}		
	private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    } 
    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}