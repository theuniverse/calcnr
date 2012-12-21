package alcnr.kb.parser.rule.impl;

import java.util.ArrayList;
import java.util.Hashtable;

import alcnr.element.GenericElement;
import alcnr.element.concept.impl.ComplementConcept;
import alcnr.kb.ALCNRKB;
import alcnr.kb.parser.rule.GenericRule;

public class CComplementRule extends GenericRule {

	public CComplementRule(){
		this.fromPattern = "C";
		this.toPattern = "\\notC";
	}

	@Override
	public GenericElement createElement(ALCNRKB kb) {
		//return null;
		return new ComplementConcept();
	}

	@Override
	public void addMap(int index, Hashtable<Integer, GenericElement> map,
			Hashtable<GenericElement, ArrayList<Integer>> vmap,
			GenericElement ge) {
		// TODO Auto-generated method stub
		int cind = index+4;
		map.put(cind, ge);
		if(vmap.get(ge) == null){
			ArrayList<Integer> list = new ArrayList<Integer>();
			list.add(cind);
			vmap.put(ge, list);
		}
	}
	
}
