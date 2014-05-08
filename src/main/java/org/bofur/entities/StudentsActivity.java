package org.bofur.entities;

import java.util.ArrayList;

import org.bofur.R;
import org.bofur.adapter.ArrayListAdapter;
import org.bofur.bean.Department;
import org.bofur.bean.Speciality;
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

public class StudentsActivity extends EntitiesActivity {
	private ArrayList<Speciality> specialities;
	private ArrayList<Student> students;
	
	private Student editingStudent;
	private LinearLayout dialogView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.entities_list);
		
		students = DaoFactory.getStudentDao().getAll();
		adapter = new ArrayListAdapter<Department>(this, 
				android.R.layout.simple_list_item_1, students);
		
		specialities = DaoFactory.getSpecialityDao().getAll();

		setupEntitesList();
	}

	protected OnClickListener getOnAddClickListener() {
		return new OnClickListener() {
			public void onClick(View v) {
				String firstName = getNameFromDialog(R.id.first_name_text);
				String secondName = getNameFromDialog(R.id.second_name_text);
				String lastName = getNameFromDialog(R.id.last_name_text);
				Speciality speciality = getSpecialityFromDialog();
				Student student = new Student(firstName, secondName, lastName, speciality);
				
				DaoFactory.getStudentDao().save(student);
				students.add(student);
				
				adapter.notifyDataSetChanged();
			}
		};
	}
	
	protected OnClickListener getOnEditClickListener(final int position) {
		editingStudent = (Student)adapter.getItem(position);
		return new OnClickListener() {
			public void onClick(View v) {
				editingStudent.setFirstName(getNameFromDialog(R.id.first_name_text));
				editingStudent.setSecondName(getNameFromDialog(R.id.second_name_text));
				editingStudent.setLastName(getNameFromDialog(R.id.last_name_text));
				editingStudent.setFirstName(getNameFromDialog(R.id.first_name_text));
				editingStudent.setSpeciality(getSpecialityFromDialog());
				
				DaoFactory.getStudentDao().update(editingStudent);
				adapter.notifyDataSetChanged();
			}
		};
	}

	@Override
	void removeEntity(int position) {
		Student student = (Student) adapter.getItem(position);
        DaoFactory.getStudentDao().remove(student);
        students.remove(position);
	}

	@Override
	protected View createDialogView() {
		LayoutInflater inflator = getLayoutInflater();
		dialogView = (LinearLayout) inflator.inflate(R.layout.student_dialog, null);
		
		ArrayListAdapter<Speciality> adapter = 
				new ArrayListAdapter<Speciality>(this, android.R.layout.select_dialog_item, specialities);
		
		Spinner spinner = (Spinner)dialogView.findViewById(R.id.speciality);
		spinner.setAdapter(adapter);
		
		return dialogView;
	}

	@Override
	protected void onShowDilog(AlertDialog dialog) {
		EditText firstNameText = (EditText)dialogView.findViewById(R.id.first_name_text);
		firstNameText.setText(editingStudent.getFirstName());
		firstNameText.setSelectAllOnFocus(true);
		
		EditText secondNameText = (EditText)dialogView.findViewById(R.id.second_name_text);
		secondNameText.setText(editingStudent.getSecondName());
		
		EditText lastNameText = (EditText)dialogView.findViewById(R.id.last_name_text);
		lastNameText.setText(editingStudent.getLastName());
		lastNameText.setSelectAllOnFocus(true);
		
		Spinner spinner = (Spinner)dialogView.findViewById(R.id.speciality);
		spinner.setSelection(specialities.indexOf(editingStudent.getSpeciality()));
	}
	
	protected void validate() throws Exception {
		if (getNameFromDialog(R.id.first_name_text).isEmpty()) throw new Exception("Введите имя");
		if (getNameFromDialog(R.id.second_name_text).isEmpty()) throw new Exception("Введите отчество");
		if (getNameFromDialog(R.id.last_name_text).isEmpty()) throw new Exception("Введите фамилию");
	}

	private String getNameFromDialog(int fieldId) {
		return ((EditText)dialogView.findViewById(fieldId)).getText().toString();
	}
	
	private Speciality getSpecialityFromDialog() {
		Spinner spinner = (Spinner)dialogView.findViewById(R.id.speciality);
		int position = spinner.getSelectedItemPosition();
		return (Speciality)specialities.get(position);
	}
}
