package alcnr.kb.parser.rule.impl;

import java.util.ArrayList;
import java.util.Hashtable;

import alcnr.element.GenericElement;
import alcnr.element.concept.impl.EQuantificationConcept;
import alcnr.kb.ALCNRKB;
import alcnr.kb.parser.rule.GenericRule;

public class CEQuantificationRule extends GenericRule {

	public CEQuantificationRule(){
		this.fromPattern = "C";
		this.toPattern = "\\existsR.C";
	}

	@Override
	public GenericElement createElement(ALCNRKB kb) {
		//return null;
		return new EQuantificationConcept();
	}

	@Override
	public void addMap(int index, Hashtable<Integer, GenericElement> map,
			Hashtable<GenericElement, ArrayList<Integer>> vmap,
			GenericElement ge) {
		// TODO Auto-generated method stub
		int rind = index+7, cind = index+9;
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
