package org.bofur.entities;

import java.util.ArrayList;

import org.bofur.R;
import org.bofur.adapter.ArrayListAdapter;
import org.bofur.bean.Degree;
import org.bofur.bean.Department;
import org.bofur.bean.Student;
import org.bofur.dao.DaoFactory;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class DegreesActivity extends EntitiesActivity {
	private ArrayList<Degree> degrees;
	private ArrayList<Student> students;
	
	private Degree editingDegree;
	private LinearLayout dialogView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.entities_list);
		
		degrees = DaoFactory.getDegreeDao().getAll();
		adapter = new ArrayListAdapter<Department>(this, 
				android.R.layout.simple_list_item_1, degrees);
		
		students = DaoFactory.getStudentDao().getAll();

		setupEntitesList();
	}
	
	protected OnClickListener getOnAddClickListener() {
		return new OnClickListener() {
			public void onClick(View v) {
				String name = getNameFromDialog(R.id.name);
				Integer year = Integer.parseInt(getNameFromDialog(R.id.year));
				Student student = getStudentFromDialog();
				Degree degree = new Degree(name, student, year);
				
				DaoFactory.getDegreeDao().save(degree);
				degrees.add(degree);
				
				adapter.notifyDataSetChanged();
			}
		};
	}
	
	protected OnClickListener getOnEditClickListener(final int position) {
		editingDegree = (Degree)adapter.getItem(position);
		return new OnClickListener() {
			public void onClick(View v) {
				editingDegree.setName(getNameFromDialog(R.id.name));
				editingDegree.setYear(Integer.parseInt(getNameFromDialog(R.id.year)));
				editingDegree.setStudent(getStudentFromDialog());
				
				DaoFactory.getDegreeDao().update(editingDegree);
				adapter.notifyDataSetChanged();
			}
		};
	}

	@Override
	void removeEntity(int position) {
		Degree degree = (Degree) adapter.getItem(position);
        DaoFactory.getDegreeDao().remove(degree);
        degrees.remove(position);
	}
	
	@Override
	protected View createDialogView() {
		LayoutInflater inflator = getLayoutInflater();
		dialogView = (LinearLayout) inflator.inflate(R.layout.degree_dialog, null);
		
		ArrayListAdapter<Student> adapter = 
				new ArrayListAdapter<Student>(this, android.R.layout.select_dialog_item, students);
		
		Spinner spinner = (Spinner)dialogView.findViewById(R.id.student);
		spinner.setAdapter(adapter);
		
		return dialogView;
	}

	@Override
	protected void onShowDilog(AlertDialog dialog) {
		EditText nameText = (EditText)dialogView.findViewById(R.id.name);
		nameText.setText(editingDegree.getName());
		nameText.setSelectAllOnFocus(true);
		
		EditText yearText = (EditText)dialogView.findViewById(R.id.year);
		yearText.setText(editingDegree.getYear() + "");
		nameText.setSelectAllOnFocus(true);
		
		Spinner spinner = (Spinner)dialogView.findViewById(R.id.student);
		spinner.setSelection(students.indexOf(editingDegree.getStudent()));
	}
	

	protected void validate() throws Exception {
		if (getNameFromDialog(R.id.name).isEmpty()) throw new Exception("Введите название");
		if (getNameFromDialog(R.id.year).isEmpty()) throw new Exception("Введите год");
		if (getNameFromDialog(R.id.year).length() != 4) throw new Exception("Неверно введен год");
	}

	private String getNameFromDialog(int fieldId) {
		return ((EditText)dialogView.findViewById(fieldId)).getText().toString();
	}
	
	private Student getStudentFromDialog() {
		Spinner spinner = (Spinner)dialogView.findViewById(R.id.student);
		int position = spinner.getSelectedItemPosition();
		return (Student)students.get(position);
	}
}
