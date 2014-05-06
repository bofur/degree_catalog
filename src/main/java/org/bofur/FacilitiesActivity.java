package org.bofur;

import java.util.ArrayList;

import org.bofur.adapter.ArrayListAdapter;
import org.bofur.bean.Facility;
import org.bofur.dao.DaoFactory;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.media.audiofx.AudioEffect.OnControlStatusChangeListener;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class FacilitiesActivity extends Activity implements OnItemClickListener {
	
	private ArrayListAdapter<Facility> adapter;
	private ListView entities;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.entities_list);
		
		final OnCreateContextMenuListener listener = this;
		ArrayList<Facility> facilities = DaoFactory.getFacilityDao().getAll();
		adapter = new ArrayListAdapter<Facility>(this, facilities) {
			protected static final int LAYOUT_ID = android.R.layout.simple_list_item_1;
		};
		
		entities = (ListView)findViewById(R.id.entities);
		entities.setAdapter(adapter);
		entities.setOnItemClickListener(this);
		registerForContextMenu(entities);
	}
	
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(0, 0, 0, R.string.remove_button);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();  
        Facility facility = (Facility) adapter.getItem(info.position);
        DaoFactory.getFacilityDao().remove(facility);
        
		adapter = new ArrayListAdapter<Facility>(this, DaoFactory.getFacilityDao().getAll()) {
			protected static final int LAYOUT_ID = android.R.layout.simple_list_item_1;
		};
		entities.setAdapter(adapter);
        
        return super.onContextItemSelected(item);
	}

	public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
		final Facility facility = (Facility)adapter.getItem(position);
		
		final EditText facilityName = new EditText(this);
		facilityName.setText(facility.getName());
		
		final Context context = this;
		
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle(R.string.edit_prompt);
		dialog.setPositiveButton(R.string.ok_button, new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				facility.setName(facilityName.getText().toString());
				DaoFactory.getFacilityDao().update(facility);
				adapter = new ArrayListAdapter<Facility>(context, DaoFactory.getFacilityDao().getAll()) {
					protected static final int LAYOUT_ID = android.R.layout.simple_list_item_1;
				};
				entities.setAdapter(adapter);
			}
		});
		
		dialog.setNegativeButton(R.string.cancel_button, new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		
		dialog.setView(facilityName);
		dialog.show();
		
	}
	
	public void add(View view) {
		final EditText facilityName = new EditText(this);
		final Context context = this;
		
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle(R.string.edit_prompt);
		dialog.setPositiveButton(R.string.ok_button, new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				DaoFactory.getFacilityDao().insert(facilityName.getText().toString());
				adapter = new ArrayListAdapter<Facility>(context, DaoFactory.getFacilityDao().getAll()) {
					protected static final int LAYOUT_ID = android.R.layout.simple_list_item_1;
				};
				entities.setAdapter(adapter);
			}
		});
		
		dialog.setNegativeButton(R.string.cancel_button, new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		
		dialog.setView(facilityName);
		dialog.show();
		
	}
}
