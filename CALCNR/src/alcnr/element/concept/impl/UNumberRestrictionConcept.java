package alcnr.element.concept.impl;

import alcnr.element.GenericElement;
import alcnr.element.concept.GenericConcept;
import alcnr.element.role.GenericRole;

public class UNumberRestrictionConcept extends GenericConcept {
	
	public Integer n;
	public GenericRole R;
	
	public UNumberRestrictionConcept(int n, GenericRole R) {
		// TODO Auto-generated constructor stub
		this.n = n;
		this.R = R;
	}
	
	public UNumberRestrictionConcept(){
		
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
		return "(\\le" + this.n + " " + this.R + ")";
	}
}
