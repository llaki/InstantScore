package request;
import java.util.*;

import securitycode.CodeGenerator;
import model.AllGoingMatches;
import model.MatchUserBinder;
import model.User;
import model.UserFactory;

public class Request {
	
	private String phoneNumber, securityCode, data; //numberWithCode;

	private ArrayList<String> matchIds;
	
	public Request(String data, String phoneNum, String securityCode){
		matchIds = new ArrayList<String>();
		this.phoneNumber = "+"+phoneNum;
		this.data = data;
		this.securityCode = securityCode;
		System.out.println("Inside Request constructor with parameters : "+data+" and "+phoneNum + " and "+ securityCode);
		if((data==null || data.length()<=2) && (securityCode==null || securityCode.length()==0)){ // sec. code case
			System.out.println("sending code procedure...");
			securityCode = CodeGenerator.generateCodeForPhoneNumber(phoneNumber);
			CodeGenerator.sendSecurityCodeToUser(phoneNumber, securityCode);
		}
		else{ // matches subscribe case
			if(!CodeGenerator.validatePair(phoneNumber, securityCode)){
				// the pair is invalid, so we just stop here
				System.out.println("invalid pair : phoneNum="+phoneNum+" and securityCode="+securityCode);
				return;
			}
			System.out.println("parsing data ...");
			parseData();
		}
	}
	
	private void parseData(){
		User u = UserFactory.createUser(phoneNumber);
		if (phoneNumber == null || phoneNumber.length() == 0) {
			u = User.TEST_USER;
		}
		ArrayList<String> matchIds = new ArrayList<String>();
		for (int i = 0; i < data.length(); i++) {
			int till = i;
			for (int j = i + 1; j < data.length(); j++) {
				if (data.charAt(j) == ']')
					break;
				else
					till = j;
			}
			if(till==i){
				break;
			}
			String matchId = data.substring(i + 1, till + 1);
			matchIds.add(matchId);
			System.out.println(matchId+" added to user...");
			MatchUserBinder.addMatchToUser(u,
					AllGoingMatches.getExistingMatchObject(matchId));
			matchIds.add(matchId);
			i = till+1;
		}
		u.setNewSubscription(matchIds);
	}
	
	public ArrayList<String> getMatchIds() {
		return matchIds;
	}
	
	public String getSecurityCode(){
		return securityCode;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

}
