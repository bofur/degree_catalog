package org.bofur.dao;

import java.util.ArrayList;

import org.bofur.bean.Department;
import org.bofur.bean.Speciality;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SpecialityDao {
	private static final String TABLE_NAME = "specialities";
	
	private static final String TABLE_COLUMN_ID = "id";
	private static final String TABLE_COLUMN_DEPARTMENT_ID = "department_id";
	private static final String TABLE_COLUMN_NAME = "name";
	
	private SQLiteDatabase db;
	
	public SpecialityDao(SQLiteDatabase db) {
		this.db = db;
	}
	
	public ArrayList<Speciality> getAll() {
		ArrayList<Speciality> result = new ArrayList<Speciality>();
		Cursor cursor = db.query(TABLE_NAME, 
				null, null, null, null, null, null);
		
		while (cursor.moveToNext()) {
			Speciality speciality = createSpeciality(cursor);
			Department department = 
					DaoFactory.getDepartmentDao().getById(getDepartmentId(cursor));
			speciality.setDepartment(department);
			result.add(speciality);
		}

		return result;
	}
	
	public ArrayList<Speciality> getByDepartment(Department department) {
		String query = 
				"  SELECT * "
				+ "FROM specialities "
				+ "WHERE department_id = " + department.getId();
		
		Cursor cursor = db.rawQuery(query, null); 
		ArrayList<Speciality> result = new ArrayList<Speciality>();
		while (cursor.moveToNext()) {
			Speciality speciality = createSpeciality(cursor);
			speciality.setDepartment(department);
			result.add(speciality);
		}
		
		return result;
	}
	
	private Speciality createSpeciality(Cursor cursor) {
		return new Speciality(getId(cursor), null, getName(cursor));
	}
	
	private long getDepartmentId(Cursor cursor) {
		int facilityIdIndex = cursor.getColumnIndex(TABLE_COLUMN_DEPARTMENT_ID);
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
