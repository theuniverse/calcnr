package alcnr.cs.propagation.rule.impl;

import java.util.ArrayList;

import alcnr.cs.constraint.GenericConstraint;
import alcnr.cs.constraint.impl.MemberConstraint;
import alcnr.cs.constraintsystem.ConstraintSystem;
import alcnr.cs.propagation.PropagationNode;
import alcnr.cs.propagation.rule.GenericPropagationRule;
import alcnr.element.GenericObject;
import alcnr.element.concept.GenericConcept;
import alcnr.element.concept.impl.UQuantificationConcept;
import alcnr.element.role.GenericRole;

public class CSUQuantificationRule extends GenericPropagationRule {

	@Override
	public ArrayList<PropagationNode> apply(PropagationNode node, GenericObject go) {
		// TODO Auto-generated method stub
		ArrayList<PropagationNode> results = new ArrayList<PropagationNode>();
		
		ConstraintSystem cs = node.cs;
		ArrayList<GenericConstraint> gcl = cs.getConstraints(ConstraintSystem.O_MEMBER_UQUANTICATION, go);
		
		for (GenericConstraint gc: gcl) {
			
			UQuantificationConcept cc = (UQuantificationConcept) ((MemberConstraint)gc).C;
			GenericConcept C = cc.C;
			GenericRole R = cc.R;
			//System.out.println(C+":"+R);
			
			for(GenericObject successor: cs.getSuccessor(go, R)){
				//System.out.println(successor);
				if(!cs.containMember(successor, C)){
					PropagationNode result = node.clone();
					result.cs.addConstraint(new MemberConstraint(C, successor));					
					node.children.add(result);
					result.parent = node;
					result.usedRule = this;
					results.add(result);
				}
			}
			
		}
		
		return results;
	}

}
