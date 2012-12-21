package alcnr.element.concept.impl;

import alcnr.element.GenericElement;
import alcnr.element.concept.GenericConcept;

public class DisjunctionConcept extends GenericConcept {
	
	public GenericConcept C, D;
	
	public DisjunctionConcept(GenericConcept C, GenericConcept D) {
		// TODO Auto-generated constructor stub
		this.C = C;
		this.D = D;
	}

	public DisjunctionConcept() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addChild(GenericElement ge, int childIndex) {
		// TODO Auto-generated method stub
		if (childIndex == 0 && ge instanceof GenericConcept) {
			this.C = (GenericConcept) ge;
		} else if (childIndex == 1 && ge instanceof GenericConcept){
			this.D = (GenericConcept) ge;
		}
		
	}
	
	@Override
	public String toString() {
		String strC = ( C == null) ? "null" : C.toString();
		String strD = ( D == null) ? "null" : D.toString();
		return "(" + strC + " \\or " + strD + ")";
	}

}
