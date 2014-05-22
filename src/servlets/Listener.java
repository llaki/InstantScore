package servlets;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import livescore.CountryScores;
import livescore.CountryUrl;
import livescore.UrlConstants;

public class Listener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						CountryScores cs = new CountryScores();
						cs.parseUrlForCountryScores(new CountryUrl("Live", "http://www.livescore.com"), false);
						for(CountryUrl countryUrl : UrlConstants.COUNTRIES_AND_URLS) {
							cs.parseUrlForCountryScores(countryUrl, true);
						}
						Thread.sleep(DELAY_BETWEEN_REFRESHES);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}
		}).start();
	}
	
	// the delay between consecutive refreshes in milliseconds
	private static final int DELAY_BETWEEN_REFRESHES = 2000;

}
