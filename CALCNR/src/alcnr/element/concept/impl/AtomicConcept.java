package alcnr.element.concept.impl;

import alcnr.element.GenericElement;
import alcnr.element.concept.GenericConcept;

public class AtomicConcept extends GenericConcept{
		
	String concept;
	
	public AtomicConcept(String concept) {
		this.concept = concept;
	}

	public String toString(){
		return concept;
	}

	@Override
	public void addChild(GenericElement ge, int childIndex) {
		
	}
	
}
