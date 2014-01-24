import java.util.*;

public class ParseUtils {
	public static void main(String[] args) {
		// TODO write test here
		System.out.println(DATE_TAG_NAME);
	}
	
	public static boolean isImageTag(String tag){
		return tag!=null && tag.length()>0 && tag.charAt(0)=='<';
	}
	
	public static String parseTimeInfo(String tag){
	//	System.out.println("parse time "+tag);
	//	if(tag==null || tag.length()==0) return ""; 
		if(!Character.isDigit(tag.charAt(0)))
			return tag;
		for(int i=0; i<tag.length(); i++) if(tag.charAt(i)=='&') return i==0 ? null : tag.substring(0, i);
		return tag;
	}
	
	public static boolean isEndOfCurrentTournamentInfo(String tag){
		return tag!=null && tag.equals(TABLE_END_TAG);
	}
	public ParseUtils() {
		// TODO Auto-generated constructor stub
	}
	public static boolean isTimeTag(String tag){
		return tag!=null && tag.equals(TIME_TAG_NAME);
	}
	
	public static String[] returnParsedStringArray2(String wholeText) {
		ArrayList<String> list = new ArrayList<String>();
		StringTokenizer tokenizer = new StringTokenizer(wholeText);
		while(tokenizer.hasMoreTokens()){
			String current = tokenizer.nextToken();
			if(current.charAt(0)=='<' && current.charAt(current.length()-1)!='>'){
				StringBuilder sb = new StringBuilder();
				sb.append(current);
				while(true){
					String token = tokenizer.nextToken();
					if(token.charAt(token.length()-1)=='>'){
						sb.append(" "+token);
						break;
					}
					else{
						sb.append(" "+token);
					}
				}
				addAllTagsFromString(list, sb.toString());
		//		list.add(sb.toString());
			}
			else{
				if(!containsTagSymbol(current)){
					addAllTagsFromString(list, current);
					continue;
				}
				StringBuilder sb = new StringBuilder();
				for(int s=0; s<current.length(); s++){
					int max = s;
					for(int t=s; t<current.length(); t++){
						if(current.charAt(t)!='<') max = t;
						else break;
					}
					if(s!=0) sb.append(" ");
					sb.append(current.substring(s, max+1));
				}
				addAllTagsFromString(list, sb.toString());
		//		list.add(current);
			}
		}
		String[] arr = new String[list.size()];
		for(int i=0; i<arr.length; i++)
			arr[i] = list.get(i);
		return arr;
	}
	
	public static String[] returnParsedStringArray(String wholeText) {
		ArrayList<String> list = new ArrayList<String>();
	//	StringTokenizer tokenizer = new StringTokenizer(wholeText);
		for(int i=0; i<wholeText.length(); i++){
			int max = i;
			boolean tagStart = (wholeText.charAt(i)=='<');
			for(int j=i; j<wholeText.length(); j++){
				boolean tagSymbol = (wholeText.charAt(j)=='>');
				boolean endHere = (tagStart && tagSymbol) || (!tagStart && wholeText.charAt(j)=='<');
				if(!endHere){
					max = j;
				}
				else{
					if(tagStart && tagSymbol){
						max = j;
					}
					break;
				}
			}
			list.add(wholeText.substring(i, max+1));
			i = max;
		}
		String[] arr = new String[list.size()];
		for(int i=0; i<arr.length; i++)
			arr[i] = formatWithoutLeadingOrEndingSpaces(list.get(i));
		return arr;
	}
	
	public static boolean startsWithAHref(String line){
		if(line==null || line.length()==0) return false;
		if(line.length()<8) return false;
		return line.substring(0, "<a href=".length()).equals("<a href=");
	}
	
	public static boolean isDateTag(String tag){
		return tag!=null && tag.equals(DATE_TAG_NAME);
	}
	
	private static String formatWithoutLeadingOrEndingSpaces(String word){
		if(word==null) return null;
		String result = "";
		int max = -1;
		for(int i=0; i<word.length(); i++){
			if(word.charAt(i)==' '){
				max = i;
			}
			else break;
		}
		int right = word.length();
		for(int i=word.length()-1; i>=0; i--){
			if(word.charAt(i)==' '){
				right = i;
			}
			else break;
		}
		for(int i=max+1; i<right; i++) result += word.charAt(i);
		return result;
	}
	
	private static boolean containsTagSymbol(String text){
		return text.indexOf("<")!=-1;
	}
	
	private static void addAllTagsFromString(ArrayList<String> list, String tag){
		for(int i=0; i<tag.length(); i++){
			if(tag.charAt(i)!='<'){
				int max = i;
				for(int j=i; j<tag.length(); j++){
					if(tag.charAt(j)!='<'){
						max = j;
						continue;
					}
					else break;
				}
				list.add(tag.substring(i, max+1));
				i = max;
				continue;
			}
			int max = i;
			for(int j=i; j<tag.length(); j++){
				if(tag.charAt(j)!='>'){
					max = j;
				}
				else{
					max = j;
					break;
				}
			}
			list.add(tag.substring(i, max+1));
			i = max;
		}
	}
	
	/**
	 * This method is just wrong. Don't use it please.
	 * @param pageHtml
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<HtmlNode> wrongGetNodesFromHtmlPage(String pageHtml) throws Exception {
		ArrayList<HtmlNode> nodeList = new ArrayList<HtmlNode>();
		StringTokenizer st = new StringTokenizer(pageHtml);
		Stack<String> stack = new Stack<String>();
		while(st.hasMoreTokens()){
			String token = st.nextToken();
			if(isTagStart(token)) {
				stack.add(getTagNameFromTagStart(token));
			}
			else if(isTagEnding(token)){
				ArrayList<HtmlNode> nodesBetween = new ArrayList<HtmlNode>();
				while(true){
					if(stack.isEmpty()){
						throw new Exception("Empty Stack Exception while tag parsing");
					}
					String current = stack.pop();
					if(current.equals(getTagNameFromTagEnd(token))){ // it's a matching starting tag and we stop here
						break;
					}
					nodesBetween.add(new HtmlNode(current));
				}
				HtmlNode node = new HtmlNode();
				nodeList.add(node);
				for(int i=0; i<nodesBetween.size(); i++){
					HtmlNode inside = nodesBetween.get(i);
					node.append(inside);
				}
			}
			else{
				stack.add(token);
			}
		}
		return nodeList;
	}
	
	/**
	 * This method returns the list of html nodes for the html page
	 * @param htmlPage
	 * @return
	 */
	public static ArrayList<HtmlNode> getNodesForHtmlPage(String htmlPage){
		
		ArrayList<HtmlNode> nodeList = new ArrayList<HtmlNode>();
	//	StringTokenizer tokenizer = new StringTokenizer(htmlPage);
	//	int countTokens = tokenizer.countTokens();
		String[] tokens = returnParsedStringArray(htmlPage);
	//	System.out.println(Arrays.toString(tokens));
	//	System.exit(0);
	//	String[] tokens = new String[countTokens];
	//	for(int i=0; i<tokens.length; i++) tokens[i] = tokenizer.nextToken();
//		System.out.println(tokens.length);
//		for(int i=0; i<tokens.length; i++) System.out.println(tokens[i]);
		recursiveFillViaNodes(nodeList, tokens, 1, tokens.length-1);
		return nodeList;
	}
	
	public static HtmlNode getNodeFrom(String[] tokens, int startIndex, int endIndex){
		String tagName = getTagNameFromTagStart(tokens[startIndex]);
		HtmlNode node = new HtmlNode();
		node.setTagName(tagName);
		ArrayList<HtmlNode> childNodes = new ArrayList<HtmlNode>();
		recursiveFillViaNodes(childNodes, tokens, startIndex+1, endIndex-1);
		for(int i=0; i<childNodes.size(); i++){
			HtmlNode childNode = childNodes.get(i);
			node.append(childNode);
		}
		return node;
	}
	
	public static int countNodes = 0;
	
	public static void recursiveFillViaNodes(ArrayList<HtmlNode> nodeList, String[] tokens, int startIndex, int endIndex){
//		System.out.println(startIndex+" "+endIndex+" "+tokens[startIndex]+" "+tokens[endIndex]);
//		System.out.println(countNodes);
		countNodes++;
		if(startIndex > endIndex)
			return;
		if(startIndex == endIndex){
			nodeList.add(new HtmlNode(tokens[startIndex]));
			return;
		}
		if(!isTag(tokens[startIndex])){
			nodeList.add(new HtmlNode(tokens[startIndex]));
			recursiveFillViaNodes(nodeList, tokens, startIndex+1, endIndex);
			return;
		}
		for(int i=startIndex; i<=endIndex; i++){
			if(!isTagStart(tokens[i])){
				nodeList.add(new HtmlNode(tokens[i]));
				continue;
			}
			int max = i;
			for(int j=i; j<=endIndex; j++){
				if(isTagEnding(tokens[j])){
					max = j;
					break;
				}
				else{
					max = j;
				}
			}
			HtmlNode childNode = getNodeFrom(tokens, i, max);
			nodeList.add(childNode);
			i = max;
		}
	}
	
	public static boolean isCorrectHtmlPage(String text){
		StringTokenizer tokenizer = new StringTokenizer(text);
		Stack<String> stack = new Stack<String>();
		while(tokenizer.hasMoreTokens()){
			String current = tokenizer.nextToken();
			if(isTagStart(current)){
				stack.push(current);
			}
			else if(isTagEnding(current)){
				while(true){
					if(stack.isEmpty()) return false;
					String pop = stack.pop();
					if(isTagMatchingPair(pop, current)){
						break;
					}
					if(isTagStart(pop))
						return false;
				}
			}
			else{
				
			}
		}
		// Everything went OK
		return true;
	}
	
	public static String getTagNameFromTagStart(String tagStart){
		return tagStart.substring(1, tagStart.length()-1);
	}
	
	public static String getTagNameFromTagEnd(String tagEnd){
		return tagEnd.substring(2, tagEnd.length()-1);
	}
	
	public static boolean isHomeTeamTag(String tag){
		return tag!=null && tag.equals(HOME_TEAM_TAH);
	}
	
	public static boolean isAwayTeamTag(String tag){
		return tag!=null && tag.equals(AWAY_TEAM_TAG);
	}
	
	public static boolean isScoreTag(String tag){
		return tag!=null && tag.equals(MATCH_SCORE_TAG);
	}
	
	public static boolean isTag(String text){
		if(text==null || text.length()<2) return false;
		return text.charAt(0)=='<' && text.charAt(text.length()-1)=='>';
	}
	
	public static boolean isTagMatchingPair(String tagStart, String tagEnd){
		StringTokenizer st = new StringTokenizer(tagStart);
		String first = st.nextToken();
		if(first.charAt(first.length()-1)!='>') first += '>';
		return isTagStart(first) && isTagEnding(tagEnd) && getTagNameFromTagEnd(first).equals(getTagNameFromTagStart(tagStart));
	}
	
	public static boolean isTagStart(String text){
		return isTag(text) && text.charAt(1)!='\\';
	}
	
	public static boolean isTagEnding(String text){
		return isTag(text) && text.charAt(1)=='\\';
	}
	
	public static final String DATE_TAG_NAME = "<span class=\"date\">";
	
	public static final String TIME_TAG_NAME = "<td class=\"fd\">";
	
	public static final String TABLE_END_TAG = "</table>";
	
	public static final String HOME_TEAM_TAH = "<td class=\"fh\">";
	
	public static final String AWAY_TEAM_TAG = "<td class=\"fa\">";
	
	public static final String MATCH_SCORE_TAG = "<td class=\"fs\">";
	
}

