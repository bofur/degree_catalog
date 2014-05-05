package org.bofur.adapter;

import java.util.ArrayList;

import org.bofur.bean.Department;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DepartmentAdapter extends ArrayListAdapter {

	public DepartmentAdapter(Context context, ArrayList<Department> facilities) {
		super(context, facilities);
	 }
	
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView != null) return convertView;
		
		Department department = (Department) getItem(position);
		convertView = lInflater.inflate(LAYOUT_ID, parent, false);
		
		((TextView)(convertView.findViewById(android.R.id.text1))).setText(department.getName());
		
		return convertView;
	}
}
