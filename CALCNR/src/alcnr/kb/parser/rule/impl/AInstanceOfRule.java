package alcnr.kb.parser.rule.impl;

import java.util.ArrayList;
import java.util.Hashtable;

import alcnr.element.GenericElement;
import alcnr.kb.ALCNRKB;
import alcnr.kb.abox.InstanceOfAssertion;
import alcnr.kb.parser.rule.GenericRule;

public class AInstanceOfRule extends GenericRule {

	public AInstanceOfRule(){
		this.fromPattern = "S";
		this.toPattern = "C(a)";
	}

	@Override
	public GenericElement createElement(ALCNRKB kb) {
		return new InstanceOfAssertion();
	}

	@Override
	public void addMap(int index, Hashtable<Integer, GenericElement> map,
			Hashtable<GenericElement, ArrayList<Integer>> vmap,
			GenericElement ge) {
		int cind = index;
		int aind = index+2;
		map.put(cind, ge);
		map.put(aind, ge);
		if(vmap.get(ge) == null){
			ArrayList<Integer> list = new ArrayList<Integer>();
			list.add(cind);
			list.add(aind);
			vmap.put(ge, list);
		}
	}
	
}
