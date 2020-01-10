package persistence;

import java.sql.Connection;
import java.sql.DriverManager;

public class MysqlBaseDao {
	protected final Connection getConnection() {
		Connection connection = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");  
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kleinesupermarkt","root","admin");  
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
		return connection;
	}
}
