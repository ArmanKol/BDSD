package persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import kafka.Transaction;

public class TransactieDaoImpl extends MysqlBaseDao {
	public List<Transaction> findAll(){
		List<Transaction> list = new ArrayList<Transaction>();
		
		try(Connection c = super.getConnection()){
			Statement stmt = c.createStatement();
			ResultSet result = stmt.executeQuery("SELECT klant_idklant, product_idproduct, datum, filiaal_idfiliaal FROM aankoop;");
			
			while(result.next()) {
				
				Transaction transactie = new Transaction(result.getInt("klant_idklant"), result.getInt("product_idproduct"), result.getString("datum"), result.getInt("filiaal_idfiliaal"));
				list.add(transactie);
			}
			
		}catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return list;
	}
}
