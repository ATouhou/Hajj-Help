package com.labbayak.adapter;

import java.util.ArrayList;

import com.labbayak.R;
import com.labbayak.model.OperationsModel;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.TextView;
public class CustomBaseAdapterOperationList extends BaseAdapter{

	Context mContext;
	ArrayList<OperationsModel>arrylist;
	
	
	public CustomBaseAdapterOperationList(Context context,ArrayList<OperationsModel>arrayList) {
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
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
ViewHolder holder = null;
        
        LayoutInflater mInflater = (LayoutInflater) 
            mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.operationitems, null);
            holder = new ViewHolder();
            holder.txtName = (TextView) convertView.findViewById(R.id.operationitems_txt_name);
            holder.txtCity=(TextView)convertView.findViewById(R.id.operationitems_txt_city);
           
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        
       
        holder.txtName.setText(arrylist.get(position).getName());
        holder.txtCity.setText(arrylist.get(position).getCity());

         
        return convertView;	}
	
	 /*private view holder class*/
    private class ViewHolder {
        TextView txtName;
        TextView txtCity;
       
    }
	

}
