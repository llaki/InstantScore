
public class MessageGenerator {
	public static void main(String[] args) {
		MatchInfo m1 = new MatchInfo(new Team("A"), new Team("B"), new Score("0-0"), new Date("2 Dec"), 
				new Tournament("Champ"), new Time("19:23"));
		MatchInfo m2 = new MatchInfo(new Team("A"), new Team("B"), new Score("2-0"), new Date("2 Dec"), 
				new Tournament("Champ"), new Time("4"));
		System.out.println(generateMessageTextViaUpdate(MatchUpdate.matchHasBeenUpdated(m1, m2)));
	}
	
	public static String generateMessageTextViaUpdate(MatchUpdate update){
		// TODO write implementation of this method
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
			String g = (sc==1 ? "goal!" : "goals!");
			sb.append(update.getCurrentMatchInfo().getHomeTeam().toString()+" scored "+sc+" "+g+"\n");
		}
		if(update.secondTeamScored()){
			int sc = update.getNumSecondScored();
			String g = (sc==1 ? "goal!" : "goals!");
			sb.append(update.getCurrentMatchInfo().getAwayTeam().toString()+" scored "+sc+" "+g+"\n");
		}
		if(update.hasEndedHalfTime()){
			sb.append("The first half has finished! \n");
		}
		if(update.hasEndedMatch()){
			sb.append("The match has finished! \n");
		}
		if(update.extraTimeFinished()){
			sb.append("The match has finished in extra time! \n");
		}
		if(update.extraTimeStarted()){
			sb.append("Extra time has been started! \n");
		}
		// TODO
		// handle the penalty shoot-out case here
		if(update.hasFinishedInPenalties()){
			// TODO write implementation of this case
		}
		return sb.toString();
	}
	
}
