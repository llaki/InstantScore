import java.util.*;

public class HtmlNode {
	
	private static int nodeIdCounter;
	
	private String tagName, textInside;
	
	private int nodeId;
	
	private HtmlNode parentNode = null;
	
	private ArrayList<HtmlNode> childNodes;
	
	/**
	 * Constructor for HtmlNode
	 * @param whole the whole tag text
	 */
	public HtmlNode(String whole){
		childNodes = new ArrayList<HtmlNode>();
		nodeId = AssignNewNodeId();
		parseTagFromText(whole);
		textInside = whole;
	}
	
	/**
	 *  Creates an empty HtmlNode object
	 */
	public HtmlNode(){
		nodeId = AssignNewNodeId();
		childNodes = new ArrayList<HtmlNode>();
	}
	
	/**
	 * Parses the tag description 
	 * @param text
	 */
	private void parseTagFromText(String text){
		// TODO write implementation
	}
	
	/**
	 * Appends child tag
	 * @param tag tag object to be appended
	 */
	public void append(HtmlNode tag){
		childNodes.add(tag);
		tag.setParentNode(this);
	}
	
	public void setTagName(String tagName){
		this.tagName = tagName;
//		this.tagName = ParseUtilities.getTagNameFromTagStart(tagName);
	}
	
	/**
	 * Sets the parent node for a node
	 * @param parent a parent node to be assigned
	 */
	public void setParentNode(HtmlNode parent){
		parentNode = parent;
	}
	
	/**
	 * @return whether a node has a parent
	 */
	public boolean hasParentNode(){
		return parentNode == null;
	}
	
	/**
	 * @return the id for this node
	 */
	public int getNodeId(){
		return nodeId;
	}
	
	/**
	 * @return tag name of this node
	 */
	public String getTagName(){
		return tagName;
	}
	
	/**
	 * @return the text inside the node
	 */
	public String getText(){
		return textInside;
	}
	
	/**
	 * Creates and returns the new id for a node. It's guranteed that two id's can't be the same
	 * @return new id for a tag
	 */
	public int AssignNewNodeId(){
		nodeIdCounter++;
		return nodeIdCounter;
	}
	
	/**
	 * Returns the string representation of a node
	 */
	public String toString(){
		if(tagName==null){
			return textInside;
		}
		StringBuilder sb = new StringBuilder();
		sb.append("<"+getTagName()+">\n");
	//	sb.append("Inside part of the tag... \n");
		for(int i=0; i<childNodes.size(); i++){
			HtmlNode childNode = childNodes.get(i);
			sb.append(childNode.toString()+"\n");
		}
		sb.append("<\\"+getTagName()+">\n");
		return sb.toString();
	}
	
}
