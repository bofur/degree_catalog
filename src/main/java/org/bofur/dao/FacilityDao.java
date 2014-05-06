package org.bofur.dao;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.bofur.bean.Facility;;

public class FacilityDao {
	private static final String TABLE_NAME = "facilities";
	private static final String TABLE_COLUMN_ID = "id";
	private static final String TABLE_COLUMN_NAME = "name";
	
	private SQLiteDatabase db;
	
	public FacilityDao(SQLiteDatabase db) {
		this.db = db;
	}

	public ArrayList<Facility> getAll() {
		Cursor cursor = db.query(TABLE_NAME, 
				null, null, null, null, null, null);
		
		ArrayList<Facility> result = new ArrayList<Facility>();
		
		while (cursor.moveToNext()) {
			result.add(createFacility(cursor));
		}
		
		return result;
	}
	
	public Facility getById(long id) {
		Cursor cursor = db.rawQuery("SELECT * FROM facilities WHERE id = " + id, null);
		Facility facility = 
				cursor.moveToFirst() ? createFacility(cursor) : null;
		
		return facility;
	} 
	
	private Facility createFacility(Cursor cursor) {
		int idIndex = cursor.getColumnIndex(TABLE_COLUMN_ID);
		int nameIndex = cursor.getColumnIndex(TABLE_COLUMN_NAME);
		
		long id = cursor.getLong(idIndex);
		String name = cursor.getString(nameIndex);
		
		return new Facility(id, name);
	}
}
