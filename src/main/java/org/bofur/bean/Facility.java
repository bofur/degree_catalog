package org.bofur.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class Facility implements Parcelable, Indexed{
	private long id;
	private String name;
	
	public Facility(long id, String name) {
		this.id = id;
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

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(id);
		dest.writeString(name);
	}

	public static final Parcelable.Creator<Facility> CREATOR
			= new Parcelable.Creator<Facility>() {
		public Facility createFromParcel(Parcel in) {
			return new Facility(in);
		}
		
		public Facility[] newArray(int size) {
		    return new Facility[size];
		}
	};

	private Facility(Parcel in) {
		id = in.readLong();
		name = in.readString();
	}

}
