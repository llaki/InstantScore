package servlets;
import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.naming.resources.jndi.Handler;

import securitycode.CodeGenerator;
import livescore.CountryScores;
import livescore.CountryUrl;
import livescore.UrlConstants;
import model.AllGoingMatches;
import model.MatchInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

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
				HashMap<String, ArrayList<MatchInfo>> map = new HashMap<String, ArrayList<MatchInfo>>();
				long lastTimeOfChecking = 0;
				while (true) {
					long currentTime = System.currentTimeMillis();
					boolean shouldCheckFutures = (currentTime - lastTimeOfChecking > NUM_MILISECONDS_IN_TWELVE_HOURS);
					ArrayList<MatchInfo> allActiveMatches = new ArrayList<MatchInfo>();
					try {
						CountryScores cs = new CountryScores();
						if(firstTime) {
							firstTime = false;
							CodeGenerator.fillMapFromFile(new File(CodeGenerator.CODES_FILE_NAME));
						}
						ArrayList<MatchInfo> matchesList =
								cs.parseUrlForCountryScoresAndGetActiveMatches(new CountryUrl("Live", "http://www.livescore.com/soccer"), false);
						map.put("Live", matchesList);
						allActiveMatches.addAll(matchesList);
						for(CountryUrl url : UrlConstants.COUNTRIES_AND_URLS) {
							if(shouldCheckFutures || firstTime || !map.containsKey(url.getCountryName())) {
								ArrayList<MatchInfo> list = cs.parseUrlForCountryScoresAndGetActiveMatches(url, true);
								allActiveMatches.addAll(list);
								map.put(url.getCountryName(), list);
							}
							else {
								allActiveMatches.addAll(map.get(url.getCountryName()));
							}
						}
						AllGoingMatches.changesToGoingMatches(allActiveMatches);
						Thread.sleep(DELAY_BETWEEN_REFRESHES);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}
		}).start();
	}
	
	private static final long NUM_MILISECONDS_IN_TWELVE_HOURS = 3600L*24L*1000L;
	
	// the delay between consecutive refreshes in milliseconds
	private static final int DELAY_BETWEEN_REFRESHES = 2000;

}
