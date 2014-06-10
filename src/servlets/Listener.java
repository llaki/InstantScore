package servlets;
import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import securitycode.CodeGenerator;
import livescore.CountryScores;
import livescore.CountryUrl;
import livescore.UrlConstants;
import model.MatchInfo;
import java.util.ArrayList;

public class Listener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				boolean firstTime = true;
				while (true) {
					ArrayList<MatchInfo> allActiveMatches = new ArrayList<MatchInfo>();
					try {
						CountryScores cs = new CountryScores();
						if(firstTime) {
							CodeGenerator.fillMapFromFile(new File(CodeGenerator.CODES_FILE_NAME));
						}
						ArrayList<MatchInfo> matchesList =
								cs.parseUrlForCountryScoresAndGetActiveMatches(new CountryUrl("Live", "http://www.livescore.com"), false);
						allActiveMatches.addAll(matchesList);
						for(CountryUrl countryUrl : UrlConstants.COUNTRIES_AND_URLS) {
							allActiveMatches.addAll(cs.parseUrlForCountryScoresAndGetActiveMatches(countryUrl, true));
						}
						Thread.sleep(DELAY_BETWEEN_REFRESHES);
					} catch (Exception e) {
						e.printStackTrace();
					}
					firstTime = false;
				}

			}
		}).start();
	}
	
	// the delay between consecutive refreshes in milliseconds
	private static final int DELAY_BETWEEN_REFRESHES = 2000;

}
