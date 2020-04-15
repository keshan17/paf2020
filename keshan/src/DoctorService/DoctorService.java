package DoctorService;


import DoctorModel.Doctor;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document; 

@Path("/Doctor") 
public class DoctorService {
	
	
	Doctor itemObj = new Doctor();
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readDoc()
	 {
	 return "Hello";
	 }

}
