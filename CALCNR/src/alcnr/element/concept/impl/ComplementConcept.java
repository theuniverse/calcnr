package alcnr.element.concept.impl;

import alcnr.element.GenericElement;
import alcnr.element.concept.GenericConcept;

public class ComplementConcept extends GenericConcept {

	public GenericConcept C;

	public ComplementConcept(GenericConcept C) {
		// TODO Auto-generated constructor stub
		this.C = C;
	}

	public ComplementConcept() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addChild(GenericElement ge, int childIndex) {
		if (childIndex == 0 && ge instanceof GenericConcept) {
			this.C = (GenericConcept) ge;
		}
	}

	@Override
	public String toString() {
		return "\\not" + this.C;
	}
	
	@Override
	public boolean equals(Object o){
		
		if(o == null) return false;
		
		if(!(o instanceof ComplementConcept))
			return false;
		
		ComplementConcept oc = (ComplementConcept)o;
		if(C != oc.C) return false;
		//System.out.println(C+","+oc.C);
		if(C!= null && oc.C != null && !oc.C.equals(C)) return false;
		
		return true;
	}
	
}
