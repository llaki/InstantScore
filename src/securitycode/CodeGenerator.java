package securitycode;
import java.io.*;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import twillio.Twilio;

import com.twilio.sdk.TwilioRestException;

public class CodeGenerator {
	private static final Logger LOGGER = Logger.getLogger(CodeGenerator.class.getName());

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
		LOGGER.log(Level.FINER, "Sending security code "+generatedCode+" to the user "+phoneNumber+"...");
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
	 * Returns arbitrarily chosen character which is one of [0-9].
	 * @return random cipher
	 */
	private static char getRandomChar() {
		int randInt = (int) (Math.abs(rgen.nextInt()));
		return (char) ('0' + randInt % 10);
	}

	/**
	 * Logs the (phoneNumber, generatedKey) pair in the file. It can be useful in case the server breaks
	 * @param phoneNumber
	 * @param generatedCode
	 */
	public static void writeIntoFile(String phoneNumber, String generatedCode) {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(CODES_FILE_NAME));
			pw.println(phoneNumber + " " + generatedCode);
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
	
	public static void fillMapFromFile(File codesFile) {
		boolean codesFileExists = true;
		try{
			BufferedReader rd = new BufferedReader(new FileReader(codesFile));
			StringTokenizer tokenizer;
			while(true) {
				String line = rd.readLine();
				if(line==null || line.length()==0) break;
				tokenizer = new StringTokenizer(line);
				String code = "", phoneNumber = "";
				if(tokenizer.hasMoreTokens()) { 
					phoneNumber = tokenizer.nextToken();
				}
				if(tokenizer.hasMoreTokens()) { 
					code = tokenizer.nextToken();
					map.put(phoneNumber, code);
				}
			}
			LOGGER.log(Level.OFF,"Read from codes file succesfully.");
			rd.close();
		}
		catch(IOException ex) {
			LOGGER.log(Level.SEVERE,"Codes file have not been found.");
			codesFileExists = false;
		}
		if(!codesFileExists) {
			try {
				PrintWriter pw = new PrintWriter(new FileWriter(codesFile));
				pw.close();
				LOGGER.log(Level.OFF,"Codes file has been created.");
			}
			catch(IOException ex) {
				LOGGER.log(Level.SEVERE,"IOException occured while attempting to create a codes file.");
			}
		}
	}
	
	private static java.util.Random rgen = new java.util.Random();

	private static final int CODE_LENGTH = 6;

	public static final String CODES_FILE_NAME = "codes_base"; // TODO change the filename to a path of the file on AWS

	private static HashMap<String, String> map = new HashMap<String, String>();

}
