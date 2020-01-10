package persistence;

import java.sql.Connection;
import java.sql.DriverManager;

public class MysqlBaseDao {
	protected final Connection getConnection() {
		Connection connection = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");  
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sonoo","root","root");  
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
		return connection;
	}
}
