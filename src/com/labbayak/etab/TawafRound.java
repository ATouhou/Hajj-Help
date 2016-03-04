package com.labbayak.etab;

import com.labbayak.R;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TawafRound extends Activity {
	GPSTracker gps;
	int loopcounter = 0;
	final int pradakshanaradious = 20;
	double lati, longi;
	TextView position, counter;
	Location prev_location = new Location("");
	Location cur_location = new Location("");
	boolean loop_flag = false;
	CountDownTimer countdowntimer;
	boolean printlog = false;
	Button start, stop;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tawafround);
		position = (TextView) findViewById(R.id.positions);
		counter = (TextView) findViewById(R.id.counter);
		counter.setText("" + loopcounter);
		gps = new GPSTracker(TawafRound.this);
		start = (Button) findViewById(R.id.start_btn);
		
		start.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "Count Started", Toast.LENGTH_LONG).show();
				prev_location = gps.getLocation();
				loopcounter = 0;
				counter.setText("" + loopcounter);
				countdowntimer.start();
			}
		});
		
		//stop = (Button) findViewById(R.id.stop_btn);
		
//		stop.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Toast.makeText(getApplicationContext(), "Count Stoped", Toast.LENGTH_LONG).show();
//				countdowntimer.cancel();
//			}
//		});
		
		if(loopcounter == 7){
			countdowntimer.cancel();
			
		}else{
			countdowntimer = new CountDownTimer(86400000L, 333) {
				public void onTick(long millisUntilFinished) {
					
					if (gps.canGetLocation()) {
						lati = gps.getLatitude();
						longi = gps.getLongitude();
						if (printlog)
							position.setText(" " + lati + "  " + longi);
						Location loc = gps.getLocation();
						if (loc != null) {
							float distanceInMeters = loc.distanceTo(prev_location);
							// Toast.makeText( MainActivity.this,distanceInMeters +
							// " Position Changed "+ loop_flag, 800).show();
							if (printlog)
								ShowToast(distanceInMeters + " Position Changed "
										+ loop_flag, 300);
							// position.setText(position.getText()+" "+loc.getLatitude()+"  "+loc.getLongitude()+" "+loop_flag+"    "+distanceInMeters);
							if (distanceInMeters < pradakshanaradious
									&& loop_flag == true) {
								if (printlog)
									Toast.makeText(TawafRound.this, "Big change",
											Toast.LENGTH_SHORT).show();
								loop_flag = false;
								loopcounter++;
								counter.setText("" + loopcounter);
							} else if (distanceInMeters > pradakshanaradious) {
								loop_flag = true;
							}
						}
					} else {
						// if(printlog)
						ShowToast("GPS Switched off", 3000);
						this.cancel();
					}
				}

				public void onFinish() {
				}
			};
			
		}
	
	}

	void ShowToast(String Message, long time) {
		final Toast toast = Toast.makeText(getApplicationContext(), ""
				+ Message, Toast.LENGTH_SHORT);
		toast.show();
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				toast.cancel();
			}
		}, time);
	}
}