package alcnr.kb.parser.rule.impl;

import java.util.ArrayList;
import java.util.Hashtable;

import alcnr.element.GenericElement;
import alcnr.kb.ALCNRKB;
import alcnr.kb.parser.rule.GenericRule;
import alcnr.kb.tbox.InclusionStatement;

public class TStatementRule extends GenericRule {

	public TStatementRule(){
		this.fromPattern = "S";
		this.toPattern = "C \\includeof C";
	}

	@Override
	public GenericElement createElement(ALCNRKB kb) {
		// TODO Auto-generated method stub
		//return null;
		return new InclusionStatement();
	}

	@Override
	public void addMap(int index, Hashtable<Integer, GenericElement> map,
			Hashtable<GenericElement, ArrayList<Integer>> vmap,
			GenericElement ge) {
		// TODO Auto-generated method stub
		int cind = index, dind = index+13;
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
