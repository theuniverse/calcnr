package alcnr.element.concept.impl;

import alcnr.element.GenericElement;
import alcnr.element.concept.GenericConcept;
import alcnr.element.role.GenericRole;

public class EQuantificationConcept extends GenericConcept{
	
	public GenericRole R;
	public GenericConcept C;
	
	public EQuantificationConcept(GenericRole R, GenericConcept C) {
		this.R = R;
		this.C = C;
	}
	
	public EQuantificationConcept(){
		
	}

	@Override
	public void addChild(GenericElement ge, int childIndex) {
		if(childIndex == 0 && ge instanceof GenericRole){
			this.R = (GenericRole) ge;
		} else if (childIndex == 1 && ge instanceof GenericConcept){
			this.C = (GenericConcept) ge;
		}
	}
	
	@Override
	public String toString() {
		//return null;
		return "\\exists"+this.R+"."+this.C;
	}

}
