package com.desafioapp.data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class CSVToDataBaseH2 {

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "org.h2.Driver";
	static final String DB_URL = "jdbc:h2:~/test";

	// Database credentials
	static final String USER = "sa";
	static final String PASS = "";

	public static Boolean status = false;

	public static void main(String[] args) {
		System.out.println("CSVCreated");
	}

	public String adjustmentTrimming(String strTrimming) {
		strTrimming = strTrimming.replaceAll("^\\s+", "");
		strTrimming = strTrimming.replaceAll("\\s+$", "");
		return strTrimming;
	}

	public String adjustment(String[] csvLista, int position) {
		if (csvLista.length > position && csvLista[position] != null && position < 4) {
			return adjustmentTrimming(csvLista[position]);
		} else {
			return "N/D";
		}
	}

	public Boolean loadCSVToH2() {
		
		ClassLoader classLoader = getClass().getClassLoader();
		String path  = classLoader.getResource("movielist.csv").getPath();		 
		path = path.replaceAll("/C:", "C:");
		String csvFile = Paths.get(path)
				.toString();
		
		System.out.println(".........................................................");
		System.out.println("... LOAD DATA BEGIN ..................................");
		System.out.println(".........................................................");
		System.out.println("Path to CSV File " + csvFile);
		
		BufferedReader csvFilmes = null;
		String line = "";
		String cvsSplitBy = ";";
		PreparedStatement prepStatement;
		Statement stmt = null;
		int recordCount = 0;

		// Create H2 DB Connection Object
		Connection conn = null;

		try {

			// Register JDBC driver
			Class.forName(JDBC_DRIVER);

			// Open a connection
			System.out.println("Connecting to H2 database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// Execute a query
			System.out.println("Creating tables in database...");
			stmt = conn.createStatement();

			// Execute a query for remove table if exists
			String sql = "DROP VIEW IF EXISTS AWARDSINTERVAL_VIEW ; DROP TABLE IF EXISTS AWARDSINTERVAL; DROP TABLE IF EXISTS MOVIES;DROP TABLE IF EXISTS PRODUCERS;DROP TABLE IF EXISTS STUDIOS;";
			stmt.executeUpdate(sql);

			// TABLE MOVIES
			sql = " CREATE TABLE MOVIES " 
					+ " (id BIGINT auto_increment, " 
					+ " year CHAR(4), " 
					+ " title VARCHAR(255), "
					+ " studios VARCHAR(255), " 
					+ " producers VARCHAR(255), " + " winner BOOLEAN, "
					+ " PRIMARY KEY ( id ))";
			stmt.executeUpdate(sql);

			// TABLE PRODUCERS
			sql = " CREATE TABLE PRODUCERS " 
					+ " (id BIGINT auto_increment, " 
					+ " name VARCHAR(255), "
					+ " fk_id_movie BIGINT, " 
					+ " PRIMARY KEY ( id ))";
			stmt.executeUpdate(sql);

			// TABLE STUDIOS
			sql = " CREATE TABLE STUDIOS " 
					+ " (id BIGINT auto_increment, " 
					+ " name VARCHAR(255), "
					+ " fk_id_movie BIGINT, " 
					+ " PRIMARY KEY ( id ))";
			stmt.executeUpdate(sql);

			
			// TABLE STUDIOS
			sql = " CREATE TABLE AWARDSINTERVAL " 
					+ " (id BIGINT auto_increment, " 
					+ " producer VARCHAR(255), "	
					+ " interval INTEGER, "					
					+ " previousWin INTEGER, "
					+ " followingWin INTEGER, "
					+ " PRIMARY KEY ( id ))";
			stmt.executeUpdate(sql);
			
			sql = "CREATE VIEW AWARDSINTERVAL_VIEW  " + 
			"AS  " + 
			"SELECT * FROM (  " + 
			"SELECT   " + 
			"  PRODUCERS   " + 
			", DATEDIFF('YEAR',CONCAT(MINIMO , '-01-01'), CONCAT(MAXIMO, '-01-01')) as INTERVAL  " + 
			", MINIMO AS PREVIOUSWIN  " + 
			", MAXIMO AS FOLLOWINGWIN  " + 
			"FROM (  " + 
			"SELECT   " + 
			"   M.PRODUCERS   " + 
			", (SELECT YEAR  FROM MOVIES  WHERE YEAR = (select MIN(YEAR ) from MOVIES M1 WHERE M1.PRODUCERS =M.PRODUCERS   )  LIMIT 1) AS MINIMO   " + 
			", (SELECT YEAR  FROM MOVIES  WHERE YEAR = (select MAX(YEAR ) from MOVIES M1 WHERE M1.PRODUCERS =M.PRODUCERS   )  LIMIT 1) AS MAXIMO  " + 
			"FROM MOVIES M  " + 
			"WHERE M.WINNER=1   " + 
			") WHERE DATEDIFF('YEAR',CONCAT(MINIMO , '-01-01'), CONCAT(MAXIMO, '-01-01'))>0 ORDER BY INTERVAL DESC LIMIT 1  " + 
			")  " + 
			"UNION ALL  " + 
			"SELECT * FROM (  " + 
			"SELECT   " + 
			"  PRODUCERS   " + 
			", DATEDIFF('YEAR',CONCAT(MINIMO , '-01-01'), CONCAT(MAXIMO, '-01-01')) as INTERVAL  " + 
			", MINIMO AS PREVIOUSWIN  " + 
			", MAXIMO AS FOLLOWINGWIN  " + 
			"FROM (  " + 
			"SELECT   " + 
			"   M.PRODUCERS   " + 
			", (SELECT YEAR  FROM MOVIES  WHERE YEAR = (select MIN(YEAR ) from MOVIES M1 WHERE M1.PRODUCERS =M.PRODUCERS   )  LIMIT 1) AS MINIMO   " + 
			", (SELECT YEAR  FROM MOVIES  WHERE YEAR = (select MAX(YEAR ) from MOVIES M1 WHERE M1.PRODUCERS =M.PRODUCERS   )  LIMIT 1) AS MAXIMO  " + 
			"FROM MOVIES M  " + 
			"WHERE M.WINNER=1   " + 
			") WHERE DATEDIFF('YEAR',CONCAT(MINIMO , '-01-01'), CONCAT(MAXIMO, '-01-01'))>0 ORDER BY INTERVAL ASC LIMIT 1  " + 
			");";
			stmt.executeUpdate(sql);
			
			System.out.println("Created table in given database...");

		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} // end try

		try {

			csvFilmes = new BufferedReader(new FileReader(csvFile));
			while ((line = csvFilmes.readLine()) != null) {

				String[] csvLista = line.split(cvsSplitBy);
				String year = adjustmentTrimming(adjustment(csvLista, 0));
				String title = adjustmentTrimming(adjustment(csvLista, 1));
				String studios = adjustmentTrimming(adjustment(csvLista, 2));
				String producers = adjustmentTrimming(adjustment(csvLista, 3));
				String winner = "False";
				int idMovie = 0;

				if (csvLista.length > 4 && csvLista[4] != null) { // especial adjustment for field winner
					winner = "True";
				}

				try {
					// INSERT IN TABLE MOVIES
					conn.setAutoCommit(false);
					prepStatement = conn.prepareStatement("INSERT INTO MOVIES ( year, title, studios, producers, winner) VALUES ( ?, ?, ?, ?, ?)");
					prepStatement.setString(1, year);
					prepStatement.setString(2, title);
					prepStatement.setString(3, studios);
					prepStatement.setString(4, producers);
					prepStatement.setString(5, winner);
					recordCount = recordCount + prepStatement.executeUpdate();
					idMovie = recordCount;

					String[] csvListaProducers = producers.split(",");
					String[] csvListaStudios = studios.split(",");

					// INSERT IN TABLE PRODUCERS
					for (int i = 0; i < csvListaProducers.length; i++) {

						// INSERT IN TABLE PRODUCERS
						prepStatement = conn.prepareStatement("INSERT INTO PRODUCERS ( name, fk_id_movie) VALUES ( ?, ?)");
						prepStatement.setString(1, adjustmentTrimming(csvListaProducers[i]));
						prepStatement.setInt(2, idMovie);
						prepStatement.executeUpdate();
					}

					// INSERT IN TABLE STUDIOS
					for (int i = 0; i < csvListaStudios.length; i++) {

						// INSERT IN TABLE STUDIOS
						prepStatement = conn.prepareStatement("INSERT INTO STUDIOS ( name, fk_id_movie) VALUES ( ?, ?)");
						prepStatement.setString(1, adjustmentTrimming(csvListaStudios[i]));
						prepStatement.setInt(2, idMovie);
						prepStatement.executeUpdate();
					}

					// REMOVE HEADER
					prepStatement = conn.prepareStatement("DELETE FROM MOVIES WHERE ID=1");
					prepStatement.executeUpdate();
					prepStatement = conn.prepareStatement("DELETE FROM STUDIOS WHERE ID=1");
					prepStatement.executeUpdate();
					prepStatement = conn.prepareStatement("DELETE FROM PRODUCERS WHERE ID=1");
					prepStatement.executeUpdate();
					conn.commit();

				} catch (Exception ex) {
					System.out.println(ex.toString());
				}
			}

		} catch (FileNotFoundException e) {
			status = false;
			e.printStackTrace();
		} catch (IOException e) {
			status = false;
			e.printStackTrace();
		} finally {
			if (csvFilmes != null) {
				try {
					csvFilmes.close();
					status = true;
				} catch (IOException e) {
					status = false;
					e.printStackTrace();
				}
			}
		}

		System.out.println("Row Count: " + recordCount);
		System.out.println(".........................................................");
		System.out.println("... LOAD DATA FINISH .................................");
		System.out.println(".........................................................");		
		return status;
	}
}