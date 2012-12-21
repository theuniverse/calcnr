package alcnr.kb.tbox;

import alcnr.element.GenericElement;
import alcnr.element.concept.GenericConcept;

public class InclusionStatement extends Statement {

	public GenericConcept C, D;

	@Override
	public void addChild(GenericElement ge, int childIndex) {
		// TODO Auto-generated method stub
		if(childIndex == 0 && ge instanceof GenericConcept){
			this.C = (GenericConcept) ge;
		}else if(childIndex == 1 && ge instanceof GenericConcept){
			this.D = (GenericConcept) ge;
		}
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.C + " \\includeof " + this.D;
	}
	
}
