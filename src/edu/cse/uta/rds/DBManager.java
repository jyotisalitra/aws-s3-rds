/**
 * Jyoti Salitra
 * UTA ID: ************
 * Cloud Computing (CSE - 6331) - David Levine
 * Programming Assignment # 3
 * Date: 10/12/2014
 */

package edu.cse.uta.rds;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A JDBC Helper class to connect to MySQL instance on AWS RDS and provide some utility methods.
 */
public class DBManager {

	//AWS RDS Constants
	public final static String USERNAME="root";
	public final static String PASSWORD="rootpassword";
	public final static String DB_STRING = "jdbc:mysql://************/cse6331_db";
	
	/**
	 * Connects to AWS RDS MySQL Instance
	 * @return Connection
	 */
	public static Connection getConnection(){
		Connection conn = null;
		
		try {
			 //loading JDBC driver for MySQL
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.err.println("Could not load MySQL JDBC driver. " +  e.getMessage());
			e.printStackTrace();
		}
		
		try {
			//connecting to the MySQL DB
			conn = DriverManager.getConnection(DB_STRING, USERNAME, PASSWORD); 
			System.out.println("Connected to the database.");
		} catch (SQLException e) {
			System.err.println("Error in connecting to the database. " +  e.getMessage());
			e.printStackTrace();
		}
		
		return conn;
	}
	
	/**
	 * Closes JDBC resources
	 */
	public static void cleanup (PreparedStatement ps, ResultSet rs, Connection con){
		try {
			if(ps != null){
				ps.close();
			}
			
			if(rs != null){
				rs.close();
			}
			
			if(con != null){
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		DBManager.getConnection();
	}
}

