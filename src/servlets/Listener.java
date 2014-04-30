package servlets;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import livescore.LivescoreConnection;

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
						LivescoreConnection.refreshURLAndWriteIntoFile();
						LivescoreConnection.getMatchInfosFromLivescore();
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
