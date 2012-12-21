package alcnr.kb.parser;

import java.util.ArrayList;

import alcnr.kb.parser.rule.GenericRule;

public class ParseNode{
	
	public ParseNode(String text){
		this.text = text;
	}
	
	public String text;
	public GenericRule usedRule;
	public int index;
	public int number;
	
	public ParseNode parent = null;
	public ArrayList<ParseNode> children = new ArrayList<ParseNode>();

}
