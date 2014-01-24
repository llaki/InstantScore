import java.util.StringTokenizer;


public class Score {
	private String score;
	
	private int firstScored = -1, secondScored = -1;
	
	public Score(String score){
		this.score = score;
		parse();
	}
	
	public void parse(){
		if(score==null) return;
		if(score.indexOf("?")!=-1) return;
		int ind = score.indexOf("-");
		if(ind==-1) return;
		String home = score.substring(0, ind), away = score.substring(ind+1);
		StringTokenizer st = new StringTokenizer(home);
		firstScored = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(away);
		secondScored = Integer.parseInt(st.nextToken());
	}
	
	public boolean isStarted(){
		return firstScored!=-1;
	}
	
	public int getFirstScored(){
		return firstScored;
	}
	
	public int getSecondScored(){
		return secondScored;
	}
	
	@Override
	public String toString(){
//		return score;
		if(firstScored==-1)
			return "? : ?";
		return firstScored+" : "+secondScored;
	}
	
	public static final Score UNDEFINED = new Score("? : ?");
	
}
