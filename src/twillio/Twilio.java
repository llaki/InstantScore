package twillio;
// Install the Java helper library from twilio.com/docs/java/install
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.naming.resources.jndi.Handler;
import org.eclipse.jdt.internal.compiler.batch.Main;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;

public class Twilio {
	// Find your Account Sid and Token at twilio.com/user/account
	
	// old credentials
	//public static final String ACCOUNT_SID = "AC00a7386511834a59fb93d4697b6c545e";
	//public static final String AUTH_TOKEN = "bd13dfcd9d9caa335894995b2bf869c6";

	
	
	public static final String ACCOUNT_SID = "AC7d19293c11f8c281ef3728f56308ced1";
	//public static final String ACCOUNT_SID = "ACc3f2a167c056e96b1a792fe2e5f19e61"; // chemi testAccountSid 
	public static final String AUTH_TOKEN = "313a2ccaca5f1f2c013100a69106cc43";
	//public static final String AUTH_TOKEN = "8ae403d4931f4fd3cde77e1991a1c710"; // chemi testAccountAuthToken
	public static final String OUR_PHONE_NUMBER = "+12292562076";
	public static final String TEST_PHONE_NUMBER = "15005550006";
	
	private TwilioRestClient client;
	
	public Twilio(){
		client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
	}
	
	public static HashMap<String, String> lastSentMessage = new HashMap<String, String>();
	
	private final static Logger LOGGER = Logger.getLogger(Twilio.class.getName());
	
	public void send(String to, String txt) throws TwilioRestException {
		
		if(lastSentMessage.containsKey(to) && lastSentMessage.get(to).equals(txt)) {
			LOGGER.info("Attempted to send message "+txt+" to the user "+to);
			// We have already sent that message to the user. No need to send the same thing again. This should never happen
			// in expected scenario but this is just to make sure that bug never arises and we won't send multiple messages
			// for no reason.
			return;
		}
		lastSentMessage.put(to, txt);
		
		// Build a filter for the MessageList
		LOGGER.info("sending message '" +txt+"' to " + to);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("Body", txt));
		params.add(new BasicNameValuePair("To", to));
		params.add(new BasicNameValuePair("From", OUR_PHONE_NUMBER));

		MessageFactory messageFactory = client.getAccount().getMessageFactory();
		messageFactory.create(params);
	}
	
	static int numMessages = 10;
	
	public static Thread[] senders = new Thread[numMessages];
	
	public static final boolean[] completed = new boolean[numMessages];
	
	public static void markCompleted(int index) {
		synchronized (completed) {
			completed[index] = true;
		}
	}
	
	public static void main(String[] args) throws TwilioRestException {
		new Twilio().send("+995598374203", "basic sms test");
		if(3>1+1) System.exit(0);
		
		for(int i=0; i<senders.length; i++) {
			senders[i] = new Thread(""+i) {
				@Override
				public void run(){
					System.out.println("I am the thread num "+getName());
					try {
						new Twilio().send("+995598374203", "Empty message by "+getName());
						Twilio.markCompleted(Integer.parseInt(getName()));
					} catch (TwilioRestException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
		}
		for(int i=0; i<senders.length; i++) {
			senders[i].start();
		}
		
		long start = System.currentTimeMillis();
		while(true) {
			boolean fin = true;
			for(int i=0; i<numMessages; i++) {
				synchronized (completed) {
					if(!completed[i]) {
						fin = false;
						break;
					}
				}
			}
			if(fin) {
				break;
			}
		}
		long fin = System.currentTimeMillis();
		System.out.println((fin-start)*0.001+" secs have passed");
	}
	
}
