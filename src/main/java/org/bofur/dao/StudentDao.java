package org.bofur.dao;

import java.util.ArrayList;

import org.bofur.bean.Speciality;
import org.bofur.bean.Student;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class StudentDao {
	private static final String TABLE_NAME = "students";
	private static final String TABLE_COLUMN_ID = "id";
	private static final String TABLE_COLUMN_FIRST_NAME = "first_name";
	private static final String TABLE_COLUMN_SECOND_NAME = "second_name";
	private static final String TABLE_COLUMN_LAST_NAME = "last_name";
	private static final String TABLE_COLUMN_SPECIALITY_ID = "speciality_id";
	
	
	private SQLiteDatabase db;
	
	public StudentDao(SQLiteDatabase db) {
		this.db = db;
	}

	public ArrayList<Student> getAll() {
		Cursor cursor = db.query(TABLE_NAME, 
				null, null, null, null, null, null);
		
		ArrayList<Student> result = new ArrayList<Student>();
		while (cursor.moveToNext()) 
			result.add(createStudent(cursor));
		return result;
	}
	
	public Student getById(long id) {
		Cursor cursor = db.rawQuery("SELECT * FROM students WHERE id = " + id, null);
		if (cursor.moveToFirst() == false) return null; 
		
		Student stutend = new Student(
				id, null, 
				getName(cursor, TABLE_COLUMN_FIRST_NAME),
				getName(cursor, TABLE_COLUMN_SECOND_NAME),
				getName(cursor, TABLE_COLUMN_LAST_NAME));
		
		Speciality speciality = 
				DaoFactory.getSpecialityDao().getById(getId(cursor, TABLE_COLUMN_SPECIALITY_ID));
		
		stutend.setSpeciality(speciality);
		return stutend;
	}
	
	public Student save(Student student) {
		ContentValues values = new ContentValues();
		values.put(TABLE_COLUMN_FIRST_NAME, student.getFirstName());
		values.put(TABLE_COLUMN_SECOND_NAME, student.getSecondName());
		values.put(TABLE_COLUMN_LAST_NAME, student.getLastName());
		values.put(TABLE_COLUMN_SPECIALITY_ID, student.getSpeciality().getId());
		
		student.setId(db.insert(TABLE_NAME, null, values));
		
		return student;
	}
	
	public void update (Student student) {
		db.execSQL("UPDATE students " +
				"SET first_name = '" + student.getFirstName() + 
				"', second_name = '" + student.getSecondName() + 
				"', last_name = '" + student.getLastName() + 
				"', speciality_id = " + student.getSpeciality().getId() + 
				" WHERE id = " + student.getId());
	}
	
	public void remove (Student student) {
		db.execSQL("DELETE FROM students WHERE id = " + student.getId());
	}
	
	private Student createStudent(Cursor cursor) {
		long id = getId(cursor, TABLE_COLUMN_ID);
		
		String firstName = getName(cursor, TABLE_COLUMN_FIRST_NAME);
		String secondName = getName(cursor, TABLE_COLUMN_SECOND_NAME);
		String lastName = getName(cursor, TABLE_COLUMN_LAST_NAME);
		
		Speciality speciality = DaoFactory.
				getSpecialityDao().getById(getId(cursor, TABLE_COLUMN_SPECIALITY_ID));
		
		return new Student(id, speciality, firstName, secondName, lastName);
	}
	
	private long getId(Cursor cursor, String columnId) {
		int idIndex = cursor.getColumnIndex(columnId);
		return cursor.getLong(idIndex);
	}
	
	private String getName(Cursor cursor, String columnId) {
		int nameIndex = cursor.getColumnIndex(columnId);
		return cursor.getString(nameIndex);
	}
}
