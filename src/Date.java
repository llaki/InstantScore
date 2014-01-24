
public class Date {
	private String dateString;
	
	public Date(String date){
		dateString = date;
	}
	
	public String getDate(){
		return dateString;
	}
	
	@Override
	public String toString(){
		return dateString;
	}
	
}
