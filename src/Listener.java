import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class Listener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						LivescoreConnection.refreshURLAndWriteIntoFile();
						LivescoreConnection.getMatchInfosFromLivescore();
						Thread.currentThread().sleep(2000);
//						try {
//							MatchesFileReadUtils.fakeUpdateViaNewList();
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}
		});
		t.start();
	}

}
