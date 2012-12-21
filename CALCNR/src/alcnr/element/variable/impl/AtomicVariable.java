package alcnr.element.variable.impl;

import alcnr.element.variable.GenericVariable;

public class AtomicVariable extends GenericVariable {
	
	public String name;

	public AtomicVariable(String name) {
		// TODO Auto-generated constructor stub
		this.name = name;
	}
	
	public AtomicVariable(){
		
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name;
	}

}
