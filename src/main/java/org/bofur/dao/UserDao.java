package org.bofur.dao;

import org.bofur.bean.User;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserDao {
	private static final String TABLE_NAME = "users";
	private static final String TABLE_COLUMN_ID = "id";
	private static final String TABLE_COLUMN_LOGIN = "login";
	private static final String TABLE_COLUMN_PASSWORD = "password";
	
	private SQLiteDatabase db;

	public UserDao(SQLiteDatabase db) {
		this.db = db;
	}
	
	public User getUser() {
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
		if (cursor.moveToFirst() == false ) return null;
		
		long id = getInd(cursor, TABLE_COLUMN_ID);
		String login = getString(cursor, TABLE_COLUMN_LOGIN);
		String password = getString(cursor, TABLE_COLUMN_PASSWORD);
		
		return new User(id, login, password);
	}
	
	public void save(User user) {
		ContentValues values = new ContentValues();
		values.put(TABLE_COLUMN_LOGIN, user.getName());
		values.put(TABLE_COLUMN_PASSWORD, user.getPassword());
		long id = db.insert(TABLE_NAME, null, values);
		user.setId(id);
	}
	
	public void update(User user) {
		db.execSQL("UPDATE " + TABLE_NAME + " SET password = '" + 
					user.getPassword() + "' WHERE id = " + user.getId());
	}
	
	public boolean tryLogin(User user) {
		Cursor cursor =  db.rawQuery("SELECT * FROM " + TABLE_NAME + 
				" WHERE login = '" + user.getName() + 
				"' AND password = '" + user.getPassword() + "'", null);
		
		return cursor.moveToFirst();
	}
	
	private long getInd(Cursor cursor, String column) {
		int index = cursor.getColumnIndex(column);
		return cursor.getInt(index);
	}
	
	private String getString(Cursor cursor, String column) {
		int index = cursor.getColumnIndex(column);
		return cursor.getString(index);
	}
}
