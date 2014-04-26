import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.*;

public class LivescoreConnection {
	public static void main(String[] args) throws Exception {
		while(true){
			System.out.println("new refresh...");
			refreshURLAndWriteIntoFile();
			getMatchInfosFromLivescore();
		}
	}
	
	public static void refreshURLAndWriteIntoFile() throws Exception {
		System.out.println("writing...");
		String htmlPage = getHtml(LIVESCORE_URL);
		ArrayList<HtmlNode> nodeList = ParseUtils.getNodesForHtmlPage(htmlPage);
		PrintWriter out = new PrintWriter(new FileWriter("console"));
		for(int i=0; i<nodeList.size(); i++){
			out.println("start printing...");
			out.println("###"+nodeList.get(i));
			out.println("finish printing");
		}
		System.out.println("done writing");
		out.flush();
		out.close();
	}
	
	public static void getMatchInfosFromLivescore() throws Exception {
		BufferedReader rd = new BufferedReader(new FileReader("console"));
		String line;
		ArrayList<MatchInfo> matches = new ArrayList<MatchInfo>();
		while((line = rd.readLine()) != null){
			MatchInfoBuilder matchBuilder = new MatchInfoBuilder();
			if(line.equals("<strong>")) {
				String nextLine = rd.readLine();
				if(nextLine==null || nextLine.length()==0){
					break;
				}
				String countryName = nextLine;
				while(true){
					String currentLine = rd.readLine();
					if(currentLine==null){
						break;
					}
					if(ParseUtils.startsWithAHref(currentLine)){
						break;
					}
				}
				String championshipName = rd.readLine();
				if(championshipName==null) break;
				
				matchBuilder.setTournament(countryName+" - "+championshipName);
				
				boolean tournamentEnded = false;
				while(true){ // search for matches
					
					boolean dateTag = false;
					while(true){
						String currentLine = rd.readLine();
						if(currentLine==null) break;
						if(ParseUtils.isEndOfCurrentTournamentInfo(currentLine)){
							tournamentEnded = true;
							break;
						}
						if(ParseUtils.isDateTag(currentLine)) {
							dateTag = true;
							break;
						}
						if(ParseUtils.isTimeTag(currentLine)){
							break;
						}
					}
					if(tournamentEnded) break;
					if(dateTag){
						String date = rd.readLine();
						if(date==null) break;
						matchBuilder.setDate(date);
						while(true){
							String currentLine = rd.readLine();
							if(currentLine==null) break;
							if(ParseUtils.isEndOfCurrentTournamentInfo(currentLine)){
								tournamentEnded = true;
								break;
							}
							if(ParseUtils.isTimeTag(currentLine)){
								break;
							}
						}
						
					}
					
					String timeInfo = rd.readLine();
					if(timeInfo==null) break;
					if(ParseUtils.isImageTag(timeInfo))
						timeInfo = rd.readLine();
					matchBuilder.setMatchTime(ParseUtils.parseTimeInfo(timeInfo));
		
					if(tournamentEnded) break;
					
					while(true){
						String currentLine = rd.readLine();
						if(currentLine==null) break;
						if(ParseUtils.isHomeTeamTag(currentLine)){
							break;
						}
					}
					String homeTeam = "";
					while(true){
						homeTeam = rd.readLine();
						if(homeTeam==null) break;
						if(homeTeam.length()<2) continue;
						break;
					}
					matchBuilder.setHomeTeam(homeTeam);
					
					while(true){
						String currentLine = rd.readLine();
						if(currentLine==null) break;
						if(ParseUtils.isScoreTag(currentLine)){
							break;
						}
					}
					String score = "";
					while(true){
						score = rd.readLine();
						if(score==null) break;
						if(score.length()<2) continue;
						if(ParseUtils.startsWithAHref(score)) continue;
						break;
					}
					matchBuilder.setScore(score);
					
					while(true){
						String currentLine = rd.readLine();
						if(currentLine==null) break;
						if(ParseUtils.isAwayTeamTag(currentLine)){
							break;
						}
					}
					String awayTeam = "";
					while(true){
						awayTeam = rd.readLine();
						if(awayTeam==null) break;
						if(awayTeam.length()<2) continue;
						break;
					}
					matchBuilder.setAwayTeam(awayTeam);
					matches.add(matchBuilder.toMatchInfo());
				}
			}
		}
		ArrayList<TournamentWithMatches> listTwms = AllMatches.groupByTournaments(matches);
		writeMatchInfosIntoFile(listTwms);
		
		AllGoingMatches.changesToGoingMatches(matches);
		rd.close();
	}
	
	public static void writeMatchInfosIntoFile(ArrayList<TournamentWithMatches> list) throws Exception {
		System.out.println("scores");
		PrintWriter pw = new PrintWriter(new FileWriter("/usr/share/tomcat7/scores"));
		int numTournaments = list.size();
		pw.println(numTournaments);
		for(int tourn=0; tourn<numTournaments; tourn++){
			TournamentWithMatches twm = list.get(tourn);
			int numMatches = twm.getAllMatches().size();
			pw.println(numMatches);
			pw.println(twm.getTournament().getTournamentName());
			twm.sortListOfMatches();
			ArrayList<MatchInfo> matches = twm.getAllMatches();
			for(int j=0; j<matches.size(); j++){
				MatchInfo info = matches.get(j);
				pw.println(info.getId());
				pw.println(info.getMatchDate());
				pw.println(info.getTimeStatus());
				pw.println(info.getHomeTeam());
				Score score = info.getMatchScore();
				pw.println(score);
				pw.println(info.getAwayTeam());
			}
		}
		pw.flush();
		pw.close();
	}
			
	public static String getHtml(String url) throws Exception {
		StringBuilder sb = new StringBuilder();
		try {
		    URL myURL = new URL(url);
		    URLConnection myURLConnection = myURL.openConnection();
		    myURLConnection.connect();
		    BufferedReader rd = new BufferedReader(new InputStreamReader(myURLConnection.getInputStream()));
		    boolean first = true;
		    while(true){
		    	String line = rd.readLine();
		    	if(line==null) break;
		    	if(first){
		    		sb.append(line);
		    	}
		    	else{
		    		sb.append("\n "+line);
		    	}
		    	first = false;
		    }
		} 
		catch (MalformedURLException e) {
			System.out.println("malformedUrlException");
		} 
		catch (IOException e) {   
			System.out.println("IOException");
		}
		return sb.toString();
	}
	
	static final String LIVESCORE_URL = "http://www.livescore.com";
	
}
