package message;
import java.util.logging.Logger;

import livescore.LivescoreConnection;
import model.Date;
import model.MatchInfo;
import model.MatchUpdate;
import model.Score;
import model.Team;
import model.Time;
import model.Tournament;


public class MessageGenerator {
	private static final Logger LOGGER = Logger.getLogger(MessageGenerator.class.getName());
	
	public static void main(String[] args) {
		MatchInfo m1 = new MatchInfo(new Team("A"), new Team("B"), new Score("0-0"), new Date("2 Dec"), 
				new Tournament("Champ"), new Time("19:23"), false, false);
		MatchInfo m2 = new MatchInfo(new Team("A"), new Team("B"), new Score("2-0"), new Date("2 Dec"), 
				new Tournament("Champ"), new Time("4"), true, false);
		LOGGER.info(generateMessageTextViaUpdate(MatchUpdate.getMatchUpdate(m1, m2)));
	}
	
	public static String generateMessageTextViaUpdate(MatchUpdate update){
		StringBuilder sb = new StringBuilder();
		if(!update.hasBeenUpdated()){
			return "No Update for Match "+update.getCurrentMatchInfo();
		}
		MatchInfo m = update.getCurrentMatchInfo();
		sb.append("Update for match "+m.getHomeTeam()+" VS "+m.getAwayTeam()+" "+m.getMatchScore()+" \n");
		if(update.hasBeenAbandoned()){
			sb.append("This match has been abandoned. \n");
			return sb.toString();
		}
		if(update.hasBeenPostponed()){
			sb.append("This match has been postponed. \n");
			return sb.toString();
		}
		if(update.hasStarted()){
			sb.append("The match has just started. \n");
		}
		if(update.firstTeamScored()){
			int sc = update.getNumFirstScored();
			if(sc > 0) {
				String g = (sc==1 ? "goal!" : "goals!");
				sb.append(update.getCurrentMatchInfo().getHomeTeam().toString()+" scored "+sc+" "+g+"\n");
			}
			else {
				String g = (sc==-1 ? "goal has" : "goals have");
				sb.append((-sc)+" "+g+" been cancelled for "+update.getCurrentMatchInfo().getHomeTeam().toString()+".");
			}
		}
		if(update.secondTeamScored()){
			int sc = update.getNumSecondScored();
			if(sc>0) {
				String g = (sc==1 ? "goal!" : "goals!");
				sb.append(update.getCurrentMatchInfo().getAwayTeam().toString()+" scored "+sc+" "+g+"\n");
			}
			else {
				String g = (sc==-1 ? "goal has" : "goals have");
				sb.append((-sc)+" "+g+" been cancelled for "+update.getCurrentMatchInfo().getAwayTeam().toString()+".");
			}
		}
		if(update.hasEndedHalfTime()){
			sb.append("The first half has finished! \n");
		}
		if(update.hasEndedMatch()){
			sb.append("The match has finished! \n");
		}
		if(update.extraTimeFinished() && !update.hasFinishedInPenalties()){
			sb.append("The match has finished in extra time! \n");
		}
		if(update.extraTimeStarted()){
			sb.append("Extra time has been started! \n");
		}
		if(update.hasFinishedInPenalties()){
			sb.append((update.firstTeamWonInPenalties() ? update.getCurrentMatchInfo().getHomeTeam().toString() :
					update.getCurrentMatchInfo().getAwayTeam())
					+" has won on penalties!");
			
		}
		return sb.toString();
	}
	
}
