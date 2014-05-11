package org.bofur;

import org.bofur.bean.Department;
import org.bofur.bean.Facility;
import org.bofur.bean.Speciality;

import android.widget.Button;

public class SearchActivityModerator {
	private Button facilityBtn;
	private Button departmentBtn;
	private Button specialityBtn;
	private Button yearBtn;
	
	private Facility facility;
	private Department department;
	private Speciality speciality;
	private String year;
	
	public SearchActivityModerator (
			Button facilityBtn, Button departmentBtn, Button specialityBtn, Button yearBtn) {
		this.facilityBtn = facilityBtn;
		this.departmentBtn = departmentBtn;
		this.specialityBtn = specialityBtn;
		this.yearBtn = yearBtn;
	}
	
	public void set(Object object) {
		Class cls = object.getClass();
		
		if 		(cls.equals(Facility.class))	set((Facility)object);
		else if (cls.equals(Department.class))	set((Department)object);
		else if (cls.equals(Speciality.class))	set((Speciality)object);
		else if (cls.equals(String.class))      set((String)object);
		else throw new IllegalArgumentException();
	}
	
	public Facility getFacility() {
		return facility;
	}
	
	public Department getDepartment() {
		return department;
	}
	
	public Speciality getSpeciality() {
		return speciality;
	}
	
	public String getYear() {
		return year;
	}
	
	private void set(Facility facility) {
		this.facility = facility;
		facilityBtn.setText(facility.getName());
		
		departmentBtn.setText(R.string.select_department);
		departmentBtn.setText(R.string.select_speciality);
		
		departmentBtn.setEnabled(true);
		specialityBtn.setEnabled(false);
		
		department = null;
		speciality = null;
	}
	
	private void set(Department department) {
		this.department = department;
		
		departmentBtn.setText(department.getName());
		
		specialityBtn.setEnabled(true);
		specialityBtn.setText(R.string.select_speciality);
		speciality = null;
	}
	
	private void set(Speciality speciality) {
		this.speciality = speciality;
		specialityBtn.setText(speciality.getName());
	}
	
	private void set(String year) {
		yearBtn.setText(year);
		this.year = year;
	}
}
