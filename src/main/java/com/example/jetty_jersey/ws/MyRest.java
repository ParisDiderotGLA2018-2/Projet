package com.example.jetty_jersey.ws;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import java.net.URI;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;


@Path("/index")
public class MyRest {
	
	
	@GET
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getUser(@QueryParam("name") String name,@QueryParam("pass")  String pass) {
		
		//User instance = new User(name, pass);
		//200 or 403
		String output = "name: "+name+" and pass : "+pass;
		System.out.println(output);
		return Response.ok("200").build();
	}
	
	
	@GET
	@Path("/mymap")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public MMap [] getMap() {
	
		User U = new User("bla","blo");
		Location loc1 = new Location("Eiffel Tower",48.8583905296204,2.2944259643554688,"IKAN","the view from the topis just breathtaking","img/paris1.jpg");
		Location loc2 = new Location("...IKHAN...",48.87386089807715,2.294940948486328,"IKAN",".............................","img/paris2.jpg");
		Location loc3 = new Location("blablo",48.8386053,2.378623100000027,"IKAN","the view from the topis just breathtaking","img/plage1.jpg");
		Location loc4 = new Location("...blablo...",41.38773117668287,2.201385498046875,"IKAN",".............................","img/plage2.jpg");
		
		MMap [] mp= {new MMap("PARIS",U,"public"),new MMap("IFNI",U,"public")};
		mp[0].setLocation(loc1);
		mp[0].setLocation(loc2);
		mp[1].setLocation(loc3);
		mp[1].setLocation(loc4);
		
		return mp;
	}
	
	@GET
	@Path("/add/listname")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String [] getlistMapName() {
		//nbr location ici 4	
		String [] s= {"PARIS","IFNI","4"};
		return s;
	}
	
	@GET
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addMap(@QueryParam("name") String name,@QueryParam("tag") String tag,@QueryParam("place") String place,@QueryParam("msg") String msg,@QueryParam("visib") String visib,@QueryParam("lat") String lat,@QueryParam("lng") String lng) {
	
		//recupere le ID a chaque appel pour user
		//upload image  
		/*
		User U = new User("bla","blo");
		double lt = Double.parseDouble(lat);
		double lg = Double.parseDouble(lng);
		Location loc = new Location(place,lt,lg,tag,msg,"file?");
		MMap mp= new MMap(name,U,visib);
		mp.setLocation(loc);*/
		System.out.println(name+","+visib+","+place+","+lat+","+lng+","+tag+","+msg);
		return Response.ok("200").build();
	}
	
	
	
	

}
