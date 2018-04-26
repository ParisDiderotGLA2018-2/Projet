package com.example.jetty_jersey.persistance;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.document.DocumentField;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.UpdateByQueryAction;
import org.elasticsearch.index.reindex.UpdateByQueryRequestBuilder;
import org.elasticsearch.search.SearchHit;

import com.example.jetty_jersey.model.User;

import com.example.jetty_jersey.model.Location;
import com.example.jetty_jersey.model.MMap;


public class MapDB implements MapDAO {


	/*
	 *
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
		IndexResponse response = client.prepareIndex("location","location")
		        .setSource(jsonBuilder()
		                    .startObject()
		                    	.field("idMap",idMap)
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
	ArrayList<MMap> tab =new ArrayList<MMap>();
	SearchHit[] hitTab = response.getHits().getHits();
	for(int i = 0; i < hitTab.length ; i++) {
		SearchHit hit = hitTab[i];
		String location = (String) hit.getSourceAsMap().get("location");
		System.out.println("location id : " + location);
		String name = (String) hit.getSourceAsMap().get("name");
		String visibilite = (String) hit.getSourceAsMap().get("visibilite");
		tab.add(new MMap(name,creator,visibilite));
	}
	MMap  [] tab2 = new MMap  [tab.size()];
	for(int i = 0; i < tab.size(); i++){
		tab2[i] = tab.get(i);
	}
	return tab2;
}
	/*TransportClient client = Bdd.connectionToBD();
		System.out.println("get maps");
		SearchRequestBuilder srb2 = client
			    .prepareSearch().setQuery(QueryBuilders.matchQuery("creator", u.login )).setSize(1);
		MultiSearchResponse sr = client.prepareMultiSearch()
				.add(srb2)
				.get();
		for(MultiSearchResponse.Item item : sr.getResponses()){
			SearchResponse response = item.getResponse();
			SearchHit[] hits = response.getHits().getHits();
			for (SearchHit hit : hits) {
				String tag = (String) hit.getSourceAsMap().get("tag");
				System.out.println(tag);
				/*Map<String, DocumentField> reponseFields = hits.getAt(i).getFields();
				DocumentField d = reponseFields.get("place");
				String place = d.getValue();
				System.out.println(item.toString());

			}
		}
		return null;
	}
*/


	/*
	 * 	public String name;
		public User creator;
		public ArrayList<Location> location;
		public String visibilite;
	 * (non-Javadoc)
	 * @see persistance.MapDAO#addMap(com.example.jetty_jersey.model.MMap)
	 */
	public void addMap(MMap instance) {
		TransportClient client = Bdd.connectionToBD();
		try {
			IndexResponse response = client.prepareIndex("map", "map")
			        .setSource(jsonBuilder()
			                    .startObject()
			                        .field("name", instance.name)
			                        .field("creator", instance.creator)
			                        .field("location", "id" )
			                        .field("visibilite", instance.visibilite )
			                    .endObject()
			                  )
			        .get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public String[] getListMapName(String name) {
		TransportClient client = Bdd.connectionToBD();
		SearchResponse response = client.prepareSearch("map")
        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
        .setQuery(QueryBuilders.matchPhraseQuery("creator", name))
        .get();
		ArrayList<String> tab =new ArrayList<String>();
		SearchHit[] hitTab = response.getHits().getHits();
		for(int i = 0; i < hitTab.length ; i++) {
			SearchHit hit = hitTab[i];
			//String location = (String) hit.getSourceAsMap().get("location");
			String nameMap = (String) hit.getSourceAsMap().get("name");
			if(!tab.contains(nameMap)) {
				//String visibilite = (String) hit.getSourceAsMap().get("visibilite");
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

	public MMap InfoLocation(String login, String mapName, String mapPlace) {
		// TODO Auto-generated method stub
		TransportClient client = Bdd.connectionToBD();
		SearchResponse response = client.prepareSearch("map")
        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
        .setQuery(QueryBuilders.matchPhraseQuery("name", mapName)) // rajouter le createur
        .get();

		SearchHit[] hitTab = response.getHits().getHits();
		if(hitTab.length != 0) {
			SearchHit hit = hitTab[0];
			String location = (String) hit.getSourceAsMap().get("location");
			String name = (String) hit.getSourceAsMap().get("name");
			String visibilite = (String) hit.getSourceAsMap().get("visibilite");
			return new MMap(mapName,login,visibilite);

			//TODO SET LOCATION
		}
		return null;

	}

	public void deletePlace(MMap instance, Location l) {
		TransportClient client = Bdd.connectionToBD();
		SearchResponse response = client.prepareSearch("location")
        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)

        .setQuery(QueryBuilders.matchPhraseQuery("name", instance.name))
        .get();
		SearchHit[] hitTab = response.getHits().getHits();
		if(hitTab.length != 0) {
			SearchHit hit = hitTab[0];
			String id = hit.getId();
			System.out.println(id);
			DeleteResponse response2 = client.prepareDelete("map", "map", id).get();

		}
		else{
			System.out.println("Aucune map trouve");
		}
	}

	public void deleteMap(MMap instance) {
		TransportClient client = Bdd.connectionToBD();
		SearchResponse response = client.prepareSearch("map")
        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)

        .setQuery(QueryBuilders.matchPhraseQuery("name", instance.name))
        .get();
		SearchHit[] hitTab = response.getHits().getHits();
		if(hitTab.length != 0) {
			SearchHit hit = hitTab[0];
			String id = hit.getId();
			System.out.println(id);
			DeleteResponse response2 = client.prepareDelete("map", "map", id).get();

		}
		else{
			System.out.println("Aucune map trouve");
		}
	}

}
