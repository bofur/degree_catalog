package org.bofur.adapter;

import java.util.ArrayList;

import org.bofur.R;
import org.bofur.bean.Degree;
import org.bofur.bean.Indexed;
import org.bofur.bean.Student;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SearchResultAdapter extends ArrayListAdapter<Degree> {
	protected static final int LAYOUT_ID = R.layout.search_result_item;
	
	public SearchResultAdapter(Context context, ArrayList<? extends Indexed> objects) {
		super(context, objects);
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView != null) return convertView;
		
		Degree degree = (Degree) objects.get(position);
		convertView = lInflater.inflate(LAYOUT_ID, parent, false);
		
		Student student = degree.getStudent();
		getView(convertView, R.id.author).setText(student.getName());
		
		getView(convertView, R.id.title).setText(degree.getName());
		getView(convertView, R.id.year).setText(degree.getYear() + "");
		
		return convertView;
	}
	
	protected TextView getView(View view, int id) {
		return (TextView)view.findViewById(id);
	}

}
