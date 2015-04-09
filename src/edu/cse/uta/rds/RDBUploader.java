/**
 * Jyoti Salitra
 * UTA ID: ************
 * Cloud Computing (CSE - 6331) - David Levine
 * Programming Assignment # 3
 * Date: 10/12/2014
 */

package edu.cse.uta.rds;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Loads data into AWS RDS MySQL instance from the csv files
 */
public class RDBUploader {

	/**
	 * Loads data from csvFileName into the table tableName.
	 * Ignores first ignoreLines
	 * @param csvFileName
	 * @param tableName
	 * @param ignoreLines
	 * @param conn
	 */
	public static void importData(String csvFileName, String tableName, int ignoreLines, Connection conn) {
		//start the timer
		long start = System.currentTimeMillis();

		Statement stmt = null;
		try {
			//create a Statement instance
			stmt = conn.createStatement();

			//LOAD DATA query for MySQL
			String query = "LOAD DATA LOCAL INFILE '" + csvFileName + "' INTO TABLE "
					+ tableName + " FIELDS TERMINATED BY ',' ENCLOSED BY '\"' "
					+ "LINES TERMINATED BY '\r\n' " + "IGNORE "+ignoreLines+" LINES";
			
			//execute the query
			stmt.executeUpdate(query);

		} catch (Exception e) {
			e.printStackTrace();
			//close JDBC resources
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		//calculate the time taken for this LOAD query
		long totalTime = System.currentTimeMillis() - start;
		System.out.println("Total Time Taken for "+ tableName+": " + totalTime + " ms");
	}

	public static void main(String[] args) {
		
		//get a Connection instance
		Connection conn = DBManager.getConnection();
		
		System.out.println("Starting loading csv files into MySQL");
		
		//Load data from us-pci.csv file
		//ignores first two lines; column heading and US data
		importData("./data/us-pci.csv", "uspci", 2, conn);
		
		//Load data from hd2013.csv file
		//ignores first line: column-heading
		importData("./data/hd2013.csv", "hd2013", 1, conn);
		System.out.println("MySQL load done.");
	}
}
