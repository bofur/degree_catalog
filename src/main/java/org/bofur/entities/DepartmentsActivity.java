package org.bofur.entities;

import java.util.ArrayList;

import org.bofur.R;
import org.bofur.adapter.ArrayListAdapter;
import org.bofur.bean.Department;
import org.bofur.bean.Facility;
import org.bofur.dao.DaoFactory;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class DepartmentsActivity extends EntitiesActivity {
	private ArrayList<Department> departments;
	private ArrayList<Facility> facilities;
	
	private Department editingDepartment;
	private LinearLayout dialogView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.entities_list);
		
		departments = DaoFactory.getDepartmentDao().getAll();
		adapter = new ArrayListAdapter<Department>(this, 
				android.R.layout.simple_list_item_1, departments);
		
		facilities = DaoFactory.getFacilityDao().getAll();

		setupEntitesList();
	}

	protected OnClickListener getOnAddClickListener() {
		return new OnClickListener() {
			public void onClick(View v) {
				Department departmetn = 
						new Department(getNameFromDialog(), getFacilityFromDialog());
				DaoFactory.getDepartmentDao().save(departmetn);
				departments.add(departmetn);
				
				adapter.notifyDataSetChanged();
			}
		};
	}
	
	protected OnClickListener getOnEditClickListener(final int position) {
		editingDepartment = (Department)adapter.getItem(position);
		return new OnClickListener() {
			public void onClick(View v) {
				editingDepartment.setName(getNameFromDialog());
				editingDepartment.setFacility(getFacilityFromDialog());
				
				DaoFactory.getDepartmentDao().update(editingDepartment);
				adapter.notifyDataSetChanged();
			}
		};
	}

	@Override
	void removeEntity(int position) {
        Department department = (Department) adapter.getItem(position);
        DaoFactory.getDepartmentDao().remove(department);
        departments.remove(position);
	}

	@Override
	protected View createDialogView() {
		LayoutInflater inflator = getLayoutInflater();
		dialogView = (LinearLayout) inflator.inflate(R.layout.branch_dialog, null);
		
		ArrayListAdapter<Facility> adapter = 
				new ArrayListAdapter<Facility>(this, android.R.layout.select_dialog_item, facilities);
		
		Spinner spinner = (Spinner)dialogView.findViewById(R.id.super_branch);
		spinner.setAdapter(adapter);
		
		return dialogView;
	}

	@Override
	protected void onShowDilog(AlertDialog dialog) {
		EditText nameText = (EditText)dialogView.findViewById(R.id.name);
		nameText.setText(editingDepartment.getName());
		nameText.setSelectAllOnFocus(true);
		
		Spinner spinner = (Spinner)dialogView.findViewById(R.id.super_branch);
		spinner.setSelection(facilities.indexOf(editingDepartment.getFacility()));
	}

	protected void validate() throws Exception {
		if (getNameFromDialog().isEmpty()) throw new Exception("Введите название");
	}
	
	private String getNameFromDialog() {
		return ((EditText)dialogView.findViewById(R.id.name)).getText().toString();
	}
	
	private Facility getFacilityFromDialog() {
		Spinner spinner = (Spinner)dialogView.findViewById(R.id.super_branch);
		int position = spinner.getSelectedItemPosition();
		return (Facility)facilities.get(position);
	}
}
