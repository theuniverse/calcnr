package alcnr.cs.propagation.rule.impl;

import java.util.ArrayList;
import java.util.ListIterator;

import alcnr.cs.constraint.GenericConstraint;
import alcnr.cs.constraint.impl.AllVarConstraint;
import alcnr.cs.constraint.impl.MemberConstraint;
import alcnr.cs.constraintsystem.ConstraintSystem;
import alcnr.cs.propagation.PropagationNode;
import alcnr.cs.propagation.rule.GenericPropagationRule;
import alcnr.element.GenericObject;
import alcnr.element.concept.GenericConcept;
import alcnr.element.individual.impl.AtomicIndividual;
import alcnr.element.variable.impl.AtomicVariable;

public class CSAllVarRule extends GenericPropagationRule {

	@Override
	public ArrayList<PropagationNode> apply(PropagationNode node,
			GenericObject go) {
		// TODO Auto-generated method stub
		ArrayList<PropagationNode> results = new ArrayList<PropagationNode>();
		
		ConstraintSystem cs = node.cs;
		ArrayList<GenericConstraint> gcl = cs.getConstraints(ConstraintSystem.V_ALLVAR, null);
		
		for (GenericConstraint gc: gcl) {
			
			GenericConcept C = ((AllVarConstraint)gc).C;
			
			ArrayList<GenericConstraint> newConstraints = new ArrayList<GenericConstraint>();
			
			ListIterator<AtomicIndividual> ail = cs.getIndividuals();
			AtomicIndividual tempAi = null;
			while(ail.hasNext()){
				tempAi = ail.next();
				if(!cs.containMember(tempAi, C)){
					newConstraints.add(new MemberConstraint(C, tempAi));
				}
			}
			
			ListIterator<AtomicVariable> avl = cs.getVariables();
			AtomicVariable tempAv = null;
			while(avl.hasNext()){
				tempAv = avl.next();
				if(!cs.containMember(tempAv, C)){
					newConstraints.add(new MemberConstraint(C, tempAv));
				}
			}
			
			if(newConstraints.size() != 0){			
				PropagationNode result = node.clone();
				for(GenericConstraint newConstraint: newConstraints){
					result.cs.addConstraint(newConstraint);
				}						
				node.children.add(result);
				result.parent = node;
				result.usedRule = this;
				results.add(result);
			}
			
		}
		
		return results;
	}

}
