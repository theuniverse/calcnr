package alcnr.cs.propagation.rule.impl;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import alcnr.cs.constraint.GenericConstraint;
import alcnr.cs.constraint.impl.InequalityConstraint;
import alcnr.cs.constraint.impl.MemberConstraint;
import alcnr.cs.constraintsystem.ConstraintSystem;
import alcnr.cs.propagation.PropagationNode;
import alcnr.cs.propagation.rule.GenericPropagationRule;
import alcnr.element.GenericObject;
import alcnr.element.concept.impl.UNumberRestrictionConcept;
import alcnr.element.individual.impl.AtomicIndividual;
import alcnr.element.role.GenericRole;
import alcnr.element.variable.impl.AtomicVariable;

public class CSUNumberRestrictionRule extends GenericPropagationRule {

	@Override
	public ArrayList<PropagationNode> apply(PropagationNode node,
			GenericObject go) {
		// TODO Auto-generated method stub
		ArrayList<PropagationNode> results = new ArrayList<PropagationNode>();
		
		ConstraintSystem cs = node.cs;		
		ArrayList<GenericConstraint> uNumberRestrictions = cs.getConstraints(ConstraintSystem.O_MEMBER_UNUMBERRESTRICTION, go);
		if(uNumberRestrictions.size() == 0)
			return results;
		
		ArrayList<GenericConstraint> iEquality = cs.getConstraints(ConstraintSystem.O_INEQUAL, null);
		
		for (GenericConstraint gc: uNumberRestrictions) {
			
			UNumberRestrictionConcept cc = (UNumberRestrictionConcept) ((MemberConstraint)gc).C;			
			int n = cc.n;
			GenericRole R = cc.R;
			
			ArrayList<GenericObject> successors = cs.getSuccessor(go, R);
			if(successors.size() <= n){
				return results;
			}			
			
			ArrayList<AtomicIndividual> indis = new ArrayList<AtomicIndividual>();
			ArrayList<AtomicVariable> vars = new ArrayList<AtomicVariable>();
			
			classifyObjects(successors, indis, vars);
			Hashtable<AtomicIndividual, ArrayList<AtomicVariable>> replaceTable = new Hashtable<AtomicIndividual, ArrayList<AtomicVariable>>();
			for(AtomicIndividual indi: indis){
				ArrayList<AtomicVariable> list = new ArrayList<AtomicVariable>();
				for(AtomicVariable var: vars){
					list.add(var);
				}
				if(list.size() != 0){
					replaceTable.put(indi, list);
				}
			}
			
			AtomicIndividual ai = null;
			AtomicVariable av = null;
			for (GenericConstraint ie: iEquality){
				GenericObject s = ((InequalityConstraint)ie).s;
				GenericObject t = ((InequalityConstraint)ie).t;
				if(s instanceof AtomicIndividual && t instanceof AtomicVariable){
					ai = (AtomicIndividual)s;
					av = (AtomicVariable)t;
					ArrayList<AtomicVariable> list = replaceTable.get(ai);
					if(list.contains(av)){
						list.remove(av);
						if(list.size() == 0){
							replaceTable.remove(ai);
						}
					}
				}
				if(t instanceof AtomicIndividual && s instanceof AtomicVariable){
					ai = (AtomicIndividual)t;
					av = (AtomicVariable)s;
					ArrayList<AtomicVariable> list = replaceTable.get(ai);
					if(list.contains(av)){
						list.remove(av);
						if(list.size() == 0){
							replaceTable.remove(ai);
						}
					}
				}
			}
			
			Enumeration<AtomicIndividual> replacees = replaceTable.keys();
			while(replacees.hasMoreElements()){
				ai = replacees.nextElement();
				ArrayList<AtomicVariable> replacers = replaceTable.get(ai);
				for(AtomicVariable replacer: replacers){
					PropagationNode result = node.clone();
					result.cs.replace(replacer, ai);
					node.children.add(result);
					result.parent = node;
					result.usedRule = this;
					results.add(result);
				}
			}
		}
		
		return results;
	}

	private void classifyObjects(ArrayList<GenericObject> successors,
			ArrayList<AtomicIndividual> indis, ArrayList<AtomicVariable> vars) {
		// TODO Auto-generated method stub
		for(GenericObject o: successors){
			if(o instanceof AtomicIndividual){
				indis.add((AtomicIndividual)o);
			}
			if(o instanceof AtomicVariable){
				vars.add((AtomicVariable)o);
			}
		}
	}

}
