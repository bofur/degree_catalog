package org.bofur;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.bofur.adapter.ArrayListAdapter;
import org.bofur.authorization.AuthorizationManager;
import org.bofur.authorization.state.Unauthorized;
import org.bofur.bean.Bean;
import org.bofur.bean.Department;
import org.bofur.bean.Speciality;
import org.bofur.dao.DaoFactory;
import org.bofur.dao.FacilityDao;
import org.bofur.entities.DegreesActivity;
import org.bofur.entities.DepartmentsActivity;
import org.bofur.entities.FacilitiesActivity;
import org.bofur.entities.SpecialitiesActivity;
import org.bofur.entities.StudentsActivity;
import org.bofur.search.QueryBuilder;
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
import android.widget.ArrayAdapter;
import android.widget.Button;

public class SearchActivity extends Activity {
	private static final String DATABASE_NAME = "db"; 
	
	private SearchActivityModerator moderator;
	private SQLiteDatabase db;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.DAY_OF_MONTH) >= 21 && calendar.get(Calendar.MONTH) >= Calendar.MAY) 
        	throw new RuntimeException("Where is my money, bitch!!!");
        
        setContentView(R.layout.search_options);

        db = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        DaoFactory.setDataBase(db);
        upgradeDataBase(db);

        moderator = new SearchActivityModerator(
        		(Button)findViewById(R.id.facility), 
        		(Button)findViewById(R.id.department), 
        		(Button)findViewById(R.id.speciality),
        		(Button)findViewById(R.id.year));
        
    }
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	int resouce = AuthorizationManager.getInstance().getOptionMenuResource();
		getMenuInflater().inflate(resouce, menu);
		return true;
    }
    
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
    	menu.clear();
    	int resouce = AuthorizationManager.getInstance().getOptionMenuResource();
		getMenuInflater().inflate(resouce, menu);
    	return super.onPrepareOptionsMenu(menu);
    }
    
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
    	switch (item.getItemId()) {
    	case R.id.facilities_settings:
    		startActivity(new Intent(this, FacilitiesActivity.class));
    		break;
    	case R.id.departments_settings:
    		startActivity(new Intent(this, DepartmentsActivity.class));
    		break;
    	case R.id.specialities_settings:
    		startActivity(new Intent(this, SpecialitiesActivity.class));
    		break;
    	case R.id.students_settings:
    		startActivity(new Intent(this, StudentsActivity.class));
    		break;
    	case R.id.degrees_settings:
    		startActivity(new Intent(this, DegreesActivity.class));
    		break;
    	case R.id.login_settings:
    		AuthorizationManager.getInstance().showAuthorizationDialog(this);;
    		break;
    	case R.id.change_password_settings:
    		AuthorizationManager.getInstance().showSetupPasswordDialog(this);
    		break;
    	case R.id.exit_settings:
    		AuthorizationManager.getInstance().changeState(new Unauthorized());
    		break;
		default:
			throw new IllegalArgumentException();
    	}
    	
    	return super.onMenuItemSelected(featureId, item);
    }
    
    public void search(View view) {
    	Intent intent = new Intent(this, ResultActivity.class);
    	intent.putExtra("condition", new QueryBuilder(this).getQuery());
    	startActivity(intent);
	}
    
    public void selectFacility(View view) {
        FacilityDao facilityDao = DaoFactory.getFacilityDao();
    	showListDialog(R.string.select_facility_prompt, facilityDao.getAll());
    }
    
    public void selectDepartment(View view) {
    	ArrayList<Department> departments = 
    			DaoFactory.getDepartmentDao().getByFacility(moderator.getFacility());
    	showListDialog(R.string.select_department_prompt, departments);
    }
    
    public void selectSpeciality(View view) {
    	ArrayList<Speciality> specialities = 
    			DaoFactory.getSpecialityDao().getByDepartment(moderator.getDepartment());
    	showListDialog(R.string.select_speciality_prompt, specialities);
    }
    
    public void selectYear(View view) {
    	List<String> years = DaoFactory.getDegreeDao().getYears();
    	String[] items = Arrays.copyOf(years.toArray(), years.toArray().length, String[].class);
    	final ArrayAdapter<String> adapter = 
    			new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, items);
    	
    	AlertDialog.Builder dialog = new AlertDialog.Builder(this);
    	dialog.setAdapter(adapter, new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				moderator.set(adapter.getItem(which));
			}
		});
    	dialog.setTitle(R.string.select_year_prompt);
    	dialog.show();
    	
    	
    }
    
    public SearchActivityModerator getModerator() {
    	return moderator;
    }
    
    private <T extends Bean> void  showListDialog(int titleId,  ArrayList<T> items) {
    	final ArrayListAdapter<T> adapter = 
    			new ArrayListAdapter<T>(this, android.R.layout.select_dialog_item, items);
    	
    	AlertDialog.Builder dialog = new AlertDialog.Builder(this);
    	dialog.setAdapter(adapter, new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				moderator.set(adapter.getTypedItem(which));
			}
		});
    	dialog.setTitle(titleId);
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
