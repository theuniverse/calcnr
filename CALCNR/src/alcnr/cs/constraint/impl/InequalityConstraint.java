package alcnr.cs.constraint.impl;

import alcnr.cs.constraint.GenericConstraint;
import alcnr.element.GenericObject;

public class InequalityConstraint extends GenericConstraint{
	
	public GenericObject s, t;
	
	public InequalityConstraint(GenericObject s, GenericObject t){
		this.s = s;
		this.t = t;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return s + " != " + t;
	}

}
