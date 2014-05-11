package org.bofur;

import org.bofur.bean.Degree;
import org.bofur.bean.Department;
import org.bofur.bean.Facility;
import org.bofur.bean.Speciality;
import org.bofur.bean.Student;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DegreeActivity extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.degree_info);
        
        Intent intent = getIntent();
        Degree degree = (Degree)intent.getParcelableExtra("degree");
        
        ((TextView)findViewById(R.id.title)).setText(degree.getName());
        ((TextView)findViewById(R.id.year)).setText(degree.getYear() + "");
        
        Student student = degree.getStudent();
        ((TextView)findViewById(R.id.author)).setText(student.getName());
        
        Speciality speciality = student.getSpeciality();
        ((TextView)findViewById(R.id.speciality)).setText(speciality.getName());
        
        Department department = speciality.getDepartment();
        ((TextView)findViewById(R.id.department)).setText(department.getName());
        
        Facility facility = department.getFacility();
        ((TextView)findViewById(R.id.facility)).setText(facility.getName());
    }
}
