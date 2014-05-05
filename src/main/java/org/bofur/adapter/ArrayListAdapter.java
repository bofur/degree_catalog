package org.bofur.adapter;

import java.util.ArrayList;

import org.bofur.bean.Indexed;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

public abstract class ArrayListAdapter extends BaseAdapter {
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

	public ArrayListAdapter() {
		super();
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

}