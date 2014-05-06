package org.bofur;

import org.bofur.bean.Department;
import org.bofur.bean.Facility;
import org.bofur.bean.Speciality;

import android.widget.Button;

public class SearchActivityModerator {
	private Button facilityBtn;
	private Button departmentBtn;
	private Button specialityBtn;
	
	private Facility facility;
	private Department department;
	private Speciality speciality;
	
	public SearchActivityModerator (
			Button facilityBtn, Button departmentBtn, Button specialityBtn) {
		this.facilityBtn = facilityBtn;
		this.departmentBtn = departmentBtn;
		this.specialityBtn = specialityBtn;
	}
	
	public void set(Object object) {
		Class cls = object.getClass();
		
		if 		(cls.equals(Facility.class))	set((Facility)object);
		else if (cls.equals(Department.class))	set((Department)object);
		else if (cls.equals(Speciality.class))	set((Speciality)object);
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
	
	private void set(Facility facility) {
		this.facility = facility;
		facilityBtn.setText(facility.getName());
		departmentBtn.setEnabled(true);
	}
	
	private void set(Department department) {
		this.department = department;
		departmentBtn.setText(department.getName());
		specialityBtn.setEnabled(true);
	}
	
	private void set(Speciality speciality) {
		this.speciality = speciality;
		specialityBtn.setText(speciality.getName());
	}
}
