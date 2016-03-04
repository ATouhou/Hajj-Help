package com.labbayak.etab;

import com.labbayak.R;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class HomeActivity extends Activity {
	private String[] mNavigationDrawerItemTitles;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);

		mNavigationDrawerItemTitles = getResources().getStringArray(
				R.array.navigation_drawer_items_array);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		ObjectDrawerItem[] drawerItem = new ObjectDrawerItem[3];

		drawerItem[0] = new ObjectDrawerItem(R.drawable.ic_launcher, "Create");
		drawerItem[1] = new ObjectDrawerItem(R.drawable.ic_launcher, "Read");
		drawerItem[2] = new ObjectDrawerItem(R.drawable.ic_launcher, "Help");
		DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this,
				R.layout.listview_item_row, drawerItem);
		mDrawerList.setAdapter(adapter);

	}

	public void btn_Hujjguide(View v) {
		Intent i = new Intent(getApplicationContext(), Hujj_Activity.class);
		startActivity(i);

	}

	public void btn_Umrahguide(View v) {
		Intent i = new Intent(getApplicationContext(), Umrah_Activity.class);
		startActivity(i);

	}

	public void btn_PrayerTimings(View v) {

		Intent i = new Intent(getApplicationContext(),
				Prayertimings_Activity.class);
		startActivity(i);

	}

	public void btn_Quran(View v) {

		Intent i = new Intent(getApplicationContext(), QuranActivity.class);
		startActivity(i);

	}

	public void btnClick_QiblaCompass(View v) {

		Intent i = new Intent(getApplicationContext(),
				QiblaActivity.class);
		startActivity(i);

	}

	public void btn_Calender(View v) {

		Intent i = new Intent(getApplicationContext(), Calender_Activity.class);
		startActivity(i);

	}

	public void btn_Weather(View v) {

		Intent i = new Intent(getApplicationContext(), Weather.class);
		startActivity(i);

	}

	public void btn_HealthTips(View v) {
		Intent i = new Intent(getApplicationContext(),
				Healthtips_Activity.class);
		startActivity(i);

	}

	public void btn_packingList(View v) {
		Intent i = new Intent(getApplicationContext(), PackageList.class);
		startActivity(i);

	}

	public void btnClick_HotelNavigator(View v) {

		Intent i = new Intent(getApplicationContext(),
				HotelNavigator_Activity.class);
		startActivity(i);

	}

	public void btnClick_MinaNavigation(View v) {

		Intent i = new Intent(getApplicationContext(), MinaCamp.class);
		startActivity(i);

	}

	public void btn_Arafathnavigation(View v) {

		Intent i = new Intent(getApplicationContext(), ArafathNav.class);
		startActivity(i);

	}

	public void btn_Location(View v) {

		Intent i = new Intent(getApplicationContext(), Location_Activity.class);
		startActivity(i);

	}

	public void btn_mayIHelpU(View v) {

		Intent i = new Intent(getApplicationContext(), MayiHelpU_Activity.class);
		startActivity(i);

	}

	public void btn_LostnFound(View v) {

		// Intent i = new Intent(getApplicationContext(),
		// HotelNavigator_Activity.class);
		// startActivity(i);

	}

	public void btn_Ethasbih(View v) {

		Intent i = new Intent(getApplicationContext(), EThasbih.class);
		startActivity(i);

	}

	public void btnClick_TawafRound(View v) {

		Intent i = new Intent(getApplicationContext(), TawafRound.class);
		startActivity(i);

	}

	public void btn_GoodandBad(View v) {

		// Intent i = new Intent(getApplicationContext(), TawafRound.class);
		// startActivity(i);

	}

	public void btn_hajjinfo(View v) {

		Intent i = new Intent(getApplicationContext(), HajjOperatorInfo.class);
		startActivity(i);

	}

	public void btn_HotelOperatorinfo(View v) {
		Intent i = new Intent(getApplicationContext(), Hoteloperatorinfo.class);
		startActivity(i);

	}

	public void btn_Quotes(View v) {

		Intent i = new Intent(getApplicationContext(), Quotes.class);
		startActivity(i);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		// noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
