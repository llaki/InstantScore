package model;
import java.util.Comparator;

public class User {
	private String phoneNumber;
	
	public User(String phoneNumber){
		this.phoneNumber = phoneNumber;
	}
	
	public String getPhoneNumber(){
		return phoneNumber;
	}
	
	@Override
	public String toString(){
		return phoneNumber;
	}
	
	public static Comparator<User> compareUsers = new Comparator<User>() {

		@Override
		public int compare(User o1, User o2) {
			return o1.getPhoneNumber().compareTo(o2.getPhoneNumber());
		}
		
	};
	
	public static final User TEST_USER = UserFactory.createUser("+995598374203");
	
}
