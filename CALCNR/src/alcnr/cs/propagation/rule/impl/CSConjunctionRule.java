package alcnr.cs.propagation.rule.impl;

import java.util.ArrayList;

import alcnr.cs.constraint.GenericConstraint;
import alcnr.cs.constraint.impl.MemberConstraint;
import alcnr.cs.constraintsystem.ConstraintSystem;
import alcnr.cs.propagation.PropagationNode;
import alcnr.cs.propagation.rule.GenericPropagationRule;
import alcnr.element.GenericObject;
import alcnr.element.concept.GenericConcept;
import alcnr.element.concept.impl.ConjunctionConcept;

public class CSConjunctionRule extends GenericPropagationRule {

	@Override
	public ArrayList<PropagationNode> apply(PropagationNode node, GenericObject go) {
		
		ArrayList<PropagationNode> results = new ArrayList<PropagationNode>();
		
		ConstraintSystem cs = node.cs;
		ArrayList<GenericConstraint> gcl = cs.getConstraints(ConstraintSystem.O_MEMBER_CONJUNCTION, go);
		
		for (GenericConstraint gc: gcl) {
			
			ConjunctionConcept cc = (ConjunctionConcept) ((MemberConstraint)gc).C;
			GenericConcept C = cc.C, D = cc.D;
			boolean containC = cs.containMember(go, C), containD = cs.containMember(go, D);
			if(!containC || !containD){
				PropagationNode result = node.clone();
				if(!containC){
					result.cs.addConstraint(new MemberConstraint(C, go));
				}
				if(!containD){
					result.cs.addConstraint(new MemberConstraint(D, go));
				}
				
				node.children.add(result);
				result.parent = node;
				result.usedRule = this;
				results.add(result);
				break;
			}
			
		}
		
		return results;
		
	}

}
