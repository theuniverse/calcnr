package alcnr.cs.constraint.impl;

import alcnr.cs.constraint.GenericConstraint;
import alcnr.element.GenericObject;
import alcnr.element.concept.GenericConcept;

public class MemberConstraint extends GenericConstraint {

	public GenericConcept C;
	public GenericObject s;
	
	public MemberConstraint(GenericConcept C, GenericObject s){
		this.C = C;
		this.s = s;
	}
	
	@Override
	public String toString() {
		return this.s+":"+this.C;
	}
	
}
