package com.labbayak.etab;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonObjectRequest;
import com.labbayak.R;
import com.labbayak.app.AppController;
import com.labbayak.model.OperationsModel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class OperationDeatails extends Activity{
    private static String TAG = OperationDeatails.class.getSimpleName();
    
	 // json object response url
    private String urlJsonObj = "http://labbayak.begoniainfosys.in/api/operator/enquiry";
    

	private TextView txtName;
	private TextView txtMobile;
	private TextView txtEmail;
	private TextView txtCity;
	
	private Button btnEnquiry;
	private Button btnSubmit;
	private Button btnCancel;
	
	private LinearLayout linearLayoutEnquiry;
	private EditText editMsg;
	
	 // Progress dialog
    private ProgressDialog pDialog;

	
	private String strName,strMobile,strCity,strEmail,strMsg;
	
	private SharedPreferences prefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.operationdeatils);
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
		  
		  }else if(prefs.getString("ClassName", "").equalsIgnoreCase("HajjOperationInfo")){
			  
			  strName = prefs.getString("HajjOperation_Name", "");
			  strMobile = prefs.getString("HajjOperation_Mobile", "");
			  strCity = prefs.getString("HajjOperation_City", "");
			  strEmail = prefs.getString("HajjOperation_Email", ""); 
		  }
		  
		  
		  txtName=(TextView)findViewById(R.id.operationdeatils_txt_name);
		  txtMobile=(TextView)findViewById(R.id.operationdeatils_txt_mobile);
		  txtEmail=(TextView)findViewById(R.id.operationdeatils_txt_email);
		  txtCity=(TextView)findViewById(R.id.operationdeatils_txt_city);
		  editMsg=(EditText)findViewById(R.id.operationdeatils_edit_msg);
		  linearLayoutEnquiry=(LinearLayout)findViewById(R.id.operationdeatils_layout_enquiry);
		  
		  btnEnquiry=(Button)findViewById(R.id.operationdeatils_btn_enquiry);
		  btnSubmit=(Button)findViewById(R.id.operationdeatils_btn_submit);
		  btnCancel=(Button)findViewById(R.id.operationdeatils_btn_cancel);

		  
		  btnEnquiry.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				btnEnquiry.setVisibility(View.GONE);
				linearLayoutEnquiry.setVisibility(View.VISIBLE);
				
			}
		});
		  
		  btnCancel.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					btnEnquiry.setVisibility(View.VISIBLE);
					linearLayoutEnquiry.setVisibility(View.GONE);
					
				}
			});
		  
		  btnSubmit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try{
				JSONObject json=new JSONObject();
				
				json.put("operator", 10);
				json.put("user", 1);

				json.put("message", editMsg.getText().toString());
				String url=urlJsonObj+"?fields="+json.toString();

				makeEnquiryRequest(url);
				}catch(JSONException exception){
					
				}
			}

		
		});
		  
		  
		  txtName.setText(strName);
		  txtMobile.setText(strMobile);
		  txtCity.setText(strCity);
		  txtEmail.setText(strEmail);


		
	
		
	}
	private void makeEnquiryRequest(String url) {
		// TODO Auto-generated method stub
		
		 showpDialog();
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

										Toast.makeText(OperationDeatails.this,failure,
													Toast.LENGTH_LONG).show();

										}else if(key.equalsIgnoreCase("success")){
											
											btnEnquiry.setVisibility(View.VISIBLE);
											linearLayoutEnquiry.setVisibility(View.GONE);
											
										}
								 }	
	 
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
