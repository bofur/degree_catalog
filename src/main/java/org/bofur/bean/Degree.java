package org.bofur.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class Degree implements Parcelable, Indexed {
	private long id;
	private String name;
	private Student student;
	private int year;
	
	public Degree(long id, String name, Student student, int year) {
		this.setId(id);
		this.setName(name);
		this.setStudent(student);
		this.setYear(year);
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

	public void setName(String title) {
		this.name = title;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(id);
		dest.writeString(name);
		dest.writeParcelable(student, flags);
		dest.writeInt(year);
		
	}
	
	public static final Parcelable.Creator<Degree> CREATOR
			= new Parcelable.Creator<Degree>() {
		public Degree createFromParcel(Parcel in) {
			return new Degree(in);
		}

		public Degree[] newArray(int size) {
		    return new Degree[size];
		}
	};
	
    private Degree(Parcel in) {
    	id = in.readLong();
    	name = in.readString();
    	student = in.readParcelable(Student.class.getClassLoader());
    	year = in.readInt();
    }

	public int describeContents() {
		return 0;
	}
}
