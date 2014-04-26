import java.util.*;

public class Request {
	
	private String phoneNumber, securityCode; //numberWithCode;

	private ArrayList<String> matchIds;

	/**
	 * phonenum=+995595123456&securitycode=qwerty&data=[Bolivar VS The Strongest]
	 * securitycoderequest=+995595123456
	 * @param requestString
	 * @param newOne
	 */
	public Request(String requestString){
		requestString = removeSpacesBothSides(requestString);
		if(requestString.charAt(0)=='p'){ // phonenum=+995595123456&securitycode=qwerty&data=[Bolivar VS The Strongest]  case
			int indexOfAmp = requestString.indexOf("&");
			if(indexOfAmp==-1){
				System.out.println("INVALID REQUEST "+requestString);
				return;
			}
			phoneNumber = requestString.substring(9, indexOfAmp);
			securityCode = "";
			int secondAmpIndex = -1;
			for(int i=indexOfAmp+1; i<requestString.length(); i++){
				char cur = requestString.charAt(i);
				if(cur=='&'){
					secondAmpIndex = i;
					break;
				}
				securityCode += cur;
			}
			if(!CodeGenerator.validatePair(phoneNumber, securityCode)){
				// the pair is invalid, so we just stop here
				return;
			}
			User u = new User(phoneNumber);
			if (phoneNumber == null || phoneNumber.length() == 0) {
				u = User.TEST_USER;
			}
			for (int i = secondAmpIndex+1; i < requestString.length(); i++) {
				if (requestString.charAt(i) != '[')
					continue;
				int till = i;
				for (int j = i + 1; j < requestString.length(); j++) {
					if (requestString.charAt(j) == ']')
						break;
					else
						till = j;
				}
				String matchId = requestString.substring(i + 1, till + 1);
				matchIds.add(matchId);
				MatchUserBinder.addMatchToUser(u,
						AllGoingMatches.getExistingMatchObject(matchId));
				i = till;
			}
		}
		else{ // securitycoderequest=+995595123456 case
			int indexOfEqual = requestString.indexOf("=");
			if(indexOfEqual==requestString.length()-1 || indexOfEqual==-1){
				// something is wrong, no need to continue
				return;
			}
			phoneNumber = requestString.substring(indexOfEqual+1);
			securityCode = CodeGenerator.generateCodeForPhoneNumber(phoneNumber);
			CodeGenerator.sendSecurityCodeToUser(phoneNumber, securityCode);
		}
	}
	
//	public Request(String requestString) {
//		System.out.println("got request:");
//		System.out.println(requestString);
//		matchIds = new ArrayList<String>();
//		int index = requestString.indexOf("[");
//		if (index == -1) {
//			numberWithCode = removeSpacesBothSides(requestString);
//			return;
//		}
//		numberWithCode = removeAllSpaces(removeSpacesBothSides(requestString
//				.substring(0, index)));
//		phoneNumber = extractPhoneNumber(numberWithCode);
//		securityCode = extractSecurityCode(numberWithCode);
//		if(!CodeGenerator.validatePair(phoneNumber, securityCode)){
//			// the pair is invalid, so we just stop here
//			return;
//		}
//		User u = new User(phoneNumber);
//		if (phoneNumber == null || phoneNumber.length() == 0) {
//			u = User.TEST_USER;
//		}
//		for (int i = index; i < requestString.length(); i++) {
//			if (requestString.charAt(i) != '[')
//				continue;
//			int till = i;
//			for (int j = i + 1; j < requestString.length(); j++) {
//				if (requestString.charAt(j) == ']')
//					break;
//				else
//					till = j;
//			}
//			String matchId = requestString.substring(i + 1, till + 1);
//			matchIds.add(matchId);
//			MatchUserBinder.addMatchToUser(u,
//					AllGoingMatches.getExistingMatchObject(matchId));
//			i = till;
//		}
//
//	}
//
//	private static String extractPhoneNumber(String numberAndCode) {
//		if (numberAndCode.length() == 0)
//			return "";
//		int index = numberAndCode.indexOf(DIVIDER);
//		if (index == -1)
//			return numberAndCode;
//		return numberAndCode.substring(0, index);
//	}
//
//	private static String extractSecurityCode(String numberAndCode) {
//		if (numberAndCode.length() == 0)
//			return "";
//		int index = numberAndCode.indexOf(DIVIDER);
//		if (index == numberAndCode.length() - 1 || index == -1)
//			return "";
//		return numberAndCode.substring(index + 1);
//	}

	public ArrayList<String> getMatchIds() {
		return matchIds;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	private static String removeSpacesBothSides(String text) {
		int min = -1, max = text.length();
		for (int i = 0; i < text.length(); i++) {
			if (text.charAt(i) == ' ') {
				min = i;
			} else
				break;
		}
		for (int i = text.length() - 1; i >= 0; i--) {
			if (text.charAt(i) == ' ') {
				max = i;
			} else
				break;
		}
		if (min > max)
			return "";
		return text.substring(min + 1, max);
	}

//	private static final char DIVIDER = '&';

}
