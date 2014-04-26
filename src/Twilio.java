// Install the Java helper library from twilio.com/docs/java/install
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;

public class Twilio {
	// Find your Account Sid and Token at twilio.com/user/account
	public static final String ACCOUNT_SID = "AC00a7386511834a59fb93d4697b6c545e";
	public static final String AUTH_TOKEN = "bd13dfcd9d9caa335894995b2bf869c6";
	private TwilioRestClient client;
	
	public Twilio(){
		client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
	}
	
	public void send(String to, String txt) throws TwilioRestException {
		// Build a filter for the MessageList
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("Body", txt));
		params.add(new BasicNameValuePair("To", to));
		params.add(new BasicNameValuePair("From", "+12315772575"));

		MessageFactory messageFactory = client.getAccount().getMessageFactory();
		messageFactory.create(params);
//		System.out.println(message.getSid());
	}
}
