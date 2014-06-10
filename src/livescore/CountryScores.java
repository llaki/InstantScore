package livescore;

import model.*;
import java.util.*;

public class CountryScores {
	
	
	public ArrayList<MatchInfo> parseUrlForCountryScoresAndGetActiveMatches(CountryUrl countryUrl, boolean filter) {
		String countryName = countryUrl.getCountryName(), url = countryUrl.getUrl();
		LivescoreConnection livescoreCon = new LivescoreConnection(url, "scores."+countryName, !filter);
		try {
			livescoreCon.refreshURLAndWriteIntoFile();
			return livescoreCon.getMatchInfosFromLivescore();
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return new ArrayList<MatchInfo>();
		}
	}
	
	
}
