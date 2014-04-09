import java.util.Iterator;

import com.twilio.sdk.TwilioRestException;

public class MessageSender {
	
	private static int MESSAGE_ID = 1;
	
	public static void sendAllMessagesForMatch(String message, MatchInfo match){
		System.err.println(message);
		System.out.println(match.getId()+"####");
		Iterator<User> users = match.getUsers();
		int numUsers = 0;
		while(users.hasNext()) {
			User currentUser = users.next();
			numUsers++;
			System.out.println(users.hasNext());
			send(currentUser, message+" ID = "+MESSAGE_ID);
			MESSAGE_ID++;
			System.out.println("sending message to the user "+currentUser);
		}
		System.out.println("num users -> "+numUsers);
	}
	
	public static void send(User user, String message){
		System.out.println("Sending to "+user.getPhoneNumber()+": "+message);
		Twilio t = new Twilio();
		try {
			t.send(user.getPhoneNumber(), message);
		} catch (TwilioRestException e) {
			e.printStackTrace();
		}
	}
	
}
