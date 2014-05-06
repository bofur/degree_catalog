package org.bofur;


import java.util.ArrayList;

import org.bofur.adapter.ArrayListAdapter;
import org.bofur.bean.Department;
import org.bofur.bean.Facility;
import org.bofur.bean.Indexed;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;

public class SearchActivity extends Activity {
	private static final String DATABASE_NAME = "db"; 
	
	private SQLiteDatabase db;

	private SearchActivityModerator moderator;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_options);

        db = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        upgradeDataBase(db);
        
        DaoFactory.setDataBase(db);

        moderator = new SearchActivityModerator(
        		(Button)findViewById(R.id.facility), 
        		(Button)findViewById(R.id.department), 
        		(Button)findViewById(R.id.speciality));
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
        FacilityDao facilityDao = DaoFactory.getFacilityDao();
    	ArrayList<Facility> facilities = facilityDao.getAll(); 
    	showListDialog(R.string.select_facility_prompt, facilities);
    }
    
    public void selectDepartment(View view) {
        DepartmentDao departmentDao = DaoFactory.getDepartmentDao();
    	ArrayList<Department> departments = departmentDao.getByFacility(moderator.getFacility());
    	showListDialog(R.string.select_department_prompt, departments);
    }
    
    public void selectSpeciality(View view) {
        SpecialityDao specialityDao = DaoFactory.getSpecialityDao();
    	ArrayList<Speciality> specialities = specialityDao.getByDepartment(moderator.getDepartment());
    	showListDialog(R.string.select_speciality_prompt, specialities);
    }
    
    private <T extends Indexed> void  showListDialog(int titleId,  ArrayList<T> items) {
    	AlertDialog.Builder dialog = new AlertDialog.Builder(this);
    	dialog.setTitle(titleId);
    	
    	final ArrayListAdapter<T> adapter = new ArrayListAdapter<T>(this, items);
    	dialog.setAdapter(
    			adapter, new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				moderator.set(adapter.getTypedItem(which));
			}
		});
    	
    	dialog.show();
    }
    
    private void upgradeDataBase(SQLiteDatabase db) {
    	ContextHolder.setContext(this);
        Flyway flyway = new Flyway();
        flyway.setDataSource("jdbc:sqlite:" + db.getPath(), "", "");
        
        flyway.migrate();
    }
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	db.close();
    }
}

