package model;
/**
 * This class keeps track of the matches that are either going now, or they're going to start now, or they have finished not long ago.
 */

import message.*;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AllGoingMatches {
	private static final Logger LOGGER = Logger.getLogger(AllGoingMatches.class.getName());
	
	private static boolean firstRun = true;
	
	private static HashMap<String, MatchInfo> map = new HashMap<String, MatchInfo>();
	
	public static MatchInfo getSameMatchObject(MatchInfo match) throws Exception {
		synchronized (map) {
			String key = makeKeyFromMatch(match);
			return map.get(key);
		}
	}
	
	public static MatchInfo getExistingMatchObject(String matchId){
		LOGGER.log(Level.OFF, "Requested existing object for match id - " + matchId);
		if(map.containsKey(matchId)) return map.get(matchId);
		return null;
	}
	
	public static boolean matchHasBeenBefore(MatchInfo match) {
		String key = makeKeyFromMatch(match);
		return map.containsKey(key);
	}
	
	private static String makeKeyFromMatch(MatchInfo match){
		return match.getHomeTeam()+" vs "+match.getAwayTeam();
	}
	
	public static void printMap() {
		LOGGER.log(Level.OFF, "Printing map...");
		for(String ids : map.keySet()) {
			LOGGER.log(Level.OFF,"Match id : " + ids);
		}
	}
	
	public static void changesToGoingMatches(ArrayList<MatchInfo> allMatches) throws Exception {
	//	HashMap<String, MatchInfo> newMap = new HashMap<String, MatchInfo>();
		for(MatchInfo match : allMatches) {
			if(match==null) {
				continue;
			}
			if(!matchHasBeenBefore(match)) { // newly added match
		//		newMap.put(makeKeyFromMatch(match), match);
				map.put(makeKeyFromMatch(match), match);
				HashSet<User> setUsers = new HashSet<User>();
				match.setInterestedUsersSet(setUsers);
			}
			else {
				MatchInfo oldMatchingMatch = getSameMatchObject(match);
				MatchUpdate update = MatchUpdate.getMatchUpdate(oldMatchingMatch, match);
				if(update.hasBeenUpdated()) {
					MatchInfo.copyUsers(oldMatchingMatch, match);
			//		newMap.put(makeKeyFromMatch(match), match);
					map.put(makeKeyFromMatch(match), match);
					MessageSender.sendAllMessagesForMatch(MessageGenerator.generateMessageTextViaUpdate(update), match);
				}
				else {
					MatchInfo.copyUsers(oldMatchingMatch, match);
		//			newMap.put(makeKeyFromMatch(match), match);
					map.put(makeKeyFromMatch(match), match);
				}
			}
		}
	//	map = newMap;
		if(firstRun) {
			printMap();
			firstRun = false;
		}
		LOGGER.log(Level.FINE, "All going matches reviewed succesfully.");
	}
	
}
