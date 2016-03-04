package com.labbayak.etab;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.labbayak.R;
import com.labbayak.adapter.CustomBaseAdapterOperatorPackages;
import com.labbayak.app.AppController;
import com.labbayak.model.OperationsModel;
import com.labbayak.model.OperatorPackagesModel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

public class OperatorPackages extends Activity{


	private static String TAG = OperatorPackages.class.getSimpleName();

	// json object response url
	private String urlJsonObj = "http://labbayak.begoniainfosys.in/api/operator/packages";

	private ListView listView;

	// Progress dialog
	private ProgressDialog pDialog;

	private OperatorPackagesModel operatorPackagesModel;
    private ArrayList<OperatorPackagesModel>arrayListPackages;

	private String strId,strName,strMobile,strCity,strEmail,strMsg;

	private SharedPreferences prefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.operatorpackages);


		prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		pDialog = new ProgressDialog(this);
		pDialog.setMessage("Please wait...");
		pDialog.setCancelable(false);

		if(prefs.getString("ClassName", "").equalsIgnoreCase("HotelOperatorinfo")){

			strName = prefs.getString("HotelOperation_Name", "");
			strMobile = prefs.getString("HotelOperation_Mobile", "");
			strCity = prefs.getString("HotelOperation_City", "");
			strEmail = prefs.getString("HotelOperation_Email", "");
			strId=prefs.getString("HotelOperation_Id", "");

		}else if(prefs.getString("ClassName", "").equalsIgnoreCase("HajjOperationInfo")){

			strName = prefs.getString("HajjOperation_Name", "");
			strMobile = prefs.getString("HajjOperation_Mobile", "");
			strCity = prefs.getString("HajjOperation_City", "");
			strEmail = prefs.getString("HajjOperation_Email", ""); 
			strId=prefs.getString("HajjOperation_Id", "");

		}

		listView=(ListView)findViewById(R.id.operatorPackage_listview);

		try{
			JSONObject json=new JSONObject();

			json.put("operator", strId);

			String url=urlJsonObj+"?fields="+json.toString();

			makePackageListRequest(url);
		}catch(JSONException exception){

		}

	}

	private void updateListView() {
		// TODO Auto-generated method stub
		CustomBaseAdapterOperatorPackages operatorPackages=new CustomBaseAdapterOperatorPackages(OperatorPackages.this,arrayListPackages);
	
			listView.setAdapter(operatorPackages);
	}

	private void makePackageListRequest(String url) {
		// TODO Auto-generated method stub

		showpDialog();
		arrayListPackages=new ArrayList<OperatorPackagesModel>();
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.POST,
				url, null, new Response.Listener<JSONObject>() {

			@SuppressLint("NewApi")
			@Override
			public void onResponse(JSONObject response) {
				Log.d(TAG, response.toString());

				JSONObject jobj = null;

				try {

					jobj = new JSONObject(response.toString());
					Iterator<String> iter = jobj.keys();
					while (iter.hasNext()) {
						String key = iter.next();
						if(key.equalsIgnoreCase("failure")){
							String failure=jobj.getString("failure");

							Toast.makeText(OperatorPackages.this,failure,
									Toast.LENGTH_LONG).show();

						}else if(key.equalsIgnoreCase("success")){
							String success=jobj.getString("success");

							Toast.makeText(OperatorPackages.this,success,
									Toast.LENGTH_LONG).show();

						}else if(key.equalsIgnoreCase("packages")){
							
							JSONArray cast = jobj.getJSONArray("packages");
							for (int i=0; i<cast.length(); i++) {
							    JSONObject operObj = cast.getJSONObject(i);
							    
							    operatorPackagesModel=new OperatorPackagesModel();
		                        
							    operatorPackagesModel.oppackgId=operObj.getString("id");
							    operatorPackagesModel.created_at=operObj.getString("created_at");
							    operatorPackagesModel.updated_at=operObj.getString("updated_at");
							    operatorPackagesModel.details=operObj.getString("details");

							    operatorPackagesModel.oppackgoperator=operObj.getString("operator");
							    operatorPackagesModel.packagename=operObj.getString("name");

		                        
		                        
		                        arrayListPackages.add(operatorPackagesModel);
							    
							    
							    
							}
		                    

						}
					}	
					updateListView();
				} catch (JSONException e) {
					e.printStackTrace();
					Toast.makeText(getApplicationContext(),
							"Error: " + e.getMessage(),
							Toast.LENGTH_LONG).show();
				}
				hidepDialog();
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				VolleyLog.d(TAG, "Error: " + error.getMessage());
				Toast.makeText(getApplicationContext(),
						error.getMessage(), Toast.LENGTH_SHORT).show();
				// hide the progress dialog
				hidepDialog();
			}
		});

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(jsonObjReq);
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
