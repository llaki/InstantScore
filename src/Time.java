import java.util.Comparator;


public class Time {
	private String timeStatus;
	
	public Time(String timeStatus){
		this.timeStatus = timeStatus;
	}
	
	
	public String getTime(){
		return timeStatus;
	}
	
	@Override
	public String toString(){
		return timeStatus;
	}
	
	public static final Comparator<Time> compareMatchTimes = new Comparator<Time>() {

		@Override
		public int compare(Time o1, Time o2) {
			return TimeUtils.getPriority(o1) - TimeUtils.getPriority(o2);
		}
		
	};
	
}
