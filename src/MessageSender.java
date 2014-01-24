import java.util.Iterator;

import com.twilio.sdk.TwilioRestException;

public class MessageSender {
	
	public static void sendAllMessagesForMatch(String message, MatchInfo match){
	//	System.out.println("The message sent for the match " +match+":"+message);
		System.err.println(message);
		System.out.println(match.getId()+"####");
		Iterator<User> users = match.getUsers();
		while(users.hasNext()) {
			System.out.println(users.hasNext());
			send(users.next(), message);
		}
	//	System.exit(0);
	}
	
	public static void send(User user, String message){
		// TODO 
		System.out.println("sending to "+user.getPhoneNumber()+": "+message);
		Twilio t = new Twilio();
		try {
			t.send("+995598374203", message);
		} catch (TwilioRestException e) {
			e.printStackTrace();
		}
	}
	
}
