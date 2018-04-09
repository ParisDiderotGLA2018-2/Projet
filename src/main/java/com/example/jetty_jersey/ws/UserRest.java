package com.example.jetty_jersey.ws;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import java.net.URI;
import java.net.URISyntaxException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import com.example.jetty_jersey.model.*;
import persistance.*;

@Path("/index")
public class UserRest {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getUser(@QueryParam("name") String name,@QueryParam("pass")  String pass) {
		try {
			User U = new User(name, pass);
		
			//200 or 403
			String output = "GET :name: "+name+" and pass : "+pass;
			System.out.println(output);
			URI location;
			
			if(true) // UserDAO.checkUser(U) méthode du DAO qui check si le user existe
			{	
		       location = new URI("http://localhost:8088/index.html");
		       return Response.temporaryRedirect(location).build();
			}else 
			{
				location = new URI("http://localhost:8088/");
				return Response.temporaryRedirect(location).build();
			}
	    	} catch (URISyntaxException e) {
	    		e.printStackTrace();
	    	}
		return null;
		
	}
	
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response setUser(@FormParam("name") String name,@FormParam("pass")  String pass) {
    	try {
    		URI location;	
    		//DAO.addUser(U);
    		String output = "POST : name: "+name+" and pass : "+pass;
    		System.out.println(output);
    		if(true)////name.equals(DAO.verifierUser(name)); //jsp la condition si le login existe deja par EX
    		{	
    			location = new URI("http://localhost:8088/index.html");
    			return Response.temporaryRedirect(location).build();
    		}else 
    		{
			location = new URI("http://localhost:8088/");
			return Response.temporaryRedirect(location).build();
    		}
    	} catch (URISyntaxException e) {
    		e.printStackTrace();
    	}
    	return null;
    }
}
