package model;

import java.util.logging.Level;
import java.util.logging.Logger;

import message.MessageSender;


public class MatchUserBinder {
	private static final Logger LOGGER = Logger.getLogger(MatchUserBinder.class.getName());

	public static void addMatchToUser(User user, MatchInfo match){
		if(match==null){
			LOGGER.log(Level.SEVERE, "match was null");
			return;
		}
		LOGGER.log(Level.FINER, user.getPhoneNumber()+" subscribed to the match - "+match.getId()+".");
		match.addInterestedUser(user);
	}
}
