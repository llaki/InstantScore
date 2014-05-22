package model;


public class MatchInfoBuilder {
	
	private Date date;
	
	private Time timeStatus;
	
	private Team home, away;
	
	private Score score;
	
	private Tournament tournament;
	
	private boolean endedInPenalties = false, firstWonInPenalties = false;
	
	/**
	 * Empty constructor here
	 */
	public MatchInfoBuilder(){
		
	}
	
	public void setHomeTeam(String hostTeamName){
		home = new Team(hostTeamName);
	}
	
	public void setAwayTeam(String visitorTeamName){
		away = new Team(visitorTeamName);
	}
	
	public void setScore(String score){
		this.score = new Score(score);
	}
	
	public void setDate(String date){
		this.date = new Date(date);
	}
	
	public void setMatchTime(String time){
		this.timeStatus = new Time(time);
	}
	
	public void setTournament(String tournament){
		this.tournament = new Tournament(tournament);
	}
	
	public MatchInfo toMatchInfo(){
		return new MatchInfo(home, away, score, date, tournament, timeStatus, endedInPenalties, firstWonInPenalties);
	}
	
	public void setPenaltiesCase(boolean firstWon){
		endedInPenalties = true;
		firstWonInPenalties = firstWon;
	}
	
}
