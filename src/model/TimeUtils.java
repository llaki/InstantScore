package model;
import java.util.Comparator;


public class TimeUtils {
	
	public static int getPriority(Time time){
		if(isFullTimeEnded(time)) return 1;
		if(isFirstHalfEnded(time)) return 2;
		if(isSecondHalfGoing(time)) return 3;
		if(isFirstHalfGoing(time)) return 4;
		return 5;
	}
	
	
	public static boolean isFutureTime(Time time){
		return time.getTime().indexOf(":")!=-1;
	}
	
	public static boolean isFirstHalfGoing(Time time){
		String timeString = time.getTime();
		if(timeString==null) {
			return false;
		}
		for(int i=0; i<timeString.length(); i++) {
			if(!Character.isDigit(timeString.charAt(i))) {
				return false;
			}
		}
		return Integer.parseInt(timeString) <= FIRST_HALF_DURATION;
	}
	
	public static boolean isSecondHalfGoing(Time time){
		String timeString = time.getTime();
		for(int i=0; i<timeString.length(); i++)
			if(!Character.isDigit(timeString.charAt(i)))
				return false;
		return Integer.parseInt(timeString)>45;
	}
	
	public static boolean isFirstHalfEnded(Time time){
		String timeString = time.getTime();
		return timeString.equals("HT");
	}
	
	private static int getMinutes(String timeString){
		int index = timeString.indexOf(":");
		if(index==-1) return 0;
		int hours = Integer.parseInt(timeString.substring(0, index)), minutes = Integer.parseInt(timeString.substring(index+1));
		return 60*hours+minutes;
	}
	
	public static final Comparator<String> compareStartTimes = new Comparator<String>(){

		@Override
		public int compare(String o1, String o2) {
			return getMinutes(o1) - getMinutes(o2);
		}
		
	};
	
	public static boolean isFullTimeEnded(Time time){
		return time.getTime().equals("FT");
	}
	
	public static final int FIRST_HALF_DURATION = 45;
	
}
