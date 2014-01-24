

public class User {
	private String phoneNumber;
	
	public User(String phoneNumber){
		this.phoneNumber = phoneNumber;
	}
	
	public String getPhoneNumber(){
		return "+995598374203";
	}
	
	@Override
	public String toString(){
		return phoneNumber;
	}
	
}
