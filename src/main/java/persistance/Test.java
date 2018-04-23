package persistance;

import java.io.IOException;

import com.example.jetty_jersey.model.Location;
import com.example.jetty_jersey.model.MMap;
import com.example.jetty_jersey.model.User;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Test {
	static User U = new User("bla","blo");
	static MapDB db = new MapDB();
    private static final Logger logger = LogManager.getLogger(Test.class);
    
	public static void main(String [] args){
		logger.debug("Test app start");
		//	insert_user_test();
		//	update_user_test();
		insert_map_test();
		get_map_test();
		logger.debug("end");
		Bdd.disconnect();
	}
	public static void get_map_test(){
		System.out.println("Test get map");
		db.getMaps(U);

	}
	public static void insert_location_test(){
		Location loc1 = new Location("Eiffel Tower",48.8583905296204,2.2944259643554688,"IKAN","the view from the topis just breathtaking","img/paris1.jpg");
		Location loc2 = new Location("...IKHAN...",48.87386089807715,2.294940948486328,"IKAN",".............................","img/paris2.jpg");
		Location loc3 = new Location("blablo",48.8386053,2.378623100000027,"IKAN","the view from the topis just breathtaking","img/plage1.jpg");
		Location loc4 = new Location("...blablo...",41.38773117668287,2.201385498046875,"IKAN",".............................","img/plage2.jpg");
	
		try {
			db.addLocation(loc1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void insert_map_test(){
		MMap [] mp= {new MMap("PARIS",U,"public"),new MMap("IFNI",U,"public")};
		System.out.println("add map");
		db.addMap(mp[1]);
	}
	public static void insert_user_test(){
		System.out.println("Test insert user");
		UserDB u = new UserDB();
		u.addUser(new User("tata","toto"));
	}
	public static void update_user_test (){
		System.out.println("Test update user");
		UserDB u = new UserDB();
		u.editUser(2, new User("tata2","toto"));
	}
	
}
