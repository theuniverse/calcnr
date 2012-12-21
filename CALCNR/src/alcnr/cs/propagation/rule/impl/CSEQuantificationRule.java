package alcnr.cs.propagation.rule.impl;

import java.util.ArrayList;

import alcnr.cs.constraint.GenericConstraint;
import alcnr.cs.constraint.impl.MemberConstraint;
import alcnr.cs.constraint.impl.RelationConstraint;
import alcnr.cs.constraintsystem.ConstraintSystem;
import alcnr.cs.propagation.PropagationNode;
import alcnr.cs.propagation.rule.GenericPropagationRule;
import alcnr.element.GenericObject;
import alcnr.element.concept.GenericConcept;
import alcnr.element.concept.impl.EQuantificationConcept;
import alcnr.element.role.GenericRole;
import alcnr.element.role.impl.AtomicRole;
import alcnr.element.variable.impl.AtomicVariable;

public class CSEQuantificationRule extends GenericPropagationRule{

	@Override
	public ArrayList<PropagationNode> apply(PropagationNode node,
			GenericObject go) {
		// TODO Auto-generated method stub
		ArrayList<PropagationNode> results = new ArrayList<PropagationNode>();
		
		ConstraintSystem cs = node.cs;
		if(go instanceof AtomicVariable){
			boolean hasEquivalence = cs.hasEquivalence((AtomicVariable) go);
			//System.out.println(hasEquivalence);
			if(hasEquivalence) return results;
		}
		
		ArrayList<GenericConstraint> gcl = cs.getConstraints(ConstraintSystem.O_MEMBER_EQUANTICATION, go);
		
		for (GenericConstraint gc: gcl) {
			
			EQuantificationConcept cc = (EQuantificationConcept) ((MemberConstraint)gc).C;
			GenericConcept C = cc.C;
			GenericRole R = cc.R;
			
			for(GenericObject successor: cs.getSuccessor(go, R)){
				if(cs.containMember(successor, C)){
					return results;
				}
			}
			
			AtomicVariable av = cs.createVariable();
			
			PropagationNode result = node.clone();
			result.cs.addConstraint(new MemberConstraint(C, av));
			ArrayList<AtomicRole> roles = ConstraintSystem.deriveRole(R);
			for(AtomicRole ar: roles) {
				result.cs.addConstraint(new RelationConstraint(ar, go, av));
			}
			node.children.add(result);
			result.parent = node;
			result.usedRule = this;
			results.add(result);
			
		}
		
		return results;
	}

}
