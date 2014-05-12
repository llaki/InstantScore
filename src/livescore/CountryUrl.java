package livescore;

public class CountryUrl {
	
	private String countryName, url;
	
	public CountryUrl(String countryName, String url) {
		this.countryName = countryName;
		this.url = url;
	}
	
	public String getCountryName() {
		return countryName;
	}
	
	public String getUrl() {
		return url;
	}
	
}
