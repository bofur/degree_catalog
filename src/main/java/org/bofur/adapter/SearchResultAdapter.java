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

public class SearchResultAdapter extends ArrayListAdapter {
	protected static final int LAYOUT_ID = R.layout.search_result_item;
	
	public SearchResultAdapter(Context context, ArrayList<? extends Indexed> objects) {
		super(context, objects);
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView != null) return convertView;
		
		Degree degree = (Degree) objects.get(position);
		convertView = lInflater.inflate(LAYOUT_ID, parent, false);
		
		getView(convertView, R.id.title).setText(degree.getTitle());
		getView(convertView, R.id.author).setText(getAuthor(degree.getStudent()));
		getView(convertView, R.id.year).setText(degree.getYear() + "");
		
		return convertView;
	}
	
	protected TextView getView(View view, int id) {
		return (TextView)view.findViewById(id);
	}

	private String getAuthor(Student student) {
	    String author = student.getLastName() + " ";
	    author += student.getFirstName().substring(0, 1) + ".";
	    author += student.getSecondName().substring(0, 1) + ".";
	    return author;
	}
}
