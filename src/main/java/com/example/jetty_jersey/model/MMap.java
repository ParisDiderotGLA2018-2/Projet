package com.example.jetty_jersey.model;

import java.util.ArrayList;

public class MMap {
	
	public String name;
	public String creator;
	public ArrayList<Location> location;
	public String visibilite;

	// constructors


	public MMap(String name,String creator,String visibilite) {
		this.name = name;
		this.creator=creator;
		this.location= new ArrayList<Location>();
		this.visibilite=visibilite;

	}

	public void setLocation(Location l) {
		location.add(l);
	}


}
