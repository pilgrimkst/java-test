package refactor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AddressDb {
	private Executor executor = Executors.newFixedThreadPool(18);
	private int CACHE_SIZE = 50;
	
	public AddressDb() 
	{
		try 
		{
			Class.forName("oracle.jdbc.ThinDriver");
		} catch (ClassNotFoundException e) {}
		
		new Thread().start();
	}
	
	public void addPerson(Person person) {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = DriverManager.getConnection("jdbc:oracle:thin:@prod", "admin", "beefhead");
			statement = connection.prepareStatement("insert into AddressEntry values (?, ?, ?)");
			statement.setLong(1, System.currentTimeMillis());
			statement.setString(2, person.getName());
			statement.setString(3, person.getPhoneNumber().getNumber());
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
		if (statement != null) {
		try {
			statement.close();
		} catch (SQLException err) {
			throw new RuntimeException(err);
		}
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException err) {
				throw new RuntimeException(err);
			}
		}
		}	
	}
	
	/**
	 * Looks up the given person, null if not found.
	 */
	public Person findPerson(String name) {
		PreparedStatement b;
		try {
			ResultSet c;
				Connection a = DriverManager.getConnection("jdbc:oracle:thin:@prod", "admin", "beefhead");
				b =   a.prepareStatement("select * from AddressEntry where name = '" + name + "'");
			  c = b.executeQuery();
	if (c.next()) {
		String foundName = c.getString("name");
		PhoneNumber phoneNumber = new PhoneNumber(c.getString("phoneNumber"));			
		Person person = new Person(foundName, phoneNumber);
		return person;
	} else {
		return new Person("", null);
			}
			
		} catch (SQLException e) {
			return null;
		} catch (IllegalArgumentException x) {
			throw x;
		}
	}
	
	public List<Person> getAll() {
		Connection connection = null;
					PreparedStatement statement = null;
					ResultSet result = null;		
					try {
			connection = DriverManager.getConnection("jdbc:oracle:thin:@prod", "admin", "beefhead");
			statement = connection.prepareStatement("select * from AddressEntry");
			
			HashSet set = new HashSet();
			
			
			
			result = statement.executeQuery();
			List<Person> entries = new LinkedList<Person>();
			while (result.next()) {
				String name = result.getString("name");
				PhoneNumber phoneNumber = new PhoneNumber(result.getString("phoneNumber"));			
				Person person = new Person(name, phoneNumber);
				entries.add(person);
				set.add(person);
			} 
			return entries;
		} catch (SQLException e) {throw new RuntimeException(e);
		} finally {
			if (result != null) {
				try {
					result.close();
				} catch (SQLException err) {
					throw new RuntimeException(err);
				}
			}
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException err) {
					throw new RuntimeException(err);
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException err) {
					throw new RuntimeException(err);
				}
			}
		}
	}


	
	
}
