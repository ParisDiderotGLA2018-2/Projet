package persistance;

import com.example.jetty_jersey.model.*;

interface MapDAO {
	//which user
	MMap[] getMaps(User U);
	String[] getListMapName(User U);
	
	// Donne les infos d'un lieu. 
	//Le type de retour est map pour renvoyer aussi le nom de la map
	MMap InfoLocation(String login, String mapName, String mapPlace); 

	void addMap(MMap instance);
	//void updateMap(String name,String mapName,String placeName,tag,visib,msg);
	
	// comment je peux donner l'id?? void editMap(int id, MMap instance);
	void deletePlace(MMap instance, Location l);
	void deleteMap(MMap instance);
}
