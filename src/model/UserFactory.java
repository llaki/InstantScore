package model;
import java.util.HashMap;

public class UserFactory {

	public static User createUser(String phoneNumber) {
		if(phoneNumber==null){
			return null;
		}
		if(userMap.containsKey(phoneNumber)){
			return userMap.get(phoneNumber);
		}
		User user = new User(phoneNumber);
		userMap.put(phoneNumber, user);
		return user;
	}
	
	private static HashMap<String, User> userMap = new HashMap<String, User>();
	
}
