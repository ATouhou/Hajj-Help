package com.labbayak.etab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.labbayak.R;

public class CreateFragment extends Fragment {
	 
    public CreateFragment() {
    }
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_create, container, false);
 
        return rootView;
    }
 
}
