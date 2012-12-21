package alcnr.kb.parser.rule.impl;


import java.util.ArrayList;
import java.util.Hashtable;

import alcnr.element.GenericElement;
import alcnr.kb.ALCNRKB;
import alcnr.kb.parser.rule.GenericRule;

public class CAtomicRule extends GenericRule {

	public CAtomicRule(){
		this("");
	}
	
	public CAtomicRule(String term){
		fromPattern = "C";
		toPattern = term;
	}

	@Override
	public GenericElement createElement(ALCNRKB kb) {
		// TODO Auto-generated method stub
		return kb.getConcept(toPattern);
	}

	@Override
	public void addMap(int index, Hashtable<Integer, GenericElement> map,
			Hashtable<GenericElement, ArrayList<Integer>> vmap,
			GenericElement ge) {
		// TODO Auto-generated method stub
		// NOTHING
	}
	
}
