package alcnr.element.role.impl;

import alcnr.element.GenericElement;
import alcnr.element.role.GenericRole;

public class AtomicRole extends GenericRole{
	
	String role;

	public AtomicRole(String role) {
		this.role = role;
	}

	@Override
	public void addChild(GenericElement ge, int childIndex) {
		// NOTHING
	}

	@Override
	public String toString() {
		return role;
	}

}
