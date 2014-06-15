package model;
/**
 * This class keeps track of the matches that are either going now, or they're going to start now, or they have finished not long ago.
 */

import message.*;
import java.util.*;

public class AllGoingMatches {
	
	private static boolean firstRun = true;
	
	private static HashMap<String, MatchInfo> map = new HashMap<String, MatchInfo>();
	
	public static MatchInfo getSameMatchObject(MatchInfo match) throws Exception {
		String key = makeKeyFromMatch(match);
		if(!map.containsKey(key)){
			throw new Exception("Exception: The corresponding match doesn't exist.");
		}
		return map.get(key);
	}
	
	public static MatchInfo getExistingMatchObject(String matchId){
		System.out.println("Requested existing object for match id - " + matchId);
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
		System.out.println("Printing map...");
		for(String ids : map.keySet()) {
			System.out.println("Match id : " + ids);
		}
	}
	
	public static void changesToGoingMatches(ArrayList<MatchInfo> allMatches) throws Exception {
		HashMap<String, MatchInfo> newMap = new HashMap<String, MatchInfo>();
		for(MatchInfo match : allMatches){
			if(!matchHasBeenBefore(match)){ // newly added match
				newMap.put(makeKeyFromMatch(match), match);
				HashSet<User> setUsers = new HashSet<User>();
				match.setInterestedUsersSet(setUsers);
			}
			else{
				MatchInfo oldMatchingMatch = getSameMatchObject(match);
				MatchUpdate update = MatchUpdate.matchHasBeenUpdated(oldMatchingMatch, match);
				if(update.hasBeenUpdated()){
					MatchInfo.copyUsers(oldMatchingMatch, match);
					newMap.put(makeKeyFromMatch(match), match);
					MessageSender.sendAllMessagesForMatch(MessageGenerator.generateMessageTextViaUpdate(update), match);
				}
				else {
					MatchInfo.copyUsers(oldMatchingMatch, match);
					newMap.put(makeKeyFromMatch(match), match);
				}
			}
		}
		map = newMap;
		if(firstRun) {
			printMap();
			firstRun = false;
		}
	}
	
	
	
}
