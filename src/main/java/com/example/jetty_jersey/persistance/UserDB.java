package com.example.jetty_jersey.persistance;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.UpdateByQueryAction;
import org.elasticsearch.index.reindex.UpdateByQueryRequestBuilder;
import org.elasticsearch.search.SearchHit;

import com.example.jetty_jersey.model.User;

public class UserDB implements UserDAO {

	public static void authoriseModifUser(){
		TransportClient client = Bdd.connectionToBD();
		UpdateByQueryRequestBuilder updateByQuery = UpdateByQueryAction.INSTANCE.newRequestBuilder(client);
		updateByQuery.source("user").abortOnVersionConflict(false);
		BulkByScrollResponse response = updateByQuery.get();
	}

	public boolean checkUser(User instance) {
		TransportClient client = Bdd.connectionToBD();
		
		SearchResponse response = client.prepareSearch("user")
        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
        .setQuery(QueryBuilders.matchPhraseQuery("login", instance.login))
        .get();
				 
		SearchHit[] hitTab = response.getHits().getHits();
		if(hitTab.length != 0) {
			SearchHit hit = hitTab[0];
			String password = (String) hit.getSourceAsMap().get("password");
			if(password.equals(instance.password)) {
				return true;
			}
		}
		return false;
	}

	public boolean addUser(User instance) {
		User inst =  instance;
		TransportClient client = Bdd.connectionToBD();
		//UserDB.authoriseModifUser();
		//Map<String, Object> json = new HashMap<String, Object>();
		if(checkUser(instance)) {
			return false;
		}
		IndexResponse response = null;
		try {
			response = client.prepareIndex("user","name")
			        .setSource(jsonBuilder()
			                    .startObject()
			                        .field("login", inst.login)
			                        .field("password", inst.password)
			                    .endObject()
			                  )
			        .get();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void editUser(int id, User instance) {
		TransportClient client = Bdd.connectionToBD();
		UpdateRequest updateRequest = new UpdateRequest();
		updateRequest.index("user");
		updateRequest.type("name");
		updateRequest.id(id+"");
		try {
			updateRequest.doc(jsonBuilder()
			        .startObject()
			            .field("login", instance.login)
						.field("password", instance.password)
			        .endObject());
			client.update(updateRequest).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void deleteUser(User instance) {
		// TODO Auto-generated method stub

	}

}
