package org.bofur.adapter;

import java.util.ArrayList;

import org.bofur.bean.Facility;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FacilityAdapter extends ArrayListAdapter {

	public FacilityAdapter(Context context, ArrayList<Facility> facilities) {
		super(context, facilities);
	 }
	
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView != null) return convertView;
		
		Facility facility = (Facility) getItem(position);
		convertView = lInflater.inflate(LAYOUT_ID, parent, false);
		
		((TextView)(convertView.findViewById(android.R.id.text1))).setText(facility.getName());
		
		return convertView;
	}

}
