package model;

import java.util.Comparator;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Iterator;

public class User {
	private String phoneNumber;
	
	private HashSet<String> lastSubscribedList = new HashSet<String>();
	
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
	
	public void setNewSubscription (ArrayList<String> matchIds) {
		HashSet<String> currentSet = new HashSet<String>();
		currentSet.addAll(matchIds);
		synchronized (lastSubscribedList) {
			Iterator<String> iter = lastSubscribedList.iterator();
			while(iter.hasNext()) {
				String current = iter.next();
				if(!currentSet.contains(current)) {
					lastSubscribedList.remove(current);
					MatchInfo match = AllGoingMatches.getExistingMatchObject(current);
					match.removeSubscriptionForUser(this);
				}
			}
			lastSubscribedList.addAll(matchIds);
		}
	}
	
	public static Comparator<User> compareUsers = new Comparator<User>() {

		@Override
		public int compare(User o1, User o2) {
			return o1.getPhoneNumber().compareTo(o2.getPhoneNumber());
		}
		
	};
	
	public static final User TEST_USER = UserFactory.createUser("+995598374203");
	
}
