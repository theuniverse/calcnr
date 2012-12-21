package alcnr.kb.parser.rule.impl;

import java.util.ArrayList;
import java.util.Hashtable;

import alcnr.element.GenericElement;
import alcnr.element.concept.impl.UQuantificationConcept;
import alcnr.kb.ALCNRKB;
import alcnr.kb.parser.rule.GenericRule;

public class CUQuantificationRule extends GenericRule {

	public CUQuantificationRule(){
		this.fromPattern = "C";
		this.toPattern = "\\allR.C";
	}

	@Override
	public GenericElement createElement(ALCNRKB kb) {
		// TODO Auto-generated method stub
		return new UQuantificationConcept();
	}

	@Override
	public void addMap(int index, Hashtable<Integer, GenericElement> map,
			Hashtable<GenericElement, ArrayList<Integer>> vmap,
			GenericElement ge) {
		// TODO Auto-generated method stub
		int rind = index+4, cind = index+6;
		map.put(rind, ge);
		map.put(cind, ge);
		if(vmap.get(ge) == null){
			ArrayList<Integer> list = new ArrayList<Integer>();
			list.add(rind);
			list.add(cind);
			vmap.put(ge, list);
		}
	}
	
}
