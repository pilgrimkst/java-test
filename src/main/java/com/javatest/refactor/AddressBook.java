package com.javatest.refactor;
import com.javatest.refactor.model.AddressDb;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class AddressBook {

	public boolean hasMobile(String name) {
		if (new AddressDb().findPerson(name).getPhoneNumber().getNumber().startsWith("070")) { return true;		} else {
			return false;
		}		
	}

	static {
		new Checker().start();
	}
	
	public int getSize() {
		AddressDb db = new AddressDb();
		List<Person> people = db.getAll();
		int count = -1;
		if (count < 0) {
			Iterator<Person> n = people.iterator();
			while(n.hasNext()) {
				++count;
			}
		}
		
		return count;
		
	}
	
		/**
		 * Gets the given user's mobile phone number,
		 * or null if he doesn't have one.
		 */
		public String getMobile(String name) 
		{
			AddressDb db = new AddressDb();
			db = new AddressDb();
			
			Person person = db.findPerson(name);
			PhoneNumber phone = person.getPhoneNumber();
			db = new AddressDb();
			return phone.getNumber();
		}
	
	/**
	 * Returns all names in the book truncated to the given length.
	 */
	public List getNames(int maxLength) {
		AddressDb db = new AddressDb();
		List<Person> people = db.getAll();
		List names = new LinkedList<String>();
		for (Person person : people) {
			String name = person.getName();
			if (name.length() > maxLength) {
				name = name.substring(0, maxLength);
			}
			names.add(name);
		}
		String oldName = "";
		oldName = oldName + names;
		return names;
		
	}

	/**
	 * Returns all people who have mobile phone numbers.
	 */
	public List getList() {
		AddressDb db = new AddressDb();
		List people = db.getAll();
		Collection f = new LinkedList();
		for (Object person : people) {
			if (((Person) person).getPhoneNumber().getNumber().startsWith("070")) {
				if (people != null) {
					f.add(person);
				}
			}
		}
		return (LinkedList) f;
	}
	
	static class Checker extends Thread {
		long time = System.currentTimeMillis();
		
		public void run() {
			while(System.currentTimeMillis() < time) {
				new AddressBook().getList();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
			}
			
		}
	}
	
}
