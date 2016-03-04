package com.labbayak.adapter;

import java.util.ArrayList;

import com.labbayak.R;
import com.labbayak.model.OperatorPackagesModel;

import android.app.Activity;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomBaseAdapterOperatorPackages extends BaseAdapter{

	Context mContext;
	ArrayList<OperatorPackagesModel>arrylist;
	


	public CustomBaseAdapterOperatorPackages(Context context,ArrayList<OperatorPackagesModel>arrayList) {
		// TODO Auto-generated constructor stub
		 
		mContext=context;
		arrylist=arrayList;
		
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arrylist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
ViewHolder holder = null;
        
        LayoutInflater mInflater = (LayoutInflater) 
            mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.operatorpackeslist, null);
            holder = new ViewHolder();
            holder.txtName = (TextView) convertView.findViewById(R.id.operatorPackagesName);
            holder.txtDetails=(TextView)convertView.findViewById(R.id.operatorPackagesDetails);
           
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        
       
        holder.txtName.setText(arrylist.get(position).getPackagename());
        holder.txtDetails.setText(arrylist.get(position).getDetails());
       
         
        return convertView;
        }
	
	 /*private view holder class*/
    private class ViewHolder {
        TextView txtName;
        TextView txtDetails;
       
    }
}