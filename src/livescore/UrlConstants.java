package livescore;

public class UrlConstants {
	
	public static final CountryUrl[] COUNTRIES_AND_URLS = new CountryUrl[] {
		new CountryUrl("Champions League", "http://www.livescore.com/soccer/champions-league/"),
		new CountryUrl("Europe League", "http://www.livescore.com/soccer/europa-league/"),
		new CountryUrl("World Cup 2014", "http://www.livescore.com/worldcup2014/"),
		new CountryUrl("Confed Cup", "http://www.livescore.com/soccer/confederations-cup/"),
		new CountryUrl("International", "http://www.livescore.com/soccer/intl/"),
		new CountryUrl("England", makeStandartUrlFromCountry("England")),
		new CountryUrl("Italy", makeStandartUrlFromCountry("Italy")),
		new CountryUrl("France", makeStandartUrlFromCountry("France")),
		new CountryUrl("Germany", makeStandartUrlFromCountry("Germany")),
		new CountryUrl("Spain", makeStandartUrlFromCountry("Spain")),
		new CountryUrl("Netherlands", makeStandartUrlFromCountry("Netherlands")),
		new CountryUrl("Belgium", makeStandartUrlFromCountry("Belgium")),
		new CountryUrl("Portugal", makeStandartUrlFromCountry("Portugal")),
//		new CountryUrl("Austria", makeStandartUrlFromCountry("Austria")),
//		new CountryUrl("Cyprus", makeStandartUrlFromCountry("Cyprus")),
		new CountryUrl("Scotland", makeStandartUrlFromCountry("Scotland")),
//		new CountryUrl("Denmark", makeStandartUrlFromCountry("Denmark")),
//		new CountryUrl("Finland", makeStandartUrlFromCountry("Finland")),
		new CountryUrl("Greece", makeStandartUrlFromCountry("Greece")),
//		new CountryUrl("Island", makeStandartUrlFromCountry("Island")),
//		new CountryUrl("Ireland", makeStandartUrlFromCountry("Ireland")),
//		new CountryUrl("Luxembourg", makeStandartUrlFromCountry("Luxembourg")),
//		new CountryUrl("Norway", makeStandartUrlFromCountry("Norway")),
//		new CountryUrl("Sweden", makeStandartUrlFromCountry("Sweden")),
		new CountryUrl("Switzerland", makeStandartUrlFromCountry("Switzerland")),
//		new CountryUrl("Turkey", makeStandartUrlFromCountry("Turkey")),
//		new CountryUrl("Wales", makeStandartUrlFromCountry("Wales")),
//		new CountryUrl("Belarus", makeStandartUrlFromCountry("Belarus")),
//		new CountryUrl("Bulgaria", makeStandartUrlFromCountry("Bulgaria")),
//		new CountryUrl("Croatia", makeStandartUrlFromCountry("Croatia")),
//		new CountryUrl("Estonia", makeStandartUrlFromCountry("Estonia")),
//		new CountryUrl("Hungary", makeStandartUrlFromCountry("Hungary")),
//		new CountryUrl("Israel", makeStandartUrlFromCountry("Israel")),
//		new CountryUrl("Latvia", makeStandartUrlFromCountry("Latvia")),
//		new CountryUrl("Lithuania", makeStandartUrlFromCountry("Lithuania")),
//		new CountryUrl("Moldova", makeStandartUrlFromCountry("Moldova")),
//		new CountryUrl("Russia", makeStandartUrlFromCountry("Russia")),
//		new CountryUrl("Argentina", makeStandartUrlFromCountry("Argentina")),
//		new CountryUrl("CONCACAF", makeStandartUrlFromCountry("concacaf")),
//		new CountryUrl("Asia", makeStandartUrlFromCountry("Asia")),
//		new CountryUrl("Georgia", makeStandartUrlFromCountry("Georgia")),
	}; 
	
	private static String makeStandartUrlFromCountry(String countryName){
		return "http://www.livescore.com/soccer/"+countryName+"/";
	}
	
}
