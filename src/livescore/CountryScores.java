package livescore;

public class CountryScores {
	
	
	public void parseUrlForCountryScores(CountryUrl countryUrl, boolean filter) {
		String countryName = countryUrl.getCountryName(), url = countryUrl.getUrl();
		LivescoreConnection livescoreCon = new LivescoreConnection(url, "scores."+countryName, !filter);
		try {
			livescoreCon.refreshURLAndWriteIntoFile();
			livescoreCon.getMatchInfosFromLivescore();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
}
