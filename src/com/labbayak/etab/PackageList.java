package com.labbayak.etab;

import java.util.ArrayList;
import java.util.List;

import com.labbayak.R;
import com.labbayak.adapter.CustomBaseAdapterPackagesList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class PackageList extends Activity{
	
	
	private static final String[] CONTENT = new String[] {
	        "Visa", "Passport", "Sim Cards", "Cash", "Credit Cards", 
	        "ATM Cards", "Soap","Shampoo", "Nail Clipper","Tooth Paste" };
	
	private ListView listViewPackages;
	List<String> packageList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.packagelist);
		
		listViewPackages=(ListView)findViewById(R.id.packagelistView);
		packageList = new ArrayList<String>();
	        for (int i = 0; i < CONTENT.length; i++) {
	        	packageList.add(CONTENT[i]);
	        }
	        
	        CustomBaseAdapterPackagesList adapter = new CustomBaseAdapterPackagesList(this, packageList);
	        listViewPackages.setAdapter(adapter);
		
		
	}

}
