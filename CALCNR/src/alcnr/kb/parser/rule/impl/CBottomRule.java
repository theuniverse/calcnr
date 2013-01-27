package alcnr.kb.parser.rule.impl;

import java.util.ArrayList;
import java.util.Hashtable;

import alcnr.element.GenericElement;
import alcnr.kb.ALCNRKB;
import alcnr.kb.parser.rule.GenericRule;

public class CBottomRule extends GenericRule {
	
	public CBottomRule(){
		fromPattern = "C";
		toPattern = "\\bottom";
	}

	@Override
	public GenericElement createElement(ALCNRKB kb) {
		return kb.getBottomConcept();
	}

	@Override
	public void addMap(int index, Hashtable<Integer, GenericElement> map,
			Hashtable<GenericElement, ArrayList<Integer>> vmap,
			GenericElement ge) {
		// NOTHING
	}

}
