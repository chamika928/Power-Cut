package com;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class PowerCut {
	
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/electriproject?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	//Read function for powercut
	public String readPowerCuts()
	{ 
		 String output = ""; 
		 try
		 { 
			 Connection con = connect(); 
			 if (con == null) 
			 { 
				return "Error while connecting to the database for reading."; 
			 } 
	
			 // Prepare the html table to be displayed
			 output = "<table border='1' class='table'><thead class=\"table-dark\"><tr><th> Province</th>" +"<th>City</th><th>Date</th>" + "<th>Time</th>" + "<th>Update</th><th>Remove</th></tr></thead>";
			 String query = "select * from powercutm";
			 Statement stmt = con.createStatement(); 
			 ResultSet rs = stmt.executeQuery(query); 
			 
			 // iterate through the rows in the result set
			 while (rs.next()) 
			 { 	
				 // Retrieve from database using column names
				 String PowerCutID = Integer.toString(rs.getInt("PowerCutID")); 
				 String PowerCutProvince = rs.getString("PowerCutProvince"); 
				 String PowerCutCity = rs.getString("PowerCutCity"); 
				 String PowerCutDate = rs.getString("PowerCutDate"); 
				 String PowerCutTime = rs.getString("PowerCutTime"); 
				 
				 // Add a row into the html table
				 output += "<tr><td>"+ PowerCutProvince + "</td>";
				 output += "<td>" + PowerCutCity + "</td>"; 
				 output += "<td>" + PowerCutDate + "</td>";
				 output += "<td>" + PowerCutTime + "</td>"; 
				 
				 // Buttons
				 output += 
				   "<td><input name='btnUpdate' type='button' value='Update' " + "class='btnUpdate btn btn-success' data-powerid='" + PowerCutID + "'></td>"
				 + "<td><input name='btnRemove' type='button' value='Remove'class='btnRemove btn btn-danger' data-powerid='" + PowerCutID + "'>"+"</td>"
				 + "</form></td></tr>";			 
			 } 
			con.close(); 
			// Complete the html table
			output += "</table>"; 
			 } 
		catch (Exception e) 
		 { 
			 output = "Error while reading the Power cut."; 
			 System.err.println(e.getMessage()); 
		 } 
		 return output; 
	}	
	//Insert function
	public String insertPowerCut(String PowerCutProvince, String PowerCutCity, String PowerCutDate, String PowerCutTime)
	{ 
		String output = "";  
		try
		 { 
			Connection con = connect(); 
			 if (con == null) 
			 { 
				 return "Error while connecting to the database"; 
			 } 
			 // create a prepared statement
			 String query = " insert into powercutm (`PowerCutID`,`PowerCutProvince`,`PowerCutCity`,`PowerCutDate`,`PowerCutTime`)"+" values (?, ?, ?, ?, ?)"; 
			 PreparedStatement preparedStmt = con.prepareStatement(query); 
			 // binding values
			 preparedStmt.setInt(1, 0); 
			 preparedStmt.setString(2, PowerCutProvince); 
			 preparedStmt.setString(3, PowerCutCity); 
			 preparedStmt.setString(4, PowerCutDate); 
			 preparedStmt.setString(5, PowerCutTime); 
			 
			 //execute the statement
			 preparedStmt.execute(); 
			 con.close(); 
			 
			 String newPowerCuts = readPowerCuts();
			 output = "{\"status\":\"success\", \"data\": \"" + newPowerCuts + "\"}";
		 } 
		catch (Exception e) 
		 { 
			output = "{\"status\":\"error\", \"data\": \"Error while inserting the item.\"}";
			 System.err.println(e.getMessage()); 
		 } 
		return output; 
	}	
	//Delete function
	public String deletePowerCut(String PowerCutID)
	{
		String output = "";
		try
		{
			Connection con = connect();
			
			if (con == null)
			{
				return "Error while connecting to the database for deleting.";
			}
		
			// create a prepared statement
			String query = "delete from powercutm where PowerCutID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(PowerCutID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newPowerCuts = readPowerCuts();
			output = "{\"status\":\"success\", \"data\": \"" + newPowerCuts + "\"}";
		}
		catch (Exception e)
		{
			output = "{\"status\":\"error\", \"data\": \"Error while deleting PowerCut.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}
	//Update function
	public String updatePowerCut(String PowerCutID, String PowerCutProvince, String PowerCutCity, String PowerCutDate, String PowerCutTime)
	{
		String output = "";
		try 
		{
			Connection con = connect();
	
			if (con == null) 
			{
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE powercutm SET PowerCutProvince=?,PowerCutCity=?,PowerCutDate=?,PowerCutTime=?" + "WHERE PowerCutID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
					preparedStmt.setString(1, PowerCutProvince);
					preparedStmt.setString(2, PowerCutCity);
					preparedStmt.setString(3, PowerCutDate);
					preparedStmt.setString(4, PowerCutTime);
					preparedStmt.setInt(5, Integer.parseInt(PowerCutID));

			// execute the statement
					preparedStmt.execute();
					con.close();

					String newPowerCuts = readPowerCuts();
					output = "{\"status\":\"success\", \"data\": \"" + newPowerCuts + "\"}";
		}
		catch (Exception e)
		{
			output = "{\"status\":\"error\", \"data\": \"Error while updating PowerCut.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}
}
