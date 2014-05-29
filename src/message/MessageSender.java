package message;
import java.util.Iterator;

import twillio.Twilio;
import model.MatchInfo;
import model.User;

import com.twilio.sdk.TwilioRestException;

public class MessageSender {
	
	//private static int MESSAGE_ID = 1;
	
	public static void sendAllMessagesForMatch(String message, MatchInfo match){
		System.err.println(message);
		System.out.println("The id of the match for which the messages are sent : " + match.getId());
		Iterator<User> users = match.getUsers();
		while(users.hasNext()) {
			User currentUser = users.next();
	//		send(currentUser, message+" ID = "+MESSAGE_ID);
	//		MESSAGE_ID++;
			send(currentUser, message);
			System.out.println("sending message to the user "+currentUser);
		}
	}
	
	public static void send(User user, String message){
		System.out.println("Sending to "+user.getPhoneNumber()+" : "+message);
		Twilio tw = new Twilio();
		try {
			tw.send(user.getPhoneNumber(), message);
		} catch (TwilioRestException e) {
			e.printStackTrace();
		}
	}
	
}
