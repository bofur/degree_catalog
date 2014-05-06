package org.bofur.adapter;

import java.util.ArrayList;

import org.bofur.bean.Department;
import org.bofur.bean.Indexed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ArrayListAdapter<T extends Indexed> extends BaseAdapter {
	protected static final int LAYOUT_ID = android.R.layout.select_dialog_item;
	
	protected ArrayList<? extends Indexed> objects;
	protected LayoutInflater lInflater;
	
	private Context ctx;
	
	public ArrayListAdapter(Context context, ArrayList<? extends Indexed> objects) {
	    ctx = context;
	    this.objects = objects;
	    lInflater = (LayoutInflater) ctx
	        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView != null) return convertView;
		
		T object = (T)getItem(position);
		convertView = lInflater.inflate(LAYOUT_ID, parent, false);
		
		((TextView)(convertView.findViewById(android.R.id.text1))).setText(object.getName());
		
		return convertView;
	}
	
	public int getCount() {
		return objects.size();
	}

	public Object getItem(int position) {
		return objects.get(position);
	}
	
	public T getTypedItem(int position) {
		return (T)getItem(position);
	}

	public long getItemId(int position) {
		return objects.get(position).getId();
	}

}