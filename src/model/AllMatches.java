package model;
import java.util.*;

public class AllMatches {
	
	public static ArrayList<TournamentWithMatches> groupByTournaments(ArrayList<MatchInfo> matches){
		ArrayList<TournamentWithMatches> groups = new ArrayList<TournamentWithMatches>();
		HashMap<String, ArrayList<MatchInfo>> map = new HashMap<String, ArrayList<MatchInfo>>();
		HashMap<String, Integer> occurenceMap = new HashMap<String, Integer>();
		int currentTournamentIndex = 0;
		for(int i=0; i<matches.size(); i++){
			MatchInfo matchInfo = matches.get(i);
			String tournamentName = matchInfo.getTournament().getTournamentName();
			if(!occurenceMap.containsKey(tournamentName)){
				occurenceMap.put(tournamentName, currentTournamentIndex++);
			}
			if(!map.containsKey(tournamentName))
				map.put(tournamentName, new ArrayList<MatchInfo>());
			map.get(tournamentName).add(matchInfo);
		}
		Iterator<String> iterator = map.keySet().iterator();
		ArrayList<TournamentWithPriorities> tournsWithPriorities = new ArrayList<TournamentWithPriorities>();
		while(iterator.hasNext()){
			String tournamentInfo = iterator.next();
			TournamentWithMatches twm = new TournamentWithMatches(new Tournament(tournamentInfo), map.get(tournamentInfo));
			twm.sortListOfMatches();
			tournsWithPriorities.add(new TournamentWithPriorities(twm, occurenceMap.get(twm.getTournament().getTournamentName())));
		}
		Collections.sort(tournsWithPriorities, TournamentWithPriorities.compareTournamentsViaPriorities);
		for(int i=0; i<tournsWithPriorities.size(); i++){
			groups.add(tournsWithPriorities.get(i).getTournamentWithMatches());
		}
		return groups;
	}
	
}

class TournamentWithPriorities{
	
	private TournamentWithMatches twm;
	
	private int priority;
	
	public TournamentWithPriorities(TournamentWithMatches twm, int priority){
		this.twm = twm;
		this.priority = priority;
	}
	
	public TournamentWithMatches getTournamentWithMatches(){
		return twm;
	}
	
	public int getPriority(){
		return priority;
	}
	
	public static Comparator<TournamentWithPriorities> compareTournamentsViaPriorities = new Comparator<TournamentWithPriorities>() {
		
		@Override
		public int compare(TournamentWithPriorities arg0, TournamentWithPriorities arg1) {
			return arg0.getPriority()!=arg1.getPriority() ? arg0.getPriority() - arg1.getPriority() : arg0.hashCode() - arg1.hashCode();
		}
	};
	
}