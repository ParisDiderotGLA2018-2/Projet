package com.example.jetty_jersey.db;

import java.io.IOException;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.example.jetty_jersey.model.Location;
import com.example.jetty_jersey.model.MMap;
import com.example.jetty_jersey.model.User;

public class Test {
	static User U = new User("bla", "blo");
	static MapDB db = new MapDB();
    private static final Logger logger = LogManager.getLogger(Test.class);

	public static void main(String [] args) {
		logger.debug("Test app start");
		// insert_user_test();
		// update_user_test();
		// insert_map_test();
		// get_map_test();
		// delete_map_test();
		// insert_location_test();
		logger.debug("end");
		// Bdd.disconnect();
	}
	
	public static void get_map_test() {
		System.out.println("Test get map");
		MMap  [] m= db.getMaps(U.login);
		System.out.println(m[0].name);

	}
	
	public static void delete_map_test() {
		System.out.println("Test delete map");
		db.deleteMap(U.login);
		System.out.println("fin de test");

	}
	
	public static void insert_location_test() {
		Location loc1 = new Location("Eiffel Tower", 48.8583905296204,2.2944259643554688, "IKAN", "the view from the topis just breathtaking", "img/paris1.jpg");
		Location loc2 = new Location("...IKHAN...", 48.87386089807715,2.294940948486328, "IKAN", ".............................", "img/paris2.jpg");
		Location loc3 = new Location("blablo", 48.8386053,2.378623100000027, "IKAN", "the view from the topis just breathtaking", "img/plage1.jpg");
		Location loc4 = new Location("...blablo...", 41.38773117668287,2.201385498046875, "IKAN", ".............................", "img/plage2.jpg");

		try {
			db.addLocation("3", loc1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void insert_map_test() {
		MMap [] mp= {new MMap("PARIS", U.login, "public"),new MMap("IFNI", U.login, "public")};
		System.out.println("add map");
		db.addMap(mp[1]);
	}
	
	public static void insert_user_test() {
		System.out.println("Test insert user");
		UserDB u = new UserDB();
		u.addUser(new User("tata", "toto"));
	}
	
	public static void update_user_test () {
		System.out.println("Test update user");
		UserDB u = new UserDB();
		u.editUser(2, new User("tata2", "toto"));
	}
}
