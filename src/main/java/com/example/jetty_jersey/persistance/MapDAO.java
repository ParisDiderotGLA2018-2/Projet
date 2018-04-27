package com.example.jetty_jersey.persistance;

import com.example.jetty_jersey.model.*;

public interface MapDAO {
	
	// which user
	MMap[] getMaps(String creator);
	String[] getListMapName(String name);
	
	// Donne les infos d'un lieu. 
	// Le type de retour est map pour renvoyer aussi le nom de la map
	MMap infoLocation(String login, String mapName, String mapPlace); 

	void addMap(MMap instance);
	// void updateMap(String name, String mapName, String placeName, tag, visib, msg);
	void deletePlace(MMap instance, Location l);
	void deleteMap(String name);
}
