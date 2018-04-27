package com.example.jetty_jersey.bdd;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.util.ArrayList;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

import com.example.jetty_jersey.model.Location;
import com.example.jetty_jersey.model.MMap;
import com.example.jetty_jersey.persistance.MapDAO;


public class MapDB implements MapDAO {
    private static final Logger logger = LogManager.getLogger(MapDB.class);


	/* Rappel des champs utilises : 
	 * MAP : 
	 * 	public String name;
		public User creator;
		public ArrayList<Location> location;
		public String visibilite;
	 * Location :
	 * 	public String place;
		public double lat;
		public double lng;
		public String tag;
		public String msg;
		public String filename;
	*
	 */
	public void addLocation(String idMap,Location l) throws IOException{
		TransportClient client = Bdd.connectionToBD();
		IndexResponse response = client.prepareIndex("location", "location")
		        .setSource(jsonBuilder()
		                    .startObject()
		                    	.field("idMap", idMap)
		                        .field("place", l.place)
		                        .field("lat", ""+ l.lat)
		                        .field("lng", ""+l.lng)
		                        .field("tag", l.tag )
		                        .field("msg", l.msg)
		                        .field("filename", l.filename)
		                    .endObject()
		                  )
		        .get();

	}

	public  MMap[] getMaps(String creator) {
	
		TransportClient client = Bdd.connectionToBD();
		SearchResponse response = client.prepareSearch("map")
	    .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
	    .setQuery(QueryBuilders.matchPhraseQuery("creator", creator))
	    .get();
		ArrayList<MMap> tab = new ArrayList<MMap>();
		SearchHit[] hitTab = response.getHits().getHits();
		for(int i = 0; i < hitTab.length ; i++) {
			SearchHit hit = hitTab[i];
			//String location = (String) hit.getSourceAsMap().get("location");
			//logger.debug("location id : " + location);
			String name = (String) hit.getSourceAsMap().get("name");
			String visibilite = (String) hit.getSourceAsMap().get("visibilite");
			String id = hit.getId();
			MMap map = new MMap(name, creator, visibilite, getLocation(id));
			tab.add(map);
		}
		MMap  [] tab2 = new MMap [tab.size()];
		for(int i = 0; i < tab.size(); i++) {
			tab2[i] = tab.get(i);
		}
		return tab2;
	}
	
	public void addMap(MMap instance) {
		TransportClient client = Bdd.connectionToBD();
		try {
			IndexResponse response = client.prepareIndex("map", "map")
			        .setSource(jsonBuilder()
			                    .startObject()
			                        .field("name", instance.name)
			                        .field("creator", instance.creator)
			                        .field("location", "id" )
			                        .field("visibilite", instance.visibilite)
			                    .endObject()
			                  )
			        .get();
			String _id = response.getId();
			ArrayList<Location> ll = instance.location;
			for(Location tmpL : ll) {
				this.addLocation(_id, tmpL);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String[] getListMapName(String login) {
		TransportClient client = Bdd.connectionToBD();
		SearchResponse response = client.prepareSearch("map")
        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
        .setQuery(QueryBuilders.matchPhraseQuery("creator", login))
        .get();
		ArrayList<String> tab = new ArrayList<String>();
		SearchHit[] hitTab = response.getHits().getHits();
		for(int i = 0; i < hitTab.length ; i++) {
			SearchHit hit = hitTab[i];
			String nameMap = (String) hit.getSourceAsMap().get("name");
			if(!tab.contains(nameMap)) {
				tab.add(nameMap);
			}
		}
		String  [] tab2 = new String  [tab.size()+1];
		for(int i = 0; i < tab2.length-1; i++){
			tab2[i] = tab.get(i);
		}
		tab2[tab2.length-1] = Integer.toString(hitTab.length);
		return tab2;
	}
	
	public Location getLocationByPlace (String id, String place){
		TransportClient client = Bdd.connectionToBD();
		SearchResponse response = client.prepareSearch("location")
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(QueryBuilders.matchPhraseQuery("idMap", id)) // rajouter le createur
				.get();
		
		SearchHit[] hitTab = response.getHits().getHits();
		
		if(hitTab.length != 0) {
			for(int i = 0; i < hitTab.length; i++){
				SearchHit hit = hitTab[i];
				String placetmp = (String) hit.getSourceAsMap().get("place");
				if(placetmp.equals(place)) {
					double lat = Double.parseDouble((String) hit.getSourceAsMap().get("lat"));
					double lng = Double.parseDouble((String) hit.getSourceAsMap().get("lng"));
					String tag = (String) hit.getSourceAsMap().get("tag");
					String msg = (String) hit.getSourceAsMap().get("msg");
					String filename = (String) hit.getSourceAsMap().get("filename");
					return new Location (place, lat, lng, tag, msg, filename);
				}
			}
		} else {
			logger.debug("get location : impossible de trouver la location");
			return null;
		}
		return null;
	}
	
	public ArrayList<Location> getLocation (String id){
		TransportClient client = Bdd.connectionToBD();
		SearchResponse response = client.prepareSearch("location")
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(QueryBuilders.matchPhraseQuery("idMap", id))
				.get();
		
		SearchHit[] hitTab = response.getHits().getHits();
		if(hitTab.length != 0) {
			ArrayList<Location> locationList = new ArrayList<Location>();
			for(int i = 0; i < hitTab.length; i++){
				SearchHit hit = hitTab[i];
				String place = (String) hit.getSourceAsMap().get("place");
				double lat = Double.parseDouble((String) hit.getSourceAsMap().get("lat"));
				double lng = Double.parseDouble((String) hit.getSourceAsMap().get("lng"));
				String tag = (String) hit.getSourceAsMap().get("tag");
				String msg = (String) hit.getSourceAsMap().get("msg");
				String filename = (String) hit.getSourceAsMap().get("filename");
				locationList.add(new Location (place, lat, lng, tag, msg, filename));
			}
			return locationList;
		}
		logger.debug("get location : impossible de trouver la location");
		return null;
	}
	
	public MMap infoLocation(String login, String mapName, String mapPlace) {
		TransportClient client = Bdd.connectionToBD();
		SearchResponse response = client.prepareSearch("map")
        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
        .setQuery(QueryBuilders.matchPhraseQuery("name", mapName)) // rajouter le createur
        .get();

		SearchHit[] hitTab = response.getHits().getHits();
		if(hitTab.length != 0) {
			SearchHit hit = hitTab[0];
			String id = hit.getId();
			String visibilite = (String) hit.getSourceAsMap().get("visibilite");
			Location l = this.getLocationByPlace(id, mapPlace);
			MMap m = new MMap(mapName,login,visibilite);
			m.setLocation(l);
			return m ;
			
		} else {
			System.out.println("Aucune Location n'a ete trouvee");
		}
		logger.debug("attention l'argument de MMap est null");
		return null;

	}

	public void deletePlace(MMap instance, Location l) {
		TransportClient client = Bdd.connectionToBD();
		SearchResponse response = client.prepareSearch("location")
        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)     
        //.setQuery(QueryBuilders.multiMatchQuery("place", l.place), (name,instance.name)) // a tester
        .setQuery(QueryBuilders.matchPhraseQuery("place", l.place))
        .get();
		SearchHit[] hitTab = response.getHits().getHits();
		if(hitTab.length != 0) {
			SearchHit hit = hitTab[0];
			String id = hit.getId();
			DeleteResponse response2 = client.prepareDelete("location", "location", id).get();

		}
		else {
			System.out.println("Aucune location trouve");
		}
	}

	public void deleteMap(String name) {
		TransportClient client = Bdd.connectionToBD();
		SearchResponse response = client.prepareSearch("map")
        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
        .setQuery(QueryBuilders.matchPhraseQuery("name", name))
        .get();
		SearchHit[] hitTab = response.getHits().getHits();
		if(hitTab.length != 0) {
			SearchHit hit = hitTab[0];
			String id = hit.getId();
			DeleteResponse response2 = client.prepareDelete("map", "map", id).get();
		}
		else {
			System.out.println("Aucune map trouvee");
		}
	}
	
	public void updateMap(String name,String mapName,String placeName,String tag,String visib, String msg){
		TransportClient client = Bdd.connectionToBD();
		SearchResponse response = client.prepareSearch("map")
		        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
		.setQuery(QueryBuilders.matchPhraseQuery("name", mapName))
		.get();
		SearchHit[] hitTab = response.getHits().getHits();
		if(hitTab.length != 0) {
			SearchHit hit = hitTab[0];
			String id = hit.getId();
			
		try {
			client.prepareUpdate("map", "map", id)
			.setDoc(jsonBuilder()               
			    .startObject()
			        .field("name", mapName)
			        .field("visibilite", visib)
			    .endObject())
			.get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}else {
			System.out.println("L'element demande pour l'update n'existe pas");
			return;
		}
	}
}
