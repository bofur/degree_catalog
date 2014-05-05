package org.bofur;


import java.util.ArrayList;

import org.bofur.adapter.DepartmentAdapter;
import org.bofur.adapter.FacilityAdapter;
import org.bofur.adapter.SpecialityAdapter;
import org.bofur.bean.Department;
import org.bofur.bean.Facility;
import org.bofur.bean.Speciality;
import org.bofur.dao.DaoFactory;
import org.bofur.dao.DepartmentDao;
import org.bofur.dao.FacilityDao;
import org.bofur.dao.SpecialityDao;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.android.ContextHolder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;

public class SearchActivity extends Activity {
	private static final String DATABASE_NAME = "db"; 
	
	private SQLiteDatabase db;
	
	private Facility facility;
	private Department department;
	private Speciality speciality;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_options);

        db = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        upgradeDataBase(db);
        
        DaoFactory.setDataBase(db);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(org.bofur.R.menu.main, menu);
		return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	// TODO Auto-generated method stub
    	return super.onOptionsItemSelected(item);
    }
    
    public void search(View view) {
    	Intent searchResult = new Intent(this, ResultActivity.class);
    	startActivity(searchResult);
	}
    
    public void selectFacility(View view) {
    	AlertDialog.Builder departmentDialog = new AlertDialog.Builder(this);
    	departmentDialog.setTitle(R.string.select_facility_prompt);

        FacilityDao facilityDao = DaoFactory.getFacilityDao();
    	ArrayList<Facility> facilities = facilityDao.getAll(); 
    	final FacilityAdapter adapter = new FacilityAdapter(this, facilities);
    	OnClickListener onClickListener = new OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				facility = (Facility)adapter.getItem(which);
				Button facilityBtn = (Button) findViewById(R.id.facility);
				facilityBtn.setText(facility.getName());
				Button departmentBtn = (Button)findViewById(R.id.department);
				departmentBtn.setEnabled(true);
			}
		};
    	
    	departmentDialog.setAdapter((ListAdapter)adapter, onClickListener);
    	departmentDialog.show();
    }
    
    public void selectDepartment(View view) {
    	AlertDialog.Builder facilitiesDialog = new AlertDialog.Builder(this);
    	facilitiesDialog.setTitle(R.string.select_department_prompt);

        DepartmentDao departmentDao = DaoFactory.getDepartmentDao();
    	ArrayList<Department> departments = departmentDao.getByFacility(facility); 
    	final DepartmentAdapter adapter = new DepartmentAdapter(this, departments);
    	OnClickListener onClickListener = new OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				department = (Department)adapter.getItem(which);
				Button departmentBtn = (Button) findViewById(R.id.department);
				departmentBtn.setText(department.getName());
				Button specialityBtn = (Button)findViewById(R.id.speciality);
				specialityBtn.setEnabled(true);
			}
		};
    	
    	facilitiesDialog.setAdapter((ListAdapter)adapter, onClickListener);
    	facilitiesDialog.show();
    }
    
    public void selectSpeciality(View view) {
    	AlertDialog.Builder specialitiesDialog = new AlertDialog.Builder(this);
    	specialitiesDialog.setTitle(R.string.select_speciality_prompt);

        SpecialityDao specialityDao = DaoFactory.getSpecialityDao();
    	ArrayList<Speciality> specialities = specialityDao.getByDepartment(department);
    	final SpecialityAdapter adapter = new SpecialityAdapter(this, specialities);
    	OnClickListener onClickListener = new OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				speciality = (Speciality)adapter.getItem(which);
				Button specialityBtn = (Button) findViewById(R.id.speciality);
				specialityBtn.setText(speciality.getName());
			}
		};
    	
    	specialitiesDialog.setAdapter((ListAdapter)adapter, onClickListener);
    	specialitiesDialog.show();
    }
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	db.close();
    }
    
    private void upgradeDataBase(SQLiteDatabase db) {
    	ContextHolder.setContext(this);
        Flyway flyway = new Flyway();
        Log.i("TAGS", db.getPath());
        flyway.setDataSource("jdbc:sqlite:" + db.getPath(), "", "");
        
        flyway.migrate();
    }
}

