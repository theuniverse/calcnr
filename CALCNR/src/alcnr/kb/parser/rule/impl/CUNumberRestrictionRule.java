package alcnr.kb.parser.rule.impl;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import alcnr.element.GenericElement;
import alcnr.element.concept.impl.UNumberRestrictionConcept;
import alcnr.kb.ALCNRKB;
import alcnr.kb.parser.ParseNode;
import alcnr.kb.parser.rule.GenericRule;

public class CUNumberRestrictionRule extends GenericRule {

	String toRegexPattern = "\\Q(\\le\\E\\d+ R\\Q)\\E";
	String nRegexPattern = "\\d+";
	Pattern p, pn;
	Matcher m, mn;
	
	public CUNumberRestrictionRule(){
		this.fromPattern = "C";
		this.toPattern = "(\\le\\n R)";
		p = Pattern.compile(toRegexPattern);
		pn = Pattern.compile(nRegexPattern);
	}
	
	public ArrayList<Integer> matches(ParseNode q) {
		
		ArrayList<Integer> result = new ArrayList<Integer>();
		
		if(toPattern == null) return result;
		
		String text = q.text;
		m = p.matcher(text);
		
		int i = 0;
		while(m.find(i)){
			result.add(m.start());
			//System.out.println(m.group());
			i = m.end();
		}
		
		return result;
	}
	
	public ParseNode parse(ParseNode q, Integer i) {
		//return null;
		ParseNode node = null;
		String text = q.text;
		
		// form the new String
		if(m.find(i)){
			int sindex = m.start(), eindex = m.end();
			String pre = text.substring(0, sindex);
			String post = text.substring(eindex);
			node = new ParseNode(pre+fromPattern+post);
			mn = pn.matcher(m.group());
			mn.find();
			node.number = Integer.parseInt(mn.group());
		}		
		
		
		node.usedRule = this;
		node.index = i;
		
		return node;
	}

	@Override
	public GenericElement createElement(ALCNRKB kb) {
		return new UNumberRestrictionConcept();
	}

	@Override
	public void addMap(int index, Hashtable<Integer, GenericElement> map,
			Hashtable<GenericElement, ArrayList<Integer>> vmap,
			GenericElement ge) {
		int rNumber = ((UNumberRestrictionConcept)ge).n, count = 0;
		if(rNumber == 0){
			count = 1;
		}else{
			while(rNumber > 0){
				count++;
				rNumber /= 10;
			}
		}
		
		int rind = index + 5 + count;
		map.put(rind, ge);
		if(vmap.get(ge) == null){
			ArrayList<Integer> list = new ArrayList<Integer>();
			list.add(rind);
			vmap.put(ge, list);
		}
	}
	
}
