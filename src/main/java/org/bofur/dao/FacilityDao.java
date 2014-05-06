package org.bofur.dao;

import java.util.ArrayList;

import org.bofur.bean.Facility;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
		while (cursor.moveToNext()) 
			result.add(createFacility(cursor));
		return result;
	}
	
	public Facility getById(long id) {
		Cursor cursor = db.rawQuery("SELECT * FROM facilities WHERE id = " + id, null);
		if (cursor.moveToFirst() == false) return null;
		return createFacility(cursor);
	}
	
	public void update(Facility facility) {
		db.execSQL("UPDATE facilities SET name = '" + 
				facility.getName() + "' WHERE id = " + facility.getId());
	}
	
	public Facility insert(String name) {
		ContentValues values = new ContentValues();
		values.put(TABLE_COLUMN_NAME, name);
		long id = db.insert(TABLE_NAME, null, values);
		return new Facility(id, name); 
	}
	
	public void remove(Facility facility) {
		db.execSQL("DELETE FROM facilities WHERE id = " + facility.getId());
	}
	
	private Facility createFacility(Cursor cursor) {
		int idIndex = cursor.getColumnIndex(TABLE_COLUMN_ID);
		int nameIndex = cursor.getColumnIndex(TABLE_COLUMN_NAME);
		
		long id = cursor.getLong(idIndex);
		String name = cursor.getString(nameIndex);
		
		return new Facility(id, name);
	}
}
