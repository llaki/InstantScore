package model;
import java.util.ArrayList;
import java.util.Collections;


public class TournamentWithMatches {
	private Tournament tournament;
	
	private ArrayList<MatchInfo> matches;
	
	public TournamentWithMatches(Tournament tournament, ArrayList<MatchInfo> matches){
		this.tournament = tournament;
		this.matches = matches;
	}
	
	public Tournament getTournament(){
		return tournament;
	}
	
	public ArrayList<MatchInfo> getAllMatches(){
		return matches;
	}
	
	public void sortListOfMatches(){
		Collections.sort(matches, MatchInfo.compare);
	}
	
}
