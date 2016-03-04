package com.labbayak.etab;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;

import com.labbayak.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EThasbih extends Activity {
	int count=0,DateNotEqulValue;
    Timer T;
	int quantity=0;
	int int_currentDayOfMonth=0;
	TextView tv_dis_TotalCount,tv_dis_TodayCount;
	String Total_Sp_Value , TotalCount_inSP,AppStartDate,TodayDisDate,TodayCount_Dis_Value,SpTodayvalue,today_Count ;
	LinearLayout todayLayout;
	SharedPreferences prefs;
	private Calendar calendar;
	private int inccurrentDayOfWeek=0;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ethasbih);
		
		tv_dis_TotalCount = (TextView) findViewById(R.id.tv_TotalCount);
		tv_dis_TodayCount = (TextView) findViewById(R.id.tv_TodayCount);
		todayLayout = (LinearLayout) findViewById(R.id.LL_Today);
		
		AppStartDate = new SimpleDateFormat("ddMMyyyy").format(new Date());
		
		 prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		TotalCount_inSP = prefs.getString("TotalCount_Key", "0");
		today_Count=prefs.getString("TodayCount_Key", "0");
		int_currentDayOfMonth=prefs.getInt("currentDayOfMonth", 0);
		inccurrentDayOfWeek=prefs.getInt("inccurrentDayOfWeek", 0);
		
		//getting local time, date, day of week and other details in local timezone
		calendar = Calendar.getInstance(TimeZone.getDefault());
		  Date currentTime = calendar.getTime();
	        int currentDay = calendar.get(Calendar.DATE);
	        int currentMonth = calendar.get(Calendar.MONTH) + 1;
	        int currentYear = calendar.get(Calendar.YEAR);
	        int currentDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
	        int currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
	        int CurrentDayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
		
	        //inccurrentDayOfWeek=currentDayOfWeek;
	        
	        if(currentDayOfWeek!=7&&(inccurrentDayOfWeek+1)>=8){
	        	TotalCount_inSP=Integer.toString(0);
	        	inccurrentDayOfWeek=0;
	        	prefs.edit().putString("TotalCount_inSP", TotalCount_inSP).commit();
	        	prefs.edit().putInt("inccurrentDayOfWeek", currentDayOfWeek).commit();

	        }else{
	        	prefs.edit().putInt("inccurrentDayOfWeek", currentDayOfWeek).commit();
	        	
	        }
	        
	       if(currentDayOfMonth!=int_currentDayOfMonth){
				prefs.edit().putInt("currentDayOfMonth", currentDayOfMonth).commit();
				prefs.edit().putInt("today_Count", 0).commit();
				quantity=0;
				tv_dis_TotalCount.setText(TotalCount_inSP);
				tv_dis_TodayCount.setText(""+quantity);
	       }else{
	    	   tv_dis_TotalCount.setText(TotalCount_inSP);
	    	   quantity = Integer.parseInt(today_Count);
				tv_dis_TodayCount.setText(""+quantity);

	       } 
	      tv_dis_TodayCount.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
				int totalvalue=Integer.parseInt(TotalCount_inSP)+1;
				TotalCount_inSP=String.valueOf(totalvalue);
				prefs.edit().putString("TotalCount_Key", TotalCount_inSP).commit();
				tv_dis_TotalCount.setText(TotalCount_inSP);	
				}
		});
	}
	public void btn_Count(View v){
		quantity = quantity + 1;
		
		
		
		display(quantity);		
	}	
	public void display(int i) {
		// TODO Auto-generated method stub
		tv_dis_TodayCount.setText("" + i);	
		TodayCount_Dis_Value = String.valueOf(quantity);
		prefs.edit().putString("TodayCount_Key", TodayCount_Dis_Value).commit();


	}	
	
	
	
	public void btn_TodayCount(View v){
		
		//todayLayout.setVisibility(View.VISIBLE);
		TodayDisDate = new SimpleDateFormat("ddMMyyyy").format(new Date());
		
		if(AppStartDate.equals(TodayDisDate)){
			
			TodayCount_Dis_Value = String.valueOf(quantity);
			
			tv_dis_TodayCount.setText(TodayCount_Dis_Value);
			prefs.edit().putString("TodayCount_Key", TodayCount_Dis_Value).commit();
			
			
		}
		else{
			
			SpTodayvalue = prefs.getString("TodayCountKey", "");
			if(SpTodayvalue == TotalCount_inSP){
				tv_dis_TodayCount.setText(TodayCount_Dis_Value);
			}else{
				DateNotEqulValue = Integer.parseInt(SpTodayvalue) - Integer.parseInt(TotalCount_inSP);			
				tv_dis_TodayCount.setText(DateNotEqulValue);
			}
			
		}    
		
    }
	
	public void ReSetValues(View v){
		
		tv_dis_TodayCount.setText("0");	
		tv_dis_TotalCount.setText("0");
		quantity = 0;
		TotalCount_inSP="0";
		TodayCount_Dis_Value="0";
		prefs.edit().putString("TodayCount_Key", "0").commit();
		prefs.edit().putString("TotalCount_Key", "0").commit();


		
    }
	}
