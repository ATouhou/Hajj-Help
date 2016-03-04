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
import com.labbayak.adapter.CustomBaseAdapterOperationList;
import com.labbayak.app.AppController;
import com.labbayak.model.OperationsModel;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class Hoteloperatorinfo extends Activity{
    private static String TAG = Hoteloperatorinfo.class.getSimpleName();

	 // json object response url
    private String urlJsonObj = "http://labbayak.begoniainfosys.in/api/get/hotels";
     
    private OperationsModel operationsModel;
    
    private static final String[] names = new String[] {
	        "Venkatesh", "Ramesh", "Subahni", "Rakesh", "Anurag"};
    
    private static final String[] city = new String[] {
	        "Hyderabad", "Vizag", "Hyderabad", "Assam", "Orisa"};
    private static final String[] mbile = new String[] {
	        "2342343243", "3423443243", "2314223532", "9862726262", "8262266262"};
    
    private static final String[] email = new String[] {
	        "ab@fnf.cic", "asd@asd.fdgs", "asd@da.fbd", "asda@sdf.df", "asda@sdf.fasa"};
    
    // Progress dialog
    private ProgressDialog pDialog;
    
    private ArrayList<OperationsModel>arrayList;
    
	private ListView listViewoperations;
	
	
	private SharedPreferences prefs;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.operatorinfo);	
		
		pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
        
        listViewoperations=(ListView)findViewById(R.id.operation_list);
//        arrayList=new ArrayList<OperationsModel>();
//        for (int i = 0; i < names.length; i++) {
//        	
//            operationsModel=new OperationsModel();
//
//        	operationsModel.name=names[i];
//        	operationsModel.city=city[i];
//        	operationsModel.mobile=mbile[i];
//        	operationsModel.email=email[i];
//        	
//        	arrayList.add(operationsModel);
//        }
//        updateList();

        
		makeJsonObjectRequest();
		
        listViewoperations.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
	        	prefs.edit().putString("ClassName", "HotelOperatorinfo").commit();

				
	        	prefs.edit().putString("HotelOperation_Name", arrayList.get(position).getName()).commit();
	        	prefs.edit().putString("HotelOperation_City", arrayList.get(position).getCity()).commit();

	        	prefs.edit().putString("HotelOperation_Mobile", arrayList.get(position).getMobile()).commit();

	        	prefs.edit().putString("HotelOperation_Email", arrayList.get(position).getEmail()).commit();
	        	prefs.edit().putString("HotelOperation_Id", arrayList.get(position).getId()).commit();

	        	
	        	Intent i=new Intent(getApplicationContext(), OperatorPackages.class);
	        	startActivity(i);
	        	
	        	
			}
		});
		
		
	
	}

	private void updateList() {
		// TODO Auto-generated method stub
		 CustomBaseAdapterOperationList adapter = new CustomBaseAdapterOperationList(this, arrayList);
		 listViewoperations.setAdapter(adapter);
		
	}

	private void makeJsonObjectRequest() {
		// TODO Auto-generated method stub
		
		 showpDialog();
		 arrayList=new ArrayList<OperationsModel>();
	        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.POST,
	                urlJsonObj, null, new Response.Listener<JSONObject>() {
	 
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

										Toast.makeText(Hoteloperatorinfo.this,failure,
													Toast.LENGTH_LONG).show();

										}else if(key.equalsIgnoreCase("hotels")){
											
											JSONArray cast = jobj.getJSONArray("hotels");
											for (int i=0; i<cast.length(); i++) {
											    JSONObject operObj = cast.getJSONObject(i);
											    
						                        operationsModel=new OperationsModel();
						                        
						                        operationsModel.id=operObj.getString("id");
						                        operationsModel.name=operObj.getString("name");
						                        operationsModel.email=operObj.getString("email");
						                        operationsModel.photo=operObj.getString("photo");

						                        operationsModel.passport=operObj.getString("passport");
						                        operationsModel.mobile=operObj.getString("mobile");
						                        operationsModel.gender=operObj.getString("gender");
						                        operationsModel.nationality=operObj.getString("nationality");

						                        operationsModel.country=operObj.getString("country");
						                        operationsModel.state=operObj.getString("state");
						                        operationsModel.city=operObj.getString("city");
						                        operationsModel.volunteer=operObj.getString("volunteer");

						                        operationsModel.type=operObj.getString("type");
						                        operationsModel.profession=operObj.getString("profession");
						                        operationsModel.data=operObj.getString("data");
						                        operationsModel.created_at=operObj.getString("created_at");

						                        operationsModel.updated_at=operObj.getString("updated_at");

						                        
						                        
						                        arrayList.add(operationsModel);
											    
											    
											    
											}
						                    

											
											
											

										}
								 }	
							        updateList();

	 
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
