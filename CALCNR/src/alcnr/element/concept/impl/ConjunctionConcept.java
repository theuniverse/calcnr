package alcnr.element.concept.impl;

import alcnr.element.GenericElement;
import alcnr.element.concept.GenericConcept;

public class ConjunctionConcept extends GenericConcept{

	public GenericConcept C, D;
	
	public ConjunctionConcept(GenericConcept C, GenericConcept D) {
		this.C = C;
		this.D = D;
	}

	public ConjunctionConcept() {
	}
	
	public String toString() {
		String strC = ( C == null) ? "null" : C.toString();
		String strD = ( D == null) ? "null" : D.toString();
		return "(" + strC + " \\and " + strD + ")";
	}
	
	@Override
	public void addChild(GenericElement ge, int childIndex) {
		if (childIndex == 0 && ge instanceof GenericConcept) {
			this.C = (GenericConcept) ge;
		} else if (childIndex == 1 && ge instanceof GenericConcept){
			this.D = (GenericConcept) ge;
		}		
	}
	
}
