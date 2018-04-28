package com.example.jetty_jersey.ws;

import com.example.jetty_jersey.db.MapDB;
import com.example.jetty_jersey.model.*;

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
	public MMap[] getMap(@PathParam( "name") String name) {
		MapDB mdb = new MapDB();
		return mdb.getMaps(name);
	}
	
	
	@GET
	@Path("{name}/listname")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String [] getlistMapName(@PathParam("name") String name) {
		
		MapDB mdb = new MapDB();
		String[] s = mdb.getListMapName(name);
		if(s.length == 0) {
			String[] s2 = {"0"};
			return s2;
		}
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
		
		// upload image namemap
		
		MMap map = new MMap(namemap, name, visib);
		double latD = Double.parseDouble(lat);
		double lngD = Double.parseDouble(lng);
		Location loc = new Location(place, latD, lngD, tag, msg, "");
		map.setLocation(loc);
		MapDB mdb = new MapDB();
		mdb.addMap(map);
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
		MapDB mdb = new MapDB();
		return mdb.infoLocation(name, namemap, place);
	}
	
	
	@POST
	@Path("/{name}/{namemap}/{place}/update")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response updateMap(@PathParam("name") String name,
			@PathParam("namemap") String namemap,
			@PathParam("place") String place,@FormParam("tag") String tag,
			@FormParam("msg") String msg,@FormParam("visib") String visib) {

		MapDB mdb = new MapDB();
		mdb.updateMap(name, namemap, place, tag, visib, msg);
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
	public Response deleteMap(@PathParam("name") String name,
							@PathParam("namemap") String namemap,
							@PathParam("place") String place) {

			MapDB mdb = new MapDB();
			mdb.deleteMap(namemap);
			try {
				URI location = new URI("http://localhost:8088/index.html");
			       return Response.seeOther(location).entity("map").build();
				} catch (URISyntaxException e) {
			        e.printStackTrace();
			    }
			return null;
	}
}
