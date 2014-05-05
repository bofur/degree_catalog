package org.bofur.dao;

import java.util.ArrayList;

import org.bofur.bean.Department;
import org.bofur.bean.Facility;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DepartmentDao {
	private static final String TABLE_NAME = "department";
	private static final String TABLE_COLUMN_ID = "id";
	private static final String TABLE_COLUMN_FACILITY_ID = "facility_id";
	private static final String TABLE_COLUMN_NAME = "name";
	
	private SQLiteDatabase db;
	
	public DepartmentDao(SQLiteDatabase db) {
		this.db = db;
	}

	public ArrayList<Department> all() {
		ArrayList<Department> result = new ArrayList<Department>();
		Cursor cursor = db.query(TABLE_NAME, 
				null, null, null, null, null, null);
		
		while (cursor.moveToNext()) 
			result.add(createDepartment(cursor));

		cursor.close();
		return result;
	}
	
	private Department createDepartment(Cursor cursor) {
		int idIndex = cursor.getColumnIndex(TABLE_COLUMN_ID);
		int facilityIdIndex = cursor.getColumnIndex(TABLE_COLUMN_FACILITY_ID);
		int nameIndex = cursor.getColumnIndex(TABLE_COLUMN_NAME);
		
		long id = cursor.getLong(idIndex);
		String name = cursor.getString(nameIndex);
		Facility facility = DaoFactory.getFacilityDao().getById(cursor.getLong(facilityIdIndex));
		
		return new Department(id, facility, name);
	}
}

