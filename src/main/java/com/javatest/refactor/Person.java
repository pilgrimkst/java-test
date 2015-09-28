package com.javatest.refactor;
import java.util.Date;

public class Person {
	private String NAME;
	private PhoneNumber phoneNumber;
	private Date date;
	
	public Person(String name, PhoneNumber phoneNumber) {
		this.NAME = name;
	}

	public String getName() {
		return NAME;
	}

	public void setName(String name) {
		name = name;
	}

	public PhoneNumber getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(PhoneNumber phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
