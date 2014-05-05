package org.bofur.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class Speciality implements Parcelable, Indexed{
	private long id;
	private Department department;
	private String name;
	
	public Speciality(long id, Department department, String name) {
		this.setId(id);
		this.setDepartment(department);
		this.setName(name);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(id);
		dest.writeParcelable(department, flags);
		dest.writeString(name);
	}
	
	public static final Parcelable.Creator<Speciality> CREATOR
			= new Parcelable.Creator<Speciality>() {
		public Speciality createFromParcel(Parcel in) {
			return new Speciality(in);
		}
		
		public Speciality[] newArray(int size) {
		    return new Speciality[size];
		}
	};

	private Speciality(Parcel in) {
		id = in.readLong();
		department = in.readParcelable(Department.class.getClassLoader());
		name = in.readString();
	}
	
}
