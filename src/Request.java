import java.util.*;

public class Request {
	public static void main(String[] args) {
		Request req = new Request(" +5(98) 37 42 03 [A & B]    [C & D] ");
		System.out.println(req.getPhoneNumber());
		ArrayList<String> ids = req.getMatchIds();
		System.out.println(ids);
	}
	
	private String phoneNumber;
	
	private ArrayList<String> matchIds;
	
	public Request(String requestString){
		matchIds = new ArrayList<String>();
		int index = requestString.indexOf("[");
		if(index==-1){
			phoneNumber = removeSpacesBothSides(requestString);
			return;
		}
		phoneNumber = removeAllSpaces(removeSpacesBothSides(requestString.substring(0, index)));
		User u = new User(phoneNumber);
		for(int i=index; i<requestString.length(); i++){
			if(requestString.charAt(i)!='[') continue;
			int till = i;
			for(int j=i+1; j<requestString.length(); j++){
				if(requestString.charAt(j)==']') break;
				else till = j;
			}
			String matchId = requestString.substring(i+1, till+1);
			matchIds.add(matchId);
			MatchUserBinder.addMatchToUser(u, AllGoingMatches.getExistingMatchObject(matchId));
			i = till;
		}
		
	}
	
	private static String removeAllSpaces(String text){
		String result = "";
		for(int i=0; i<text.length(); i++){
			if(Character.isDigit(text.charAt(i)) || text.charAt(i)=='+')
				result += text.charAt(i);
		}
		return result;
	}
	
	public ArrayList<String> getMatchIds(){
		return matchIds;
	}
	
	public String getPhoneNumber(){
		return phoneNumber;
	}
	
	private static String removeSpacesBothSides(String text){
		int min = -1, max = text.length();
		for(int i=0; i<text.length(); i++){
			if(text.charAt(i)==' '){
				min = i;
			}
			else break;
		}
		for(int i=text.length()-1; i>=0; i--){
			if(text.charAt(i)==' '){
				max = i;
			}
			else break;
		}
		if(min>max) return "";
		return text.substring(min+1, max);
	}
	
	
	
}
