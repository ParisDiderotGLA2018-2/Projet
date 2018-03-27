package com.example.jetty_jersey.ws;

public class Location {
	
	public String place;
	public double lat;
	public double lng;
	public String tag;
	public String msg;
	public String filename; 
	
	public Location(String place,double lat,double lng,String tag,String msg,String filename) {
		this.place=place;
		this.lat=lat;
		this.lng=lng;
		this.tag=tag;
		this.msg=msg;
		this.filename=filename;
	}

}
