package model;


public class MatchUpdate {
	
	private int firstScored = 0, secondScored = 0, updateStatus = 0;
	
	private MatchInfo currentMatchInfo;
	
	public MatchUpdate (int updateStatus, int firstScored, int secondScored, MatchInfo currentInfo){
		this.updateStatus = updateStatus;
		this.firstScored = firstScored;
		this.secondScored = secondScored;
		this.currentMatchInfo = currentInfo;
	}
	
	public MatchInfo getCurrentMatchInfo(){
		return currentMatchInfo;
	}
	
	public int getNumFirstScored(){
		return firstScored;
	}
	
	public int getNumSecondScored(){
		return secondScored;
	}
	
	public int getUpdateStatus(){
		return updateStatus;
	}
	
	private static boolean bitmaskContainsBit(int mask, int bit){
		return (mask & (1<<bit)) != 0;
	}
	
	public boolean hasStarted(){
		return bitmaskContainsBit(updateStatus, MATCH_STARTED);
	}
	
	public boolean hasEndedHalfTime(){
		return bitmaskContainsBit(updateStatus, HALF_TIME_OVER);
	}
	
	public boolean hasEndedMatch(){
		return bitmaskContainsBit(updateStatus, MATCH_FINISHED);
	}
	
	public boolean hasBeenPostponed(){
		return bitmaskContainsBit(updateStatus, POSTPONED);
	}
	
	public boolean hasBeenAbandoned(){
		return bitmaskContainsBit(updateStatus, ABANDONED);
	}
	
	public boolean extraTimeFinished(){
		return bitmaskContainsBit(updateStatus, FINISHED_EXTRA_TIME);
	}
	
	public boolean extraTimeStarted(){
		return bitmaskContainsBit(updateStatus, EXTRA_TIME_STARTED);
	}
	
	public boolean firstTeamScored(){
		return getNumFirstScored()>0;
	}
	
	public boolean secondTeamScored(){
		return getNumSecondScored()>0;
	}
	
	public boolean hasFinishedInPenalties(){
		//TODO write real implementation
		return false;
	}
	
	public boolean firstTeamWonInPenalties(){
		// TODO write real implementation
		return true;
	}
	
	public static MatchUpdate matchHasBeenUpdated(MatchInfo old, MatchInfo current){
		if(old==null){
			old = MatchInfo.returnEmptyMatchInfo();
		}
		Score oldScore = old.getMatchScore(), currentScore = current.getMatchScore();
		Time oldTime = old.getTimeStatus(), currentTime = current.getTimeStatus();
		boolean oldStarted = oldScore.isStarted(), currentStarted = currentScore.isStarted();
		int update = 0;
		if(!oldStarted && currentStarted){
	//		update = (update | (1<<MATCH_STARTED));
			// THIS UPDATE SHOULD TEMPORARILY BE REMOVED FROM MESSAGE TO DECREASE NUMBER OF TOTAL MESSAGES
		}
		if(!oldTime.getTime().equals("HT") && currentTime.getTime().equals("HT")){
			//update = (update | (1<<HALF_TIME_OVER));
			// THIS UPDATE SHOULD BE TEMPORARILY REMOVED FROM MESSAGE TO DECREASE NUMBER OF TOTAL MESSAGES
		}
		if(!oldTime.getTime().equals("FT") && currentTime.getTime().equals("FT")){
			update = (update | (1<<MATCH_FINISHED));
		}
		if(oldTime.getTime().indexOf("p")==-1 && currentTime.getTime().indexOf("p")!=-1){
			update = (update | (1<<POSTPONED));
		}
		if(oldTime.getTime().indexOf("b")==-1 && currentTime.getTime().indexOf("b")!=-1){
			update = (update | (1<<ABANDONED));
		}
		if(oldTime.getTime().indexOf("AET")==-1 && currentTime.getTime().indexOf("AET")!=-1){
			update = (update | (1<<FINISHED_EXTRA_TIME));
		}
		if(oldTime.getTime().indexOf("AET")==-1){
			String oldStr = oldTime.getTime();
			boolean allDigits = true;
			for(int s=0; s<oldStr.length(); s++){
				if(!Character.isDigit(oldStr.charAt(s))) allDigits = false;
			}
			String curStr = currentTime.getTime();
			boolean allDigits2 = true;
			for(int s=0; s<curStr.length(); s++){
				if(!Character.isDigit(curStr.charAt(s))) allDigits2 = false;
			}
			boolean less = oldStr==null || oldStr.length()==0 || !allDigits || (Integer.parseInt(oldStr)<=90);
			boolean more = (curStr!=null && curStr.length()>0 && allDigits2 && (Integer.parseInt(curStr)>90));
			if(less && more){
				update = (update | (1<<EXTRA_TIME_STARTED));
			}
		}
		int oldGoalsFirst = (oldStarted ? oldScore.getFirstScored() : 0), oldGoalsSecond = (oldStarted ? oldScore.getSecondScored() : 0);
		int currentGoalsFirst = (currentStarted ? currentScore.getFirstScored() : 0),
				currentGoalSecond = (currentStarted ? currentScore.getSecondScored() : 0);
		int firstScoredUpd = currentGoalsFirst - oldGoalsFirst, secondScoredUpd = currentGoalSecond - oldGoalsSecond;
		firstScoredUpd = Math.max(firstScoredUpd, 0);
		secondScoredUpd = Math.max(secondScoredUpd, 0);
		return new MatchUpdate(update, firstScoredUpd, secondScoredUpd, current);
	}
	
	public boolean hasBeenUpdated(){
		return getUpdateStatus()!=0 || firstTeamScored() || secondTeamScored();
	}
	
	public static final int MATCH_STARTED = 1, MATCH_FINISHED = 2, HALF_TIME_OVER = 4, FIRST_TEAM_SCORED = 5, SECOND_TEAM_SCORED = 6,
			POSTPONED = 7, ABANDONED = 8,	NO_UPDATE = 0, FINISHED_EXTRA_TIME = 9, EXTRA_TIME_STARTED = 10;
	
}
