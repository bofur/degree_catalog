package org.bofur.adapter;

import java.util.ArrayList;

import org.bofur.R;
import org.bofur.bean.Degree;
import org.bofur.bean.Student;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SearchResultAdapter extends BaseAdapter {
	private static final int LAYOUT_ID = R.layout.search_result_item; 
	
	private Context ctx;
	private LayoutInflater lInflater;
	private ArrayList<Degree> objects;
	  
	public SearchResultAdapter(Context context, ArrayList<Degree> degrees) {
	    ctx = context;
	    objects = degrees;
	    lInflater = (LayoutInflater) ctx
	        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	 }
	
	public int getCount() {
		return objects.size();
	}

	public Object getItem(int position) {
		return objects.get(position);
	}

	public long getItemId(int position) {
		return objects.get(position).getId();
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView != null) return convertView;
		
		Degree degree = objects.get(position);
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
