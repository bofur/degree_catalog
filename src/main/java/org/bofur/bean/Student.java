package org.bofur.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class Student implements Parcelable, Indexed {
	private long id;
	
	private Speciality speciality;
	
	private String firstName;
	private String secondName;
	private String lastName;

	public Student(long id, Speciality speciality, 
			String firstName, String secondName, String lastName) {
		this.setId(id);
		this.setSpeciality(speciality);
		this.setFirstName(firstName);
		this.setSecondName(secondName);
		this.setLastName(lastName);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Speciality getSpeciality() {
		return speciality;
	}

	public void setSpeciality(Speciality speciality) {
		this.speciality = speciality;
	}

	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(id);
		dest.writeParcelable(speciality, flags);
		dest.writeString(firstName);
		dest.writeString(secondName);
		dest.writeString(lastName);
	}
	
	public static final Parcelable.Creator<Student> CREATOR
			= new Parcelable.Creator<Student>() {
		public Student createFromParcel(Parcel in) {
			return new Student(in);
		}
		
		public Student[] newArray(int size) {
		    return new Student[size];
		}
	};

	private Student(Parcel in) {
		id = in.readLong();
		speciality = in.readParcelable(Speciality.class.getClassLoader());
		firstName = in.readString();
		secondName = in.readString();
		lastName = in.readString();
	}

	public String getName() {
		return lastName + " " + firstName + " " + secondName;
	}
}
