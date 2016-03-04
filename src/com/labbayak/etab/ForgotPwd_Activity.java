package com.labbayak.etab;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.labbayak.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ForgotPwd_Activity extends Activity {

	RequestQueue queue;
	String Url,WUrl,reg;
	EditText emailID,PassportNo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.forgotpwd_layout);
		emailID = (EditText)findViewById(R.id.et_username);
		PassportNo = (EditText)findViewById(R.id.et_passportnum);		

	}
	
	public void btn_Submit(View v){
		
		// Request a string response
		String e = emailID.getText().toString().trim();
		String p = PassportNo.getText().toString().trim();
		
		final JSONObject Forgotdetails = new JSONObject();
		 

	      try {
	    	  
	    	  Forgotdetails.put("EmailID", e);
	    	  Forgotdetails.put("Passport",p);
	    	  
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		};
			       Url = "http://labbayak.begoniainfosys.in/api/user/forgot?fields=" + Forgotdetails;;
		
		StringRequest stringRequest = new StringRequest(Request.Method.GET, Url,
	            new Response.Listener<String>() {  
	    @Override
	    public void onResponse(String response) {
	 
	    	Toast.makeText(getApplicationContext(), response + "Please Check Your Mail", Toast.LENGTH_LONG).show();
	    		 
	    }
	}, new Response.ErrorListener() {
	    @Override
	    public void onErrorResponse(VolleyError error) {
	         
	        // Error handling
	       
	        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
	 
	    }
	});
	 
	// Add the request to the queue
	Volley.newRequestQueue(this).add(stringRequest);
		
		
	}

	}