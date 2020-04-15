package appointmentModel;


import java.sql.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//import jdk.javadoc.internal.doclets.toolkit.util.DocFinder.Output;



public class Appointment {
	public Connection connect()
	{
		Connection con=null;
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/newdb", "root", ""); 
			System.out.print("Successfully connected"); 
			
			
		}
		catch(Exception e)
		{
			System.out.print("error");
			e.printStackTrace(); 
		}
		return con;
	}
	public String createAppoitnment(String NIC, String DoctorSpec, String Doctor, String Date, String Time, String hospital) {
		
		int id= 0;
		String Output ="";
		
		try {
			//patientDatabase newp = new patientDatabase();
			Connection con = connect();

			if (con == null) {
				//return "Error while connectingto the database for inserting";
			}

			String query = " insert into appointment_details (`appointment_id`,`patient_nic`,`doc_speciality`, `doc_name`, `appointment_date`,`appointment_time`, `appointment_hospital`) "
					+ "values(?, ?, ?, ?, ?, ?, ?)";
			Date date = Calendar.getInstance().getTime();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
			String strDate = dateFormat.format(date);
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, NIC);
			preparedStmt.setString(3, DoctorSpec);
			preparedStmt.setString(4, Doctor);
			preparedStmt.setString(5, strDate);
			preparedStmt.setString(6, Time);
			preparedStmt.setString(7, hospital);
			
			
	    	String appointment = "select appointment_id from appointment_details where patient_nic="+NIC ;
	    	
	    	
	    	
	    	Statement st=con.createStatement();
	    	 ResultSet rs = st.executeQuery(appointment);
	    	
	    	while(rs.next()) {
	    		id = rs.getInt(1);
	    		System.out.println(id);
	    		
	    		
	    	}
	    	
	    	
			Output = String.valueOf(id);
	    	
			

			preparedStmt.execute();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Output;
	}
	public String readAppointment()
	{
		String output= "";
		
		try {
			Connection con=connect();
			
			if(con==null)
			{
				return "error while connecting to the database for reading";
			}
			
			output = "<table border=\"1\"><tr><th>appointment number</th>"
					 +"<th>patient nic</th>"
					 + "<th>doctor speclization</th>"
					 +"<th>doctor name</th>"
					 +"<th>doctor nic</th>"
					 +"<th>date</th>"
					 +"<th>time</th>"
					 +"<th>hospital</th></tr>";
					// + "<th>Update</th><th>Remove</th></tr>"; 
			String  query="select * from appointment_details";
			Statement stmtStatement=con.createStatement();
			ResultSet rs=stmtStatement.executeQuery(query);
			
			while (rs.next())
			{
				String pid=Integer.toString(rs.getInt("appointment_id"));
				String pnic=rs.getString("patient_nic");
				String dspecilaztionString=rs.getString("doc_speciality");
				String dname=rs.getString("doc_name");
				String adate=rs.getString("appointment_date");
				String atime=rs.getString("appointment_time");
				String ahospital=rs.getString("appointment_hospital");
				
				output += "<tr><td>" + pid + "</td>"; 
				output += "<td>" + pnic + "</td>"; 
				output += "<td>" + dspecilaztionString + "</td>"; 
				output += "<td>" + dname + "</td>"; 
				output += "<td>" + adate + "</td>"; 
				output += "<td>" + atime + "</td>"; 
				output += "<td>" + ahospital + "</td>"; 
				
			}
			con.close();
			
			output+="</table>";
			
		}
		catch(Exception e)
		{
			output="error while reading the appointment";
			System.err.println(e.getMessage());
		}
		return output;	
		
	}
	public String deletePatient(String patientID)

	{
		String output="";
		
		try {
			Connection connection=connect();
			
			if(connection==null)
			{
				return "Error while connecting to the database for deleting.";
			}
			
			String query="delete from appointment_details where appointment_id=?";
			
			PreparedStatement prepareStmt=connection.prepareStatement(query);
			
			prepareStmt.setInt(1, Integer.parseInt(patientID));
			prepareStmt.execute();
			connection.close();
			
			output= "deleted successfully";
		}
		catch(Exception e)
		{
			output="error while deleting the patient";
			System.err.println(e.getMessage());
		}
		return output;
	}
	public String updateAppoitnment(String appointmentNum,String date,String hospital,String time)

	{
		String output="";
		
		try {
			Connection connection=connect();
			if(connection==null)
			{
				return "error while connecting to the database for updating";
			}
			
			String query="UPDATE appointment_details SET appointment_date=?,appointment_hospital=?,appointment_time=? "
					+ "where appointment_id=? ";
					
					PreparedStatement pStatement=connection.prepareStatement(query);
					
					//pStatement.setString(1, appointmentNum);
					pStatement.setString(1, date);
					pStatement.setString(2, hospital);
					pStatement.setString(3, time);
					pStatement.setInt(4, Integer.parseInt(appointmentNum)); 
					
					pStatement.execute();
					connection.close();
					
					output="updated successfully";
		}
		catch (Exception e) {
			// TODO: handle exception
			output="error while updating the appointment";
			System.err.println(e.getMessage());
		}
		return output;
	}
}
