package alcnr.cs.constraint.impl;

import alcnr.cs.constraint.GenericConstraint;
import alcnr.element.concept.GenericConcept;
import alcnr.element.variable.GenericVariable;

public class AllVarConstraint extends GenericConstraint {
	
	//GenericVariable x;
	public GenericConcept C;

	public AllVarConstraint(GenericVariable x, GenericConcept C) {
		this.C = C;
	}

	@Override
	public String toString() {
		return "\\allx.x:" + C;
	}

}
