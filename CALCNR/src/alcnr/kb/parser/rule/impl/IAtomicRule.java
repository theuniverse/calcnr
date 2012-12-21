package alcnr.kb.parser.rule.impl;

import java.util.ArrayList;
import java.util.Hashtable;

import alcnr.element.GenericElement;
import alcnr.kb.ALCNRKB;
import alcnr.kb.parser.rule.GenericRule;

public class IAtomicRule extends GenericRule {

	public IAtomicRule(){
		this("");
	}
	
	public IAtomicRule(String individual){
		this.fromPattern = "a";
		this.toPattern = individual;
	}

	@Override
	public GenericElement createElement(ALCNRKB kb) {
		// TODO Auto-generated method stub
		return kb.getIndividual(toPattern);
	}

	@Override
	public void addMap(int index, Hashtable<Integer, GenericElement> map,
			Hashtable<GenericElement, ArrayList<Integer>> vmap,
			GenericElement ge) {
		// TODO Auto-generated method stub
		// NOTHING
	}
	
}
