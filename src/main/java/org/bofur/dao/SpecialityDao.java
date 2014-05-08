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
	
	public Speciality getById(long id) {
		Cursor cursor = db.rawQuery("SELECT * FROM specialities WHERE id = " + id, null);
		if (cursor.moveToFirst() == false) return null; 
		
		Speciality speciality = new Speciality(id, null, getName(cursor));
		Department department = 
				DaoFactory.getDepartmentDao().getById(getDepartmentId(cursor));
		speciality.setDepartment(department);
		return speciality;
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
	
	public Speciality save(Speciality speciality) {
		ContentValues values = new ContentValues();
		values.put(TABLE_COLUMN_NAME, speciality.getName());
		values.put(TABLE_COLUMN_DEPARTMENT_ID, speciality.getDepartment().getId());
		speciality.setId(db.insert(TABLE_NAME, null, values));
		
		return speciality;
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
