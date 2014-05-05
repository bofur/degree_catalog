package org.bofur.dao;

import android.database.sqlite.SQLiteDatabase;

public class DaoFactory {
	
	private static SQLiteDatabase db;
	private static FacilityDao facilityDao;
	
	public static void setDataBase(SQLiteDatabase dataBase) {
		db = dataBase;
	}
	
	public static FacilityDao getFacilityDao() {
		if (facilityDao != null) return facilityDao;
		return facilityDao = new FacilityDao(db);
	}
	
	private  DaoFactory() {}
}
