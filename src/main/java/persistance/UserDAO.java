package persistance;
import com.example.jetty_jersey.model.*;

public interface UserDAO {
	    boolean checkUser(User instance);
		boolean addUser(User instance);
		void editUser(int id, User instance);
		void deleteUser(User instance);
}

