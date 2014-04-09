

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
	
	public static final User TEST_USER = new User("+995598374203");
	
}
