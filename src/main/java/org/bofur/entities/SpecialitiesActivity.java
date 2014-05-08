package org.bofur.entities;

import java.util.ArrayList;

import org.bofur.R;
import org.bofur.adapter.ArrayListAdapter;
import org.bofur.bean.Department;
import org.bofur.bean.Speciality;
import org.bofur.dao.DaoFactory;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class SpecialitiesActivity extends EntitiesActivity {
	private ArrayList<Speciality> specialities;
	private ArrayList<Department> departments;
	
	private Speciality editingSpeciality;
	private LinearLayout dialogView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.entities_list);
		
		specialities = DaoFactory.getSpecialityDao().getAll();
		adapter = new ArrayListAdapter<Speciality>(this, 
				android.R.layout.simple_list_item_1, specialities);
		
		departments = DaoFactory.getDepartmentDao().getAll();

		setupEntitesList();
	}
	
	protected OnClickListener getOnAddClickListener() {
		return new OnClickListener() {
			public void onClick(View v) {
				Speciality speciality = 
						new Speciality(getNameFromDialog(), getDepartmentFromDialog());
				DaoFactory.getSpecialityDao().save(speciality);
				specialities.add(speciality);
				
				adapter.notifyDataSetChanged();
			}
		};
	}
	
	protected OnClickListener getOnEditClickListener(final int position) {
		editingSpeciality = (Speciality)adapter.getItem(position);
		return new OnClickListener() {
			public void onClick(View v) {
				editingSpeciality.setName(getNameFromDialog());
				editingSpeciality.setDepartment(getDepartmentFromDialog());
				
				DaoFactory.getSpecialityDao().update(editingSpeciality);
				adapter.notifyDataSetChanged();
			}
		};
	}
	
	@Override
	void removeEntity(int position) {
        Speciality speciality = (Speciality) adapter.getItem(position);
        DaoFactory.getSpecialityDao().remove(speciality);
        specialities.remove(position);
	}
	
	@Override
	protected View createDialogView() {
		LayoutInflater inflator = getLayoutInflater();
		dialogView = (LinearLayout) inflator.inflate(R.layout.branch_dialog, null);
		
		ArrayListAdapter<Speciality> adapter = 
				new ArrayListAdapter<Speciality>(this, android.R.layout.select_dialog_item, departments);
		
		Spinner spinner = (Spinner)dialogView.findViewById(R.id.super_branch);
		spinner.setAdapter(adapter);
		
		return dialogView;
	}
	
	@Override
	protected void onShowDilog(AlertDialog dialog) {
		EditText nameText = (EditText)dialogView.findViewById(R.id.name);
		nameText.setText(editingSpeciality.getName());
		nameText.setSelectAllOnFocus(true);
		
		Spinner spinner = (Spinner)dialogView.findViewById(R.id.super_branch);
		spinner.setSelection(departments.indexOf(editingSpeciality.getDepartment()));
	}
	
	protected void validate() throws Exception {
		if (getNameFromDialog().isEmpty()) throw new Exception("Введите название");
	}
	
	private String getNameFromDialog() {
		return ((EditText)dialogView.findViewById(R.id.name)).getText().toString();
	}
	
	private Department getDepartmentFromDialog() {
		Spinner spinner = (Spinner)dialogView.findViewById(R.id.super_branch);
		int position = spinner.getSelectedItemPosition();
		return (Department)departments.get(position);
	}
}
