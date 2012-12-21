package alcnr.kb.parser.rule.impl;

import java.util.ArrayList;
import java.util.Hashtable;

import alcnr.element.GenericElement;
import alcnr.element.concept.impl.DisjunctionConcept;
import alcnr.kb.ALCNRKB;
import alcnr.kb.parser.rule.GenericRule;

public class CDisjunctionRule extends GenericRule {
	
	public CDisjunctionRule(){		
		this.fromPattern = "C";
		this.toPattern = "(C \\or C)";
	}
	
	public GenericElement createElement(ALCNRKB kb){
		return new DisjunctionConcept();
	}

	public void addMap(int index, Hashtable<Integer, GenericElement> map, Hashtable<GenericElement, ArrayList<Integer>> vmap, GenericElement ge) {
		int cind = index+1, dind = index+7;
		map.put(cind, ge);
		map.put(dind, ge);
		if(vmap.get(ge) == null){
			ArrayList<Integer> list = new ArrayList<Integer>();
			list.add(cind);
			list.add(dind);
			vmap.put(ge, list);
		}
	}
	
}
