package org.bofur;

import java.util.ArrayList;

import org.bofur.adapter.ArrayListAdapter;
import org.bofur.bean.Department;
import org.bofur.bean.Facility;
import org.bofur.dao.DaoFactory;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class DepartmentsActivity extends Activity implements OnItemClickListener{
	private ArrayListAdapter<Department> adapter;
	private ListView entities;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.entities_list);
		
		ArrayList<Department> departments = DaoFactory.getDepartmentDao().getAll();
		adapter = new ArrayListAdapter<Department>(this, 
				android.R.layout.simple_list_item_1, departments);
		
		entities = (ListView)findViewById(R.id.entities);
		entities.setAdapter(adapter);
		entities.setOnItemClickListener(this);
		registerForContextMenu(entities);
	}

	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(0, 0, 0, R.string.remove_button);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();  
        Department department = (Department) adapter.getItem(info.position);
        DaoFactory.getDepartmentDao().remove(department);
        
		adapter = new ArrayListAdapter<Department>(this, 
				android.R.layout.simple_list_item_1, DaoFactory.getFacilityDao().getAll());
		entities.setAdapter(adapter);
        
        return super.onContextItemSelected(item);
	}
	
	public void onItemClick(AdapterView<?> parent, View view, int position,	long id) {
		final Department department = (Department)adapter.getItem(position);
		
		final EditText departmentName = new EditText(this);
		departmentName.setText(department.getName());
		
		final Context context = this;
		
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle(R.string.edit_prompt);
		dialog.setPositiveButton(R.string.ok_button, new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				department.setName(departmentName.getText().toString());
//				DaoFactory.getFacilityDao().update(department);
				adapter = new ArrayListAdapter<Department>(context, 
						android.R.layout.simple_list_item_1, DaoFactory.getDepartmentDao().getAll());
				entities.setAdapter(adapter);
			}
		});
		
		dialog.setNegativeButton(R.string.cancel_button, null);
		
		dialog.setView(departmentName);
		dialog.show();
	}
	
	public void add(View view) {
		final EditText departmentName = new EditText(this);
		final Context context = this;
		
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle(R.string.edit_prompt);
		dialog.setPositiveButton(R.string.ok_button, new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
//				DaoFactory.getDepartmentDao().insert(departmentName.getText().toString());
				adapter = new ArrayListAdapter<Department>(context, 
						android.R.layout.simple_list_item_1, DaoFactory.getDepartmentDao().getAll());
				entities.setAdapter(adapter);
			}
		});
		
		dialog.setNegativeButton(R.string.cancel_button, new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		
		dialog.setView(departmentName);
		dialog.show();
	}
}
