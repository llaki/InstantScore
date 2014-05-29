package model;
import java.util.*;

public class MatchInfo {
	private Team home, away;
	
	private String id;
	
	private HashSet<User> setInterestedUsers = new HashSet<User>();
	
	private Score score;
	
	private Date date;
	
	private Tournament tournament;
	
	private Time timeStatus;
	
	private boolean endedInPenalties = false, firstTeamWonInPenalties = false;
	
	public static void copyUsers(MatchInfo from, MatchInfo to){
		to.setInterestedUsersSet(from.getSetOfUsers());
	}
	
	public HashSet<User> getSetOfUsers(){
		return setInterestedUsers;
	}
	
	/**
	 * Works in O(1). Just copies the reference to a set.
	 * @param users the set of interested users
	 */
	public void setInterestedUsersSet(HashSet<User> users){
		setInterestedUsers = users;
	}
	
	public Iterator<User> getUsers(){
		return setInterestedUsers.iterator();
	}
	
	private static String makeKeyFromMatch(MatchInfo match){
		return match.getHomeTeam()+" vs "+match.getAwayTeam();
	}
	
	public String getId(){
		return id;
	}
	
	public void addInterestedUser(User u){
		setInterestedUsers.add(u);
	}
	
	public MatchInfo(Team homeTeam, Team awayTeam, Score matchScore, Date date, Tournament tournament, Time timeStatus,
			boolean endedInPenalties, boolean firstTeamWonInPenalties){
		home = homeTeam;
		away = awayTeam;
		score = matchScore;
		id = makeKeyFromMatch(this);
		setInterestedUsers = new HashSet<User>();
		this.date = date;
		this.tournament = tournament;
		this.timeStatus = timeStatus;
		this.endedInPenalties = endedInPenalties;
		this.firstTeamWonInPenalties = firstTeamWonInPenalties;
	}
	
	public static MatchInfo returnEmptyMatchInfo(){
		return new MatchInfo(new Team(""), new Team(""), new Score(""), new Date(""), new Tournament(""), new Time(""), false, false);
	}
	
	public Team getHomeTeam(){
		return home;
	}
	
	public Team getAwayTeam(){
		return away;
	}
	
	public Score getMatchScore(){
		return score;
	}
	
	public boolean isAlreadyGoingOrFinished(){
		for(char ch='0'; ch<='9'; ch++) {
			if(score.toString().indexOf(ch)!=-1) return true;
		}
		return false;
	}
	
	public Tournament getTournament(){
		return tournament;
	}
	
	public Date getMatchDate(){
		return date;
	}
	
	public Time getTimeStatus(){
		return timeStatus;
	}
	
	public boolean firstTeamWonInPenalties() {
		return firstTeamWonInPenalties;
	}
	
	public boolean finishedInPenalties() {
		return endedInPenalties;
	}

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
	//	sb.append(getTournament()+"\n");
		sb.append(getMatchDate()+" "+getTimeStatus()+"    "+getHomeTeam()+" "+getMatchScore()+" "+getAwayTeam());
		return sb.toString();
	}
	
	public static final Comparator<MatchInfo> compare = new Comparator<MatchInfo>() {

		@Override
		public int compare(MatchInfo o1, MatchInfo o2) {
			return Time.compareMatchTimes.compare(o1.getTimeStatus(), o2.getTimeStatus());
		}
	};
	
}
