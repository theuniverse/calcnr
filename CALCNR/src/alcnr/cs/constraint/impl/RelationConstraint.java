package alcnr.cs.constraint.impl;

import alcnr.cs.constraint.GenericConstraint;
import alcnr.element.GenericObject;
import alcnr.element.role.GenericRole;

public class RelationConstraint extends GenericConstraint {
	
	public GenericObject s,t;
	public GenericRole P;

	public RelationConstraint(GenericRole P, GenericObject s,
			GenericObject t) {
		// TODO Auto-generated constructor stub
		this.s = s;
		this.t = t;
		this.P = P;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return s+P.toString()+t;
	}

}
