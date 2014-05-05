package org.bofur.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DaoFactory {
	private static final String DB_NAME = "db";
	
	private static SQLiteDatabase db;
	
	private static void setDataBase(SQLiteDatabase dataBase) {
		db = dataBase;
	}
	
	private  DaoFactory() {}
}
