package parseutils;
import java.util.*;

public class HtmlPage {
	private String htmlText;
	
	private ArrayList<HtmlNode> nodes;
	
	
	public HtmlPage(String pageHtml){
		nodes = new ArrayList<HtmlNode>();
		
	}
	
	public String getHtmlText(){
		return htmlText;
	}
	
	public ArrayList<HtmlNode> getNodes(){
		return nodes;
	}
	
}
