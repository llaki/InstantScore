package model;

public class Tournament {
	private String tournamentName;
	
	public Tournament(String tournamentName){
		this.tournamentName = tournamentName;
	}
	
	public String getTournamentName(){
		return tournamentName;
	}
	
	@Override
	public String toString(){
		return tournamentName;
	}
	
	
}
