package org.bofur.dao;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.bofur.bean.Facility;;

public class FacilityDao {
	private SQLiteDatabase db;
	
	public FacilityDao(SQLiteDatabase db) {
		this.db = db;
	}

	public ArrayList<Facility> all() {
		Cursor cursor = db.query("facilities", 
				null, null, null, null, null, null);
		
		ArrayList<Facility> result = new ArrayList<Facility>();
		if (cursor.moveToFirst() == false) return result;
		
		while (cursor.moveToNext()) {
			int idIndex = cursor.getColumnIndex("id");
			int nameIndex = cursor.getColumnIndex("name");
			
			long id = cursor.getLong(idIndex);
			String name = cursor.getString(nameIndex);
			
			result.add(new Facility(id, name));
		}
		
		return result;
	}
}
