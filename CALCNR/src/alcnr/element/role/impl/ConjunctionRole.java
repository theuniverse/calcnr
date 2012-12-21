package alcnr.element.role.impl;

import alcnr.element.GenericElement;
import alcnr.element.role.GenericRole;

public class ConjunctionRole extends GenericRole {
	
	public ConjunctionRole R;
	public AtomicRole P;
	
	@Override
	public void addChild(GenericElement ge, int childIndex) {
		// TODO Auto-generated method stub
		if(childIndex == 0 && ge instanceof ConjunctionRole){
			this.R = (ConjunctionRole) ge;
		}else if(childIndex == 1 && ge instanceof AtomicRole){
			this.P = (AtomicRole) ge;
		}
		
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.R + "and" +this.P;
	}

}
