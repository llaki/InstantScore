import java.io.*;
import java.util.HashMap;
import com.twilio.sdk.TwilioRestException;

public class CodeGenerator {

	/**
	 * Generates random security code for a given phone number and returns it. It also logs the generated code in the file together with
	 * the phone number. We may need this in case server breaks, so that the users won't need to generate their security codes again. 
	 * @param phoneNumber the phone number
	 * @return the generated security code for this phone number
	 */
	public static String generateCodeForPhoneNumber(String phoneNumber) {
		if (map.containsKey(phoneNumber)) {
			return map.get(phoneNumber);
		}
		String generatedCode = getRandomCode(CODE_LENGTH);
		map.put(phoneNumber, generatedCode);
		return generatedCode;
	}

	/**
	 * Sends the generated security code to the user
	 * @param phoneNumber phone number of a user
	 * @param generatedCode generated security code
	 */
	public static void sendSecurityCodeToUser(String phoneNumber, String generatedCode){
		System.out.println("Sending security code "+generatedCode+" to the user "+phoneNumber+"...");
		Twilio twillioClient = new Twilio();
		try {
			twillioClient.send(phoneNumber, "Your security code is "+generatedCode+". \n Thanks for using InstantScore!");
		} catch (TwilioRestException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns random security word of given length. Each character will be either latin letter or a digit to make it easier for users to 
	 * read it
	 * @param length the length of the security code
	 * @return random security word of given length
	 */
	private static String getRandomCode(int length) {
		char[] buffer = new char[length];
		for (int i = 0; i < buffer.length; i++) {
			buffer[i] = getRandomChar();
		}
		return new String(buffer);
	}

	/**
	 * Returns arbitrarily chosen character which is one of [0-9], [a-z], [A-Z].
	 * @return random character
	 */
	private static char getRandomChar() {
		int offset = (int) (Math.abs(rgen.nextInt()));
		if (offset % 3 == 0) {
			return (char) ('0' + offset % 10);
		}
		if (offset % 3 == 1) {
			return (char) ('a' + offset % 25);
		}
		return (char) ('A' + offset % 25);
	}

	/**
	 * Logs the (phoneNumber, generatedKey) pair in the file. It can be useful in case the server breaks
	 * @param phoneNumber
	 * @param generatedCode
	 */
	public static void writeIntoFile(String phoneNumber, String generatedCode) {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(CODES_FILE_NAME));
			pw.println(phoneNumber + " -> " + generatedCode);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Validates whether the claimed code is correct for the given phone number
	 * @param phoneNumber phone number
	 * @param claimedCode claimed security code
	 * @return whether the claimed code is correct
	 */
	public static boolean validatePair(String phoneNumber, String claimedCode){
		return map.containsKey(phoneNumber) && map.get(phoneNumber).equals(claimedCode);
	}
	
	private static java.util.Random rgen = new java.util.Random();

	private static final int CODE_LENGTH = 5;

	private static final String CODES_FILE_NAME = "codes"; // TODO change the filename to a path of the file on AWS

	public static HashMap<String, String> map = new HashMap<String, String>();

}
