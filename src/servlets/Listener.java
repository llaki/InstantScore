package servlets;
import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import securitycode.CodeGenerator;
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
				boolean firstTime = true;
				while (true) {
					try {
						CountryScores cs = new CountryScores();
						if(firstTime) {
							CodeGenerator.fillMapFromFile(new File(CodeGenerator.CODES_FILE_NAME));
						}
						cs.parseUrlForCountryScores(new CountryUrl("Live", "http://www.livescore.com"), false);
						for(CountryUrl countryUrl : UrlConstants.COUNTRIES_AND_URLS) {
							cs.parseUrlForCountryScores(countryUrl, true);
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
