package com.example.jetty_jersey.ws;

import com.example.jetty_jersey.model.*;
import com.example.jetty_jersey.persistance.MapDB;

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


@Path("/index")
public class MapRest {
	
	@GET
	@Path("/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public MMap[] getMap(@PathParam( "name" ) String name) {
		System.out.println("name ="+name);
		/*Location loc1 = new Location("Eiffel Tower",48.8583905296204,2.2944259643554688,"IKAN","the view from the topis just breathtaking","img/paris1.jpg");
		Location loc2 = new Location("...IKHAN...",48.87386089807715,2.294940948486328,"IKAN",".............................","img/paris2.jpg");
		Location loc3 = new Location("blablo",48.8386053,2.378623100000027,"IKAN","the view from the topis just breathtaking","img/plage1.jpg");
		Location loc4 = new Location("...blablo...",41.38773117668287,2.201385498046875,"IKAN",".............................","img/plage2.jpg");
		
		MMap [] mp= {new MMap("PARIS",name,"public"),new MMap("IFNI",name,"public")};
		mp[0].setLocation(loc1);
		mp[0].setLocation(loc2);
		mp[1].setLocation(loc3);
		mp[1].setLocation(loc4);*/
		MapDB mdb = new MapDB();
		return mdb.getMaps(name);
		//return mp;
	}
	
	
	@GET
	@Path("{name}/listname")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String [] getlistMapName(@PathParam("name") String name) {
		//nbr location ici 4
		MapDB mdb = new MapDB();
		String[] s = mdb.getListMapName(name);
		if(s.length == 0) {
			String[] s2 = {"0"};
			return s2;
		}
		//String [] s= {"PARIS","IFNI","4"};
		return s;
	}
	
	@POST
	@Path("/{name}/addMapOrPlace")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response addMap(@PathParam("name") String name,
							@FormParam("namemap") String namemap,
							@FormParam("tag") String tag,@FormParam("place") String place,
							@FormParam("msg") String msg,@FormParam("visib") String visib,
							@FormParam("lat") String lat,@FormParam("lng") String lng) {
		//upload image  namemap
		
		//DAO.addMap(name) 
		MMap map = new MMap(namemap, name, visib);
		MapDB mdb = new MapDB();
		mdb.addMap(map);
		System.out.println(name+" : "+namemap+","+visib+","+place+","+lat+","+lng+","+tag+","+msg);
		try {
			URI location = new URI("http://localhost:8088/index.html");
			return Response.seeOther(location).entity("map").build();
		} catch (URISyntaxException e) {
	        e.printStackTrace();
	    }
		return null;
	}
	
	@GET
	@Path("/{name}/{namemap}/{place}/info")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public MMap infoLocation(@PathParam("name") String name,
			@PathParam("namemap") String namemap,
			@PathParam("place") String place) {
		//DAO.getMap(name,namemap,place)
		return new MMap("PARIS",name,"public");
	}
	
	
	@POST
	@Path("/{name}/{namemap}/{place}/update")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response updateMap(@PathParam("name") String name,
			@PathParam("namemap") String namemap,
			@PathParam("place") String place,@FormParam("tag") String tag,
			@FormParam("msg") String msg,@FormParam("visib") String visib) {
		//DAO.updateMap(name,namemap,place,tag,visib,msg)
		try {
			URI location = new URI("http://localhost:8088/index.html");
		       return Response.seeOther(location).entity("map").build();
			} catch (URISyntaxException e) {
		        e.printStackTrace();
		    }
		return null;
	}
	
	@DELETE
	@Path("/{name}/{namemap}/{place}/delete")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response UpdateMap(@PathParam("name") String name,
							@PathParam("namemap") String namemap,
							@PathParam("place") String place) {
			//DAO.deleteMap(name,namemap,place)
			try {
				URI location = new URI("http://localhost:8088/index.html");
			       return Response.seeOther(location).entity("map").build();
				} catch (URISyntaxException e) {
			        e.printStackTrace();
			    }
			return null;
		
	}
	

}
