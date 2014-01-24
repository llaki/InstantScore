import java.util.*;

public class AllGoingMatchesTest {
	public static void main(String[] args) throws Exception {
		ArrayList<MatchInfo> oldFile = MatchesFileReadUtils.readMatchInfosFromFile("oldScores");
		ArrayList<MatchInfo> newFile = MatchesFileReadUtils.readMatchInfosFromFile("newScores");
		AllGoingMatches.changesToGoingMatches(oldFile);
		AllGoingMatches.changesToGoingMatches(newFile);
	}
	
	
}
