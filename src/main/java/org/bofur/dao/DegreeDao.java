package org.bofur.dao;

import java.util.ArrayList;
import java.util.List;

import org.bofur.bean.Degree;
import org.bofur.bean.Student;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DegreeDao {
	private static final String TABLE_NAME = "degrees";
	private static final String TABLE_COLUMN_ID = "id";
	private static final String TABLE_COLUMN_NAME = "name";
	private static final String TABLE_COLUMN_STUDENT_ID = "student_id";
	private static final String TABLE_COLUMN_YEAR = "year";
	
	private SQLiteDatabase db;
	
	public DegreeDao(SQLiteDatabase db) {
		this.db = db;
	}

	public ArrayList<Degree> getAll() {
		Cursor cursor = db.query(TABLE_NAME, 
				null, null, null, null, null, null);
		
		ArrayList<Degree> result = new ArrayList<Degree>();
		while (cursor.moveToNext()) 
			result.add(createDegree(cursor));
		return result;
	}
	
	public List<String> getYears() {
		return new ArrayList<String>();
	}
	
	public ArrayList<Degree> getByConditions(String condition) {
		ArrayList<Degree> result = new ArrayList<Degree>();
		if (condition == null || condition.isEmpty()) return getAll();
		
		Cursor cursor = db.rawQuery(condition, null);
		while (cursor.moveToNext()) 
			result.add(createDegree(cursor));
		return result;
	}
	
	public Degree save(Degree degree) {
		ContentValues values = new ContentValues();
		values.put(TABLE_COLUMN_NAME, degree.getName());
		values.put(TABLE_COLUMN_STUDENT_ID, degree.getStudent().getId());
		values.put(TABLE_COLUMN_YEAR, degree.getYear());
		
		degree.setId(db.insert(TABLE_NAME, null, values));
		
		return degree;
	}
	
	public void update (Degree degree) {
		db.execSQL("UPDATE degrees " +
				"SET name = '" + degree.getName() + 
				"', year = " + degree.getYear() + 
				", student_id = " + degree.getStudent().getId() + 
				" WHERE id = " + degree.getId());
	}
	
	public void remove (Degree degree) {
		db.execSQL("DELETE FROM degrees WHERE id = " + degree.getId());
	}
	
	private Degree createDegree(Cursor cursor) {
		long id = getInt(cursor, TABLE_COLUMN_ID);
		int year = (int)getInt(cursor, TABLE_COLUMN_YEAR);
		String name = getName(cursor, TABLE_COLUMN_NAME);
		
		Student student = DaoFactory.
				getStudentDao().getById(getInt(cursor, TABLE_COLUMN_STUDENT_ID));
		
		return new Degree(id, name, student, year);
	}
	
	private long getInt(Cursor cursor, String columnId) {
		int idIndex = cursor.getColumnIndex(columnId);
		return cursor.getLong(idIndex);
	}
	
	private String getName(Cursor cursor, String columnId) {
		int nameIndex = cursor.getColumnIndex(columnId);
		return cursor.getString(nameIndex);
	}
}
