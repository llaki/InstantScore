import java.io.*;
import java.util.*;

public class MatchesFileReadUtils {
	public static void main(String[] args) throws IOException {
		oldList = readMatchInfosFromFile("D:\\eclipse_enterprise\\oldScores");
		newList = readMatchInfosFromFile("D:\\eclipse_enterprise\\newScores");
		System.out.println(oldList);
		System.out.println(newList);
	}
	
	public static ArrayList<MatchInfo> oldList, newList;
	
	public static void fakeUpdateViaMatchList() throws Exception {
		AllGoingMatches.changesToGoingMatches(readMatchInfosFromFile("D:\\eclipse_enterprise\\oldScores"));		
	}
	
	public static void fakeUpdateViaNewList() throws Exception {
		AllGoingMatches.changesToGoingMatches(readMatchInfosFromFile("D:\\eclipse_enterprise\\newScores"));		
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
				rd.readLine();
				String date = rd.readLine();
				String time = rd.readLine();
				String home = rd.readLine();
				String score = rd.readLine();
				String away = rd.readLine();
				MatchInfoBuilder mib = new MatchInfoBuilder();
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
