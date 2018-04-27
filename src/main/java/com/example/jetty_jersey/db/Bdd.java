package com.example.jetty_jersey.db;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.elasticsearch.common.xcontent.XContentFactory.*;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.transport.client.PreBuiltTransportClient;


public class Bdd {

	private static TransportClient client = null;

	@SuppressWarnings("resource")
	public synchronized static TransportClient connectionToBD(){
		if(Bdd.client == null) {
			System.out.println("Connection DB ");
			try {
				Settings settings = Settings.builder()
				       .put("client.transport.sniff", true).build();
				Bdd.client = new PreBuiltTransportClient(settings)
						.addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300));
				//		.addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300));

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(Bdd.client == null) {
			System.out.println("La connexion a �chouee");
			System.exit(1);
		}
		return Bdd.client;

	}
	
	public static void disconnect() {
		Bdd.client.close();
	}
	
	public static void test(String[] args) {
		// TODO Auto-generated method stub
		TransportClient client = connectionToBD();
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("user", "kimchy");
		json.put("postDate", new Date());
		json.put("message", "trying out Elasticsearch");
		/**
		 * Creer un JSON :
		 */

		try {
			XContentBuilder builder = XContentFactory.jsonBuilder().startObject()
				        .field("user", "kimchy")
				        .field("postDate", new Date())
				        .field("message", "trying out Elasticsearch")
				    .endObject();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		IndexResponse response = null;
		try {
			response = client.prepareIndex("twitter", "tweet", "1")
			        .setSource(jsonBuilder()
			                    .startObject()
			                        .field("user", "kimchy")
			                        .field("postDate", new Date())
			                        .field("message", "trying out Elasticsearch")
			                    .endObject()
			                  )
			        .get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response = client.prepareIndex("twitter", "tweet")
		        .setSource(json, XContentType.JSON)
		        .get();
		// Index name
		String _index = response.getIndex();
		// Type name
		String _type = response.getType();
		// Document ID (generated or not)
		String _id = response.getId();
		// Version (if it's the first time you index this document, you will get: 1)
		long _version = response.getVersion();
		// status has stored current instance statement
		RestStatus status = response.status();

		System.out.println(_index + _type + _id + _version );
	}
}
