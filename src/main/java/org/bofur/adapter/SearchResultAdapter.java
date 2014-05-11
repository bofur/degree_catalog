package org.bofur.adapter;

import java.util.ArrayList;

import org.bofur.R;
import org.bofur.bean.Degree;
import org.bofur.bean.Bean;
import org.bofur.bean.Student;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SearchResultAdapter extends ArrayListAdapter<Degree> {
	
	public SearchResultAdapter(Context context, ArrayList<? extends Bean> objects) {
		super(context, R.layout.search_result_item, objects);
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) 
			convertView = lInflater.inflate(layoutId, parent, false);
		
		Degree degree = (Degree) objects.get(position);
		getView(convertView, R.id.title).setText(degree.getName());
		getView(convertView, R.id.year).setText(degree.getYear() + "");
		
		Student student = degree.getStudent();
		getView(convertView, R.id.author).setText(student.getName());
		
		return convertView;
	}
	
	protected TextView getView(View view, int id) {
		return (TextView)view.findViewById(id);
	}

}
