/**
 * Jyoti Salitra
 * UTA ID: ************
 * Cloud Computing (CSE - 6331) - David Levine
 * Programming Assignment # 3
 * Date: 10/12/2014
 */

package edu.cse.uta.rds;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Queries AWS MySQL instance to get the list of states, per-capita-income, and total # universities  in the state.
 */
public class SIERMapper {

	public static void main(String[] args) {
		//start the timer
		long start = System.currentTimeMillis();
		
		//get Connection from the DBManager
		Connection conn = DBManager.getConnection();
		
		//SQL query to select state, total universties, and per-capita-income for each state
		String query = "SELECT hd.STABBR, COUNT(hd.STABBR), pci.avgPCI "
						+ "FROM cse6331_db.hd2013 hd, "
						+ "(SELECT AreaName, (1990Dollars + 1991Dollars + 1992Dollars + 1993Dollars + 1994Dollars + 1995Dollars + 1996Dollars + 1997Dollars + 1998Dollars + 1999Dollars + 2000Dollars + 2001Dollars + 2002Dollars + 2003Dollars + 2004Dollars + 2005Dollars + 2006Dollars + 2007Dollars + 2008Dollars + 2009Dollars + 2010Dollars + 2011Dollars + 2012Dollars)/23 as avgPCI FROM cse6331_db.uspci) pci "
						+ "WHERE hd.STABBR = pci.AreaName "
						+"GROUP BY hd.STABBR "; 
		String orderByUniv = "  ORDER BY COUNT(hd.STABBR) desc";
		String orderByPCI = " ORDER BY pci.avgPCI desc";
		
		//create instances
		Statement stmt_pci, stmt_univ;
		ResultSet rs_pci, rs_univ;
		
		try {
			//create and execute query with descending order of PCI
			stmt_pci = conn.createStatement();
			rs_pci = stmt_pci.executeQuery(query + orderByPCI);
			
			//create and execute query  with descending order of University Count
			stmt_univ = conn.createStatement();
			rs_univ = stmt_univ.executeQuery(query + orderByUniv);
			
			
			System.out.println("IN DESCENDING ORDER OF AVERAGE PCI OVER 23 YEARS");
			System.out.println("RANK\tSTATE\t#UNIV\tAVG PCI(In USD)");
			int count = 1;
			
			//print result of first query
			while(rs_pci.next())
			{
				System.out.println(count++ + "\t" + rs_pci.getString(1) + "\t" + rs_pci.getInt(2) + "\t" + rs_pci.getInt(3));
			}
			
			count = 1;
			System.out.println("\n\nIN DESCENDING ORDER OF UNIV COUNT");
			System.out.println("RANK\tSTATE\t#UNIV\tAVG PCI(In USD)");
			//print result of second query
			while(rs_univ.next())
			{
				System.out.println(count++ + "\t" + rs_univ.getString(1) + "\t" + rs_univ.getInt(2) + "\t" + rs_univ.getInt(3));
			}
			
			//calculate the total time spent
			long totalTime = System.currentTimeMillis() - start;
			System.out.println("Total Time Taken: " + totalTime + " ms");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
