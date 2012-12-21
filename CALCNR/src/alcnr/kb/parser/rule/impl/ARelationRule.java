package alcnr.kb.parser.rule.impl;

import java.util.ArrayList;
import java.util.Hashtable;

import alcnr.element.GenericElement;
import alcnr.kb.ALCNRKB;
import alcnr.kb.abox.RelationAssertion;
import alcnr.kb.parser.rule.GenericRule;

public class ARelationRule extends GenericRule {

	public ARelationRule(){
		this.fromPattern = "S";
		this.toPattern = "R(a, a)";
	}

	@Override
	public GenericElement createElement(ALCNRKB kb) {
		return new RelationAssertion();
	}

	@Override
	public void addMap(int index, Hashtable<Integer, GenericElement> map,
			Hashtable<GenericElement, ArrayList<Integer>> vmap,
			GenericElement ge) {
		int rind = index, aind = index+2, bind = index+5;
		map.put(rind, ge);
		map.put(aind, ge);
		map.put(bind, ge);
		if(vmap.get(ge) == null){
			ArrayList<Integer> list = new ArrayList<Integer>();
			list.add(rind);
			list.add(aind);
			list.add(bind);
			vmap.put(ge, list);
		}
	}
	
}
