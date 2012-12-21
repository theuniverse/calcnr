package alcnr.element.concept.impl;

import alcnr.element.GenericElement;
import alcnr.element.concept.GenericConcept;
import alcnr.element.role.GenericRole;

public class LNumberRestrictionConcept extends GenericConcept {
	
	public Integer n;
	public GenericRole R;
	
	public LNumberRestrictionConcept(int n, GenericRole R) {
		this.n = n;
		this.R = R;
	}
	
	public LNumberRestrictionConcept(){
	}
	
	@Override
	public void addChild(GenericElement ge, int childIndex) {
		if(childIndex == 0 && ge instanceof GenericRole){
			this.R = (GenericRole) ge;
		}
	}
	
	public void addNumber(Integer i){
		if ( i >= 0 ) {
			this.n = i;
		}
	}
	
	@Override
	public String toString() {
		//return null;
		return "(\\ge" + this.n + " " + this.R + ")";
	}
	
}
