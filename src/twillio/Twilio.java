package twillio;
// Install the Java helper library from twilio.com/docs/java/install
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
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
	
	
	private TwilioRestClient client;
	
	public Twilio(){
		client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
	}
	
	public void send(String to, String txt) throws TwilioRestException {
		// Build a filter for the MessageList
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("Body", txt));
		params.add(new BasicNameValuePair("To", to));
		params.add(new BasicNameValuePair("From", "+12292562076"));
	//	params.add(new BasicNameValuePair("From", "+15005550006")); test phone number

		MessageFactory messageFactory = client.getAccount().getMessageFactory();
		messageFactory.create(params);
	}
	
	public static void main(String[] args) throws TwilioRestException {
		new Twilio().send("+995598374203", "Oh yeah...");
	}
	
}
