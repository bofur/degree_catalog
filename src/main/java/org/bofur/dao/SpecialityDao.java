package org.bofur.dao;

import java.util.ArrayList;

import org.bofur.bean.Department;
import org.bofur.bean.Speciality;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
	
	public Speciality insert(String name, Department department) {
		ContentValues values = new ContentValues();
		values.put(TABLE_COLUMN_NAME, name);
		values.put(TABLE_COLUMN_DEPARTMENT_ID, department.getId());
		long id = db.insert(TABLE_NAME, null, values);
		
		return new Speciality(id, department, name);
	}
	
	public void update(Speciality speciality) {
		db.execSQL("UPDATE specialities SET name = '" + speciality.getName() + 
				"', department_id = " + speciality.getDepartment().getId() + 
				" WHERE id = " + speciality.getId());
	}
	
	public void remove(Speciality speciality) {
		db.execSQL("DELETE FROM specialities WHERE id = " + speciality.getId());
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
