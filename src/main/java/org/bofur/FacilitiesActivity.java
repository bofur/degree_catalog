package org.bofur;

import java.util.ArrayList;

import org.bofur.adapter.ArrayListAdapter;
import org.bofur.bean.Facility;
import org.bofur.dao.DaoFactory;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
//import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class FacilitiesActivity extends Activity implements OnItemClickListener {
	
	private ArrayListAdapter<Facility> adapter;
	private ArrayList<Facility> facilities;
	
	EditText facilityName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.entities_list);
		
		facilities = DaoFactory.getFacilityDao().getAll();
		adapter = new ArrayListAdapter<Facility>(this, 
				android.R.layout.simple_list_item_1, facilities);
		
		ListView entities = (ListView)findViewById(R.id.entities);
		entities.setOnItemClickListener(this);
		entities.setAdapter(adapter);
		
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
        Log.i("LOGS", "" + info.position);
        DaoFactory.getFacilityDao().remove(facility);
        facilities.remove(info.position);
        
        adapter.notifyDataSetChanged();
        
        return super.onContextItemSelected(item);
	}

	public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
		final Facility facility = (Facility)adapter.getItem(position);
		showDialog(R.string.edit_prompt, new OnClickListener() {
			public void onClick(View v) {
				facility.setName(facilityName.getText().toString());
				DaoFactory.getFacilityDao().update(facility);
				adapter.notifyDataSetChanged();
			}
		});
		
	}
	
	public void add(View view) {
		showDialog(R.string.edit_prompt, new OnClickListener() {
			public void onClick(View v) {
				Facility facility = new Facility(facilityName.getText().toString());
				DaoFactory.getFacilityDao().save(facility);
				facilities.add(facility);
				
				adapter.notifyDataSetChanged();
			}
		});
	}
	
	private void showDialog(int titleResourceId, final OnClickListener listener) {
		facilityName = new EditText(this);
		final AlertDialog dialog = new AlertDialog.Builder(this)
				.setView(facilityName)
				.setTitle(titleResourceId)
				.setPositiveButton(R.string.ok_button, null)
				.setNegativeButton(R.string.cancel_button, null)
				.create();
		
		dialog.setOnShowListener(new DialogInterface.OnShowListener() {
			private OnClickListener validationListener = new OnClickListener() {
				public void onClick(View v) {
					try {
						validate();
						listener.onClick(v);
						dialog.dismiss();
					} catch(Exception e) {
						showErro(e.getMessage());
					}
				}
			};
			
			public void onShow(DialogInterface d) {
				Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
				button.setOnClickListener(validationListener);
			}
		});
		
		dialog.show();
	}
	
	private void showErro(String message) {
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}
	
	private void validate() throws Exception {
		String name = facilityName.getText().toString();
		if (name.isEmpty()) throw new Exception("Введите название");
	}
}
