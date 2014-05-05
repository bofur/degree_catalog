package org.bofur.adapter;

import java.util.ArrayList;

import org.bofur.bean.Speciality;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SpecialityAdapter extends ArrayListAdapter {

	public SpecialityAdapter(Context context, ArrayList<Speciality> specialities) {
		super(context, specialities);
	 }
	
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView != null) return convertView;
		
		Speciality speciality = (Speciality) getItem(position);
		convertView = lInflater.inflate(LAYOUT_ID, parent, false);
		
		((TextView)(convertView.findViewById(android.R.id.text1))).setText(speciality.getName());
		
		return convertView;
	}
}
