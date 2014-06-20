package message;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import twillio.Twilio;
import model.MatchInfo;
import model.User;
import model.UserFactory;

import com.twilio.sdk.TwilioRestException;

public class MessageSender {
	private static final Logger LOGGER = Logger.getLogger(MessageSender.class.getName());
	//private static int MESSAGE_ID = 1;
	
	public static void sendAllMessagesForMatch(String message, MatchInfo match){
		LOGGER.info(message);
		LOGGER.log(Level.FINEST, "The id of the match for which the messages are sent : " + match.getId());
		Iterator<User> users = match.getUsers();
		while(users.hasNext()) {
			User currentUser = users.next();
	//		send(currentUser, message+" ID = "+MESSAGE_ID);
	//		MESSAGE_ID++;
			send(currentUser, message);
			LOGGER.log(Level.FINEST, "message sent to the user "+currentUser);
		}
	}
	
	public static void send(User user, String message){
		LOGGER.log(Level.FINEST, "Sending to "+user.getPhoneNumber()+" : "+message);
		Twilio tw = new Twilio();
		try {
			tw.send(user.getPhoneNumber(), message);
		} catch (TwilioRestException e) {
			e.printStackTrace();
		}
	}
	
}
