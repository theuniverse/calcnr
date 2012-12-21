package alcnr.kb.abox;

import alcnr.element.GenericElement;
import alcnr.element.individual.GenericIndividual;
import alcnr.element.role.GenericRole;

public class RelationAssertion extends Assertion{

	public GenericRole R;
	public GenericIndividual a, b;
	
	@Override
	public void addChild(GenericElement ge, int childIndex) {
		// TODO Auto-generated method stub
		//System.out.println(ge.getClass());
		if(childIndex == 0 && ge instanceof GenericRole){
			this.R = (GenericRole) ge;
		} else if (childIndex == 1 && ge instanceof GenericIndividual){
			//System.out.println("append"+ge);
			this.a = (GenericIndividual) ge;
		} else if (childIndex == 2 && ge instanceof GenericIndividual){
			this.b = (GenericIndividual) ge;
		}
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.R + "(" + this.a + ", " + this.b + ")";
	}
	
}
