package alcnr.kb.parser.rule.impl;

import java.util.ArrayList;
import java.util.Hashtable;

import alcnr.element.GenericElement;
import alcnr.element.role.impl.ConjunctionRole;
import alcnr.kb.ALCNRKB;
import alcnr.kb.parser.rule.GenericRule;

public class RConjunctionRule extends GenericRule {
	
	public RConjunctionRule(){		
		this.fromPattern = "R";
		this.toPattern = "(R and R)";
	}

	@Override
	public GenericElement createElement(ALCNRKB kb) {
		return new ConjunctionRole();
	}

	@Override
	public void addMap(int index, Hashtable<Integer, GenericElement> map,
			Hashtable<GenericElement, ArrayList<Integer>> vmap,
			GenericElement ge) {
		int rind = index+1, pind = index+7;
		map.put(rind, ge);
		map.put(pind, ge);
		if(vmap.get(ge) == null){
			ArrayList<Integer> list = new ArrayList<Integer>();
			list.add(rind);
			list.add(pind);
			vmap.put(ge, list);
		}
	}
}
