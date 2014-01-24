import java.util.*;

public class AllMatches {
	
	public static ArrayList<TournamentWithMatches> groupByTournaments(ArrayList<MatchInfo> matches){
		ArrayList<TournamentWithMatches> groups = new ArrayList<TournamentWithMatches>();
		HashMap<String, ArrayList<MatchInfo>> map = new HashMap<String, ArrayList<MatchInfo>>();
		for(int i=0; i<matches.size(); i++){
			MatchInfo matchInfo = matches.get(i);
			String tournamentName = matchInfo.getTournament().getTournamentName();
			if(!map.containsKey(tournamentName))
				map.put(tournamentName, new ArrayList<MatchInfo>());
			map.get(tournamentName).add(matchInfo);
		}
		Iterator<String> iterator = map.keySet().iterator();
		while(iterator.hasNext()){
			String tournamentInfo = iterator.next();
			TournamentWithMatches twm = new TournamentWithMatches(new Tournament(tournamentInfo), map.get(tournamentInfo));
			twm.sortListOfMatches();
			groups.add(twm);
		}
		return groups;
	}
	
}
