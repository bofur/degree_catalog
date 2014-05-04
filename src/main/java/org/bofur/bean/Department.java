package org.bofur.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class Department implements Parcelable{
	private long id;
	private Facility facility;
	private String name;
	
	public Department(long id, Facility facility, String name) {
		this.id = id;
		this.setFacility(facility);
		this.name = name;
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

	public Facility getFacility() {
		return facility;
	}

	public void setFacility(Facility facility) {
		this.facility = facility;
	}

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(id);
		dest.writeParcelable(facility, flags);
		dest.writeString(name);
	}
	
	public static final Parcelable.Creator<Department> CREATOR
			= new Parcelable.Creator<Department>() {
		public Department createFromParcel(Parcel in) {
			return new Department(in);
		}
		
		public Department[] newArray(int size) {
		    return new Department[size];
		}
	};

	private Department(Parcel in) {
		id = in.readLong();
		facility = in.readParcelable(Facility.class.getClassLoader());
		name = in.readString();
	}
}
