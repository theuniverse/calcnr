package alcnr.kb.parser.rule.impl;

import java.util.ArrayList;
import java.util.Hashtable;

import alcnr.element.GenericElement;
import alcnr.kb.ALCNRKB;
import alcnr.kb.parser.rule.GenericRule;

public class CTopRule extends GenericRule {
	
	public CTopRule(){
		fromPattern = "C";
		toPattern = "\\top";
	}

	@Override
	public GenericElement createElement(ALCNRKB kb) {
		return kb.getTopConcept();
	}

	@Override
	public void addMap(int index, Hashtable<Integer, GenericElement> map,
			Hashtable<GenericElement, ArrayList<Integer>> vmap,
			GenericElement ge) {
		// NOTHING
	}

}
