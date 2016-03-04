package com.labbayak.adapter;

import java.util.List;

import com.labbayak.R;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class CustomBaseAdapterPackagesList extends BaseAdapter{
	public static final String MyPREFERENCES = "package" ;
	
	Context mContext;
	List<String>packageList;
	SharedPreferences sharedpreferences ;	
	 SharedPreferences.Editor editor ;

	
	public CustomBaseAdapterPackagesList(Context context,List<String>list) {
		// TODO Auto-generated constructor stub
		mContext=context;
		packageList=list;
		sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);	
		editor = sharedpreferences.edit();
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return packageList.size();
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
            convertView = mInflater.inflate(R.layout.packageitems, null);
            holder = new ViewHolder();
            holder.txtTitle = (TextView) convertView.findViewById(R.id.packageTitle);
            holder.radioGroup=(RadioGroup)convertView.findViewById(R.id.myRadioGroup);
            holder.mradioButtonYes=(RadioButton)convertView.findViewById(R.id.yes);
            holder.mradioButtonNo=(RadioButton)convertView.findViewById(R.id.no);
            holder.mradioButtonLater=(RadioButton)convertView.findViewById(R.id.later);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
       
        holder.radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// find which radio button is selected
				if(checkedId == R.id.yes) {
					editor.putString(packageList.get(position), "yes");
					editor.commit();
					Toast.makeText(mContext, "choice: Yes", 
							Toast.LENGTH_SHORT).show();
				} else if(checkedId == R.id.no) {
					editor.putString(packageList.get(position), "no");
					editor.commit();
					Toast.makeText(mContext, "choice: No", 
							Toast.LENGTH_SHORT).show();
				} else {
					editor.putString(packageList.get(position), "later");
					editor.commit();
					Toast.makeText(mContext, "choice: Later", 
							Toast.LENGTH_SHORT).show();
				}
			}
			
		});
       String savedStatus=sharedpreferences.getString(packageList.get(position), null);
       if(savedStatus!=null  ){
    	  if(savedStatus.equalsIgnoreCase("yes")){
    		  holder.radioGroup.check(R.id.yes);
    	  }else if(savedStatus.equalsIgnoreCase("no")){
    		  holder.radioGroup.check(R.id.no);
    		  
    	  }else if(savedStatus.equalsIgnoreCase("later")){
    		  holder.radioGroup.check(R.id.later);
    		  
    	  }
       }
        holder.txtTitle.setText((position+1)+"."+packageList.get(position));
         
        return convertView;
        
	}
	 /*private view holder class*/
    public class ViewHolder {
        TextView txtTitle;
        RadioGroup radioGroup;
        RadioButton mradioButtonYes, mradioButtonNo, mradioButtonLater;
    }
}
