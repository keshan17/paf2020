package AppointmentService;


import appointmentModel.Appointment;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document; 

@Path("/Appontment") 
public class AppointmentService {
	Appointment itemObj = new Appointment();
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readAppointment()
	 {
	 return itemObj.readAppointment();
	 }
	
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertAppointment(@FormParam("nic") String nic,
	 @FormParam("DoctorSpec") String DoctorSpec,
	 @FormParam("Doctor") String Doctor,
	 @FormParam("Date") String Date,
	 @FormParam("Time") String Time,
	 @FormParam("hospital") String hospital)
	 
	{
	 String output = itemObj.createAppoitnment(nic, DoctorSpec, Doctor, Date,Time,hospital);
	return output;
	}
	
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateItem(String itemData)
	{
	//Convert the input string to a JSON object
	 JsonObject itemObject = new JsonParser().parse(itemData).getAsJsonObject();
	//Read the values from the JSON object
	 String appointmentNum = itemObject.get("appointmentNum").getAsString();
	 String date = itemObject.get("date").getAsString();
	 String hospital = itemObject.get("hospital").getAsString();
	 String time = itemObject.get("time").getAsString();
	 
	 String output = itemObj.updateAppoitnment(appointmentNum, date, hospital, time);
	return output;
	}
	
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteItem(String appData)
	{
	//Convert the input string to an XML document
	 Document doc = Jsoup.parse(appData, "", Parser.xmlParser());

	//Read the value from the element <itemID>
	 String appointId = doc.select("appointId").text();
	 String output = itemObj.deletePatient(appointId);
	return output;
	}

	

	
	
	

}
