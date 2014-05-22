package parseutils;
import java.io.*;
import java.util.*;

import model.MatchInfo;
import model.MatchInfoBuilder;

public class MatchesFileReadUtils {

	public static ArrayList<MatchInfo> oldList, newList;
	
	private static boolean wonInPenalties(String line) {
		return line.indexOf("*")!=-1;
	}
	
	private static String retrieveTeamName(String line) {
		int index = line.indexOf("*");
		if(index==-1) return line;
		if(index==0) return line;
		line = line.substring(0, index);
		while(line.charAt(line.length()-1)==' ' && line.length()>1) {
			line = line.substring(0, line.length()-1);
		}
		return line;
	}
	
	public static ArrayList<MatchInfo> readMatchInfosFromFile(String fileName) throws IOException {
		ArrayList<MatchInfo> list = new ArrayList<MatchInfo>();
		BufferedReader rd = new BufferedReader(new FileReader(fileName));
		int numTournaments = Integer.parseInt(rd.readLine());
		StringTokenizer st;
		for(int i=0; i<numTournaments; i++){
			st = new StringTokenizer(rd.readLine());
			int numMatches = Integer.parseInt(st.nextToken());
			String tournamentName = rd.readLine();
			for(int j=0; j<numMatches; j++){
				MatchInfoBuilder mib = new MatchInfoBuilder();
				
				rd.readLine();
				String date = rd.readLine();
				String time = rd.readLine();
				String home = rd.readLine();
				String score = rd.readLine();
				String away = rd.readLine();
				
				
				boolean homeTeamWonInPenalties = wonInPenalties(home);
				boolean penaltiesCase = homeTeamWonInPenalties;
				if(penaltiesCase){
					mib.setPenaltiesCase(homeTeamWonInPenalties);
				}
				home = retrieveTeamName(home);
				away = retrieveTeamName(away);
				mib.setDate(date);
				mib.setHomeTeam(home);
				mib.setAwayTeam(away);
				mib.setMatchTime(time);
				mib.setScore(score);
				mib.setTournament(tournamentName);
				list.add(mib.toMatchInfo());
			}
		}
		rd.close();
		return list;
	}
	
}
