package org.bofur.dao;

import java.util.ArrayList;

import org.bofur.bean.Department;
import org.bofur.bean.Facility;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DepartmentDao {
	private static final String TABLE_NAME = "departments";
	
	private static final String TABLE_COLUMN_ID = "id";
	private static final String TABLE_COLUMN_FACILITY_ID = "facility_id";
	private static final String TABLE_COLUMN_NAME = "name";
	
	private SQLiteDatabase db;
	
	public DepartmentDao(SQLiteDatabase db) {
		this.db = db;
	}

	public ArrayList<Department> getAll() {
		ArrayList<Department> result = new ArrayList<Department>();
		Cursor cursor = db.query(TABLE_NAME, 
				null, null, null, null, null, null);
		
		while (cursor.moveToNext()) {
			Department department = createDepartment(cursor);
			Facility facility = 
					DaoFactory.getFacilityDao().getById(getFacilityId(cursor));
			department.setFacility(facility);
			result.add(department);
		}

		return result;
	}
	
	public ArrayList<Department> getByFacility(Facility facility) {
		ArrayList<Department> result = new ArrayList<Department>();
		Cursor cursor = db.rawQuery(
				"SELECT * FROM departments WHERE facility_id = " + facility.getId(), null);
		
		while (cursor.moveToNext()) {
			Department department = createDepartment(cursor);
			department.setFacility(facility);
			result.add(department);
		}
		
		return result;
	}
	
	public Department getById(long id) {
		Cursor cursor = db.rawQuery("SELECT * FROM departments WHERE id = " + id, null);
		if (cursor.moveToFirst() == false) return null; 
		
		Department department = new Department(id, null, getName(cursor));
		Facility facility = 
				DaoFactory.getFacilityDao().getById(getFacilityId(cursor));
		department.setFacility(facility);
		return department;
	}
	
	public Department save(Department department) {
		ContentValues values = new ContentValues();
		values.put(TABLE_COLUMN_NAME, department.getName());
		values.put(TABLE_COLUMN_FACILITY_ID, department.getFacility().getId());
		department.setId(db.insert(TABLE_NAME, null, values));
		
		return department;
	}
	
	public void update (Department department) {
		db.execSQL("UPDATE departments SET name = '" + department.getName() + 
				"', facility_id = " + department.getFacility().getId() + 
				" WHERE id = " + department.getId());
	}
	
	public void remove (Department department) {
		db.execSQL("DELETE FROM departments WHERE id = " + department.getId());
	}
	
	private Department createDepartment(Cursor cursor) {
		return new Department(getId(cursor), null, getName(cursor));
	}
	
	private long getFacilityId(Cursor cursor) {
		int facilityIdIndex = cursor.getColumnIndex(TABLE_COLUMN_FACILITY_ID);
		return cursor.getLong(facilityIdIndex);
	}
	
	private long getId(Cursor cursor) {
		int idIndex = cursor.getColumnIndex(TABLE_COLUMN_ID);
		return cursor.getLong(idIndex);
	}
	
	private String getName(Cursor cursor) {
		int nameIndex = cursor.getColumnIndex(TABLE_COLUMN_NAME);
		return cursor.getString(nameIndex);
	}
}

