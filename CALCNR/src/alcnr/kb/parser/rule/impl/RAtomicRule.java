package alcnr.kb.parser.rule.impl;

import java.util.ArrayList;
import java.util.Hashtable;

import alcnr.element.GenericElement;
import alcnr.kb.ALCNRKB;
import alcnr.kb.parser.rule.GenericRule;

public class RAtomicRule extends GenericRule {

	public RAtomicRule(){
		this("");
	}
	
	public RAtomicRule(String role){		
		this.fromPattern = "R";
		this.toPattern = role;
	}

	@Override
	public GenericElement createElement(ALCNRKB kb) {
		return kb.getRole(this.toPattern);
	}

	@Override
	public void addMap(int index, Hashtable<Integer, GenericElement> map,
			Hashtable<GenericElement, ArrayList<Integer>> vmap,
			GenericElement ge) {
		// NOTHING
	}
	
}
