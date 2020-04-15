package DoctorModel;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Doctor {
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
	public String addSession(String nic,String Specialization,String hospital,String room,String datee,String time,String time2)
	{
	String Output ="";
	
	try {
		//patientDatabase newp = new patientDatabase();
		Connection con = connect();

		if (con == null) {
			//return "Error while connectingto the database for inserting";
		}

		String query = " insert into doctor_portal (`doc_nic`,`doc_specialization`,`doc_hospital`, `room_no`, `date`,`time`,`time2`) "
				+ "values(?, ?, ?, ?, ?, ?, ?)";
//		Date date = Calendar.getInstance().getTime();
//		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
//		String strDate = dateFormat.format(date);
		
		PreparedStatement preparedStmt = con.prepareStatement(query);
		preparedStmt.setString(1, nic);
		preparedStmt.setString(2, Specialization);
		preparedStmt.setString(3, hospital);
		preparedStmt.setInt(4, Integer.parseInt(room) );
		preparedStmt.setString(5, datee);
		preparedStmt.setString(6, time);
		preparedStmt.setString(7, time2);
		
		preparedStmt.execute();
		con.close();
		System.out.println("inserted");
	} catch (Exception e) {
		e.printStackTrace();
		System.out.println(" not inserted");
	}

	return Output;
}
	public String deleteSession(String docNic)
	{
		String output="";
		
		try {
			Connection connection=connect();
			
			if(connection==null)
			{
				return "Error while connecting to the database for deleting.";
			}
			
			String query="delete from doctor_portal where doc_nic=?";
			
			PreparedStatement prepareStmt=connection.prepareStatement(query);
			
			prepareStmt.setInt(1, Integer.parseInt(docNic));
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
	public String updateSession(String nic,String hospital,String date,String time,String time2,String roomno)
	{
		String output="";
		
		try {
			Connection connection=connect();
			if(connection==null)
			{
				return "error while connecting to the database for updating";
			}
			
			String query="UPDATE doctor_portal SET doc_hospital=?,date=?,time=?,time2=?,room_no=? "
					+ "where doc_nic=? ";
					
					PreparedStatement pStatement=connection.prepareStatement(query);
					
					//pStatement.setString(1, appointmentNum);
					
					pStatement.setString(1, hospital);
					pStatement.setString(2, date);
					pStatement.setString(3, time);
					pStatement.setString(4, time2);
					pStatement.setInt(5, Integer.parseInt(roomno));
					pStatement.setString(6, nic);
					
					
					
					
					pStatement.execute();
					connection.close();
					System.out.print("updated");
					output="updated successfully";
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.print("not updated");
			output="error while updating the appointment";
			System.err.println(e.getMessage());
		}
		return output;
	}
	public String readSession()
	{
		String output= "";
		
		try {
			Connection con=connect();
			
			if(con==null)
			{
				return "error while connecting to the database for reading";
			}
			
			output = "<table border=\"1\"><tr><th>Date</th>"
					 +"<th>Doctor NIC</th>"
					 + "<th>Hospital</th>"
					 +"<th>start Time</th>"
					 +"<th>End time</th>"
					 +"<th>Room number</th></tr>";
					 //+"<th>hospital</th></tr>";
					// + "<th>Update</th><th>Remove</th></tr>"; 
			String  query="select * from doctor_portal ";
			Statement stmtStatement=con.createStatement();
			ResultSet rs=stmtStatement.executeQuery(query);
			
			while (rs.next())
			{
				String datee=rs.getString("date");
				String nic=rs.getString("doc_nic");
				String hospital=rs.getString("doc_hospital");
				String stime=rs.getString("time");
				String etime=rs.getString("time2");
				String roomno=Integer.toString(rs.getInt("room_no"));
				
				
				output += "<tr><td>" + datee + "</td>"; 
				output += "<td>" + nic + "</td>"; 
				output += "<td>" + hospital + "</td>"; 
				output += "<td>" + stime + "</td>"; 
				output += "<td>" + etime + "</td>"; 
				output += "<td>" + etime + "</td>"; 
				
				
			}
			con.close();
			
			output+="</table>";
			System.out.println("table can be view");
		}
		catch(Exception e)
		{
			output="error while reading the appointment";
			System.err.println(e.getMessage());
			System.out.println("table can't be view");
		}
		return output;	
		
	}
	
	

}