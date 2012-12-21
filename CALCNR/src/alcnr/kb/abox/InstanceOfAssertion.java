package alcnr.kb.abox;

import alcnr.element.GenericElement;
import alcnr.element.concept.GenericConcept;
import alcnr.element.individual.GenericIndividual;

public class InstanceOfAssertion extends Assertion {
	
	public GenericConcept C;
	public GenericIndividual a;
	
	@Override
	public void addChild(GenericElement ge, int childIndex) {
		// TODO Auto-generated method stub
		if(childIndex == 0 && ge instanceof GenericConcept){
			this.C = (GenericConcept) ge;
		} else if (childIndex == 1 && ge instanceof GenericIndividual){
			this.a = (GenericIndividual) ge;
		}
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.C+"(" + this.a + ")";
	}
	
}
