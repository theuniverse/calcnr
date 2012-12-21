package alcnr.kb.parser.rule;

import java.util.ArrayList;
import java.util.Hashtable;

import alcnr.element.GenericElement;
import alcnr.kb.ALCNRKB;
import alcnr.kb.parser.ParseNode;

public abstract class GenericRule {
	
	protected String fromPattern;
	protected String toPattern;
	
	public String fromPattern(){
		return this.fromPattern;
	}
	
	public String toPattern(){
		return this.toPattern;
	}
	
	//public abstract ParseNode parse(ParseNode q, Integer i);
	
	public int diff(){
		return this.toPattern.length() - this.fromPattern.length();
	}
	
	public ArrayList<Integer> matches(ParseNode q) {
		
		ArrayList<Integer> result = new ArrayList<Integer>();
		
		if(toPattern == null) return result;
		
		String text = q.text;
		int i = 0;
		while((i = text.indexOf(toPattern, i)) != -1){
			result.add(i);
			i += toPattern.length();
		}
		
		return result;
	}
	
	public ParseNode parse(ParseNode q, Integer i) {
		//return null;
		String text = q.text;
		
		// form the new String
		String pre = text.substring(0, i);
		String post = text.substring(i + toPattern.length());
		
		ParseNode node = new ParseNode(pre+fromPattern+post);
		//System.out.println(node.text);
		node.usedRule = this;
		node.index = i;
		
		return node;
	}
	
	public String toString(){		
		return fromPattern + "->" + toPattern;		
	}

	public abstract GenericElement createElement(ALCNRKB kb);	
	public abstract void addMap(int index, Hashtable<Integer, GenericElement> map, Hashtable<GenericElement, ArrayList<Integer>> vmap, GenericElement ge);
	
}
