package alcnr.element.individual.impl;

import alcnr.element.GenericElement;
import alcnr.element.individual.GenericIndividual;

public class AtomicIndividual extends GenericIndividual {

	public String individual;

	public AtomicIndividual(String indi) {
		// TODO Auto-generated constructor stub
		this.individual = indi;
	}

	@Override
	public void addChild(GenericElement ge, int childIndex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return individual;
	}
	
}
