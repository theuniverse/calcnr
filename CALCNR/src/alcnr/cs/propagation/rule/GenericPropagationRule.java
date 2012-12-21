package alcnr.cs.propagation.rule;

import java.util.ArrayList;

import alcnr.cs.propagation.PropagationNode;
import alcnr.element.GenericObject;

public abstract class GenericPropagationRule {
	
	public abstract ArrayList<PropagationNode> apply(PropagationNode node, GenericObject go);
	
}
