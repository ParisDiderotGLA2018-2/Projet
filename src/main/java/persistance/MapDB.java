package persistance;

import java.util.ArrayList;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.document.DocumentField;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.UpdateByQueryAction;
import org.elasticsearch.index.reindex.UpdateByQueryRequestBuilder;
import org.elasticsearch.search.SearchHits;

import com.example.jetty_jersey.model.User;

import com.example.jetty_jersey.model.Location;
import com.example.jetty_jersey.model.MMap;
import com.example.jetty_jersey.model.User;

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
	public void addLocation(Location l) throws IOException{
		TransportClient client = Bdd.connectionToBD();
		IndexResponse response = client.prepareIndex("map", "map")
		        .setSource(jsonBuilder()
		                    .startObject()
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
	
	public  MMap[] getMaps(User u) {
		TransportClient client = Bdd.connectionToBD();
		SearchRequestBuilder srb2 = client
			    .prepareSearch().setQuery(QueryBuilders.matchQuery("creator", u.login )).setSize(1);

		MultiSearchResponse sr = client.prepareMultiSearch()
				.add(srb2)
				.get();
		for(MultiSearchResponse.Item item : sr.getResponses()){
			SearchResponse response = item.getResponse();
			SearchHits hits = response.getHits();
			for (int i = 0; i < hits.getTotalHits(); i++) {
				Map<String, DocumentField> reponseFields = hits.getAt(i).getFields();
				DocumentField d = reponseFields.get("place");
				String place = d.getValue();
				System.out.println(item.toString());
			
			}
		}
		return null;
	}

	public String[] getListMapName(User U) {
		// TODO Auto-generated method stub
		return null;
	}

	public MMap InfoLocation(String login, String mapName, String mapPlace) {
		// TODO Auto-generated method stub
		return null;
	}


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
			                        .field("user", instance.creator.login)
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
	public void deletePlace(MMap instance, Location l) {
		// TODO Auto-generated method stub

	}

	public void deleteMap(MMap instance) {
		// TODO Auto-generated method stub

	}

}
