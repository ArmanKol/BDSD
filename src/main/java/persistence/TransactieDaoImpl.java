package persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import nl.hu.Transaction;

public class TransactieDaoImpl extends MysqlBaseDao {
	public List<Transaction> findAll(){
		List<Transaction> list = new ArrayList<Transaction>();
		
		try(Connection c = super.getConnection()){
			Statement stmt = c.createStatement();
			ResultSet result = stmt.executeQuery("");
			
			while(result.next()) {
				Transaction transactie = new Transaction(result.getInt(), result.getInt(), result.getString());
			}
			
		}catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return list;
	}
}
