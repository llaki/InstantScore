package model;


public class MatchUserBinder {
	public static void addMatchToUser(User user, MatchInfo match){
		if(match==null){
			System.out.println("match was null");
			return;
		}
		System.out.println(user.getPhoneNumber()+" subscribed to the match - "+match.getId()+".");
		match.addInterestedUser(user);
	}
}
