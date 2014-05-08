package org.bofur.dao;

import android.database.sqlite.SQLiteDatabase;

public class DaoFactory {
	
	private static SQLiteDatabase db;
	
	private static FacilityDao facilityDao;
	private static DepartmentDao departmentDao;
	private static SpecialityDao specialityDao;
	private static StudentDao studentDao;
	private static DegreeDao degreeDao;
	
	public static void setDataBase(SQLiteDatabase dataBase) {
		db = dataBase;
	}
	
	public static FacilityDao getFacilityDao() {
		if (facilityDao != null) return facilityDao;
		return facilityDao = new FacilityDao(db);
	}
	
	public static DepartmentDao getDepartmentDao() {
		if (departmentDao != null) return departmentDao;
		return departmentDao = new DepartmentDao(db);
	}
	
	public static SpecialityDao getSpecialityDao() {
		if (specialityDao != null) return specialityDao;
		return specialityDao = new SpecialityDao(db);
	}
	
	public static StudentDao getStudentDao() {
		if (studentDao != null) return studentDao;
		return studentDao = new StudentDao(db);
	}
	
	public static DegreeDao getDegreeDao() {
		if (degreeDao != null) return degreeDao;
		return degreeDao = new DegreeDao(db);
	}
	
	private  DaoFactory() {}
}
