package alcnr.cs.propagation.rule.impl;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import alcnr.cs.constraint.GenericConstraint;
import alcnr.cs.constraint.impl.InequalityConstraint;
import alcnr.cs.constraint.impl.MemberConstraint;
import alcnr.cs.constraint.impl.RelationConstraint;
import alcnr.cs.constraintsystem.ConstraintSystem;
import alcnr.cs.propagation.PropagationNode;
import alcnr.cs.propagation.rule.GenericPropagationRule;
import alcnr.element.GenericObject;
import alcnr.element.concept.impl.LNumberRestrictionConcept;
import alcnr.element.role.GenericRole;
import alcnr.element.role.impl.AtomicRole;
import alcnr.element.variable.impl.AtomicVariable;

public class CSLNumberRestrictionRule extends GenericPropagationRule {

	@Override
	public ArrayList<PropagationNode> apply(PropagationNode node,
			GenericObject go) {
		
		ArrayList<PropagationNode> results = new ArrayList<PropagationNode>();
		
		ConstraintSystem cs = node.cs;
		if(go instanceof AtomicVariable){
			boolean hasEquivalence = cs.hasEquivalence((AtomicVariable) go);
			//System.out.println(hasEquivalence);
			if(hasEquivalence) return results;
		}
		
		ArrayList<GenericConstraint> lNumberRestrictions = cs.getConstraints(ConstraintSystem.O_MEMBER_LNUMBERRESTRICTION, go);
		ArrayList<GenericConstraint> iEquality = cs.getConstraints(ConstraintSystem.O_INEQUAL, null);
		//System.out.println("[INEQUALITY CONSTRAINT]: " + iEquality.size());
		
		PropagationNode result = node.clone();
		
		if(lNumberRestrictions.size() == 0)
			return results;
		
		for (GenericConstraint gc: lNumberRestrictions) {
			
			LNumberRestrictionConcept cc = (LNumberRestrictionConcept) ((MemberConstraint)gc).C;
			
			int n = cc.n;
			GenericRole R = cc.R;
			
			//int i = 0, j = 0;
			//System.out.print("[SEPARATED VARIABLE]: ");
			Hashtable<GenericObject, ArrayList<GenericObject>> goh = new Hashtable<GenericObject, ArrayList<GenericObject>>();
			for(GenericObject successor: cs.getSuccessor(go, R)){
				//System.out.print(successor+" ");
				goh.put(successor, new ArrayList<GenericObject>());
			}
			//System.out.println();
			
			for(GenericConstraint ie: iEquality){
				GenericObject s = ((InequalityConstraint)ie).s;
				GenericObject t = ((InequalityConstraint)ie).t;
				ArrayList<GenericObject> temp = null;
				if((temp = goh.get(s)) != null && goh.get(t) != null){
					temp.add(t);
				}
				if((temp = goh.get(t)) != null && goh.get(s) != null){
					temp.add(s);
				}
			}			
			
			while(!isQualified(goh)){
				GenericObject minObject = getMin(goh);
				ArrayList<GenericObject> relatedObjects = goh.remove(minObject);
				for(GenericObject rObject: relatedObjects){
					ArrayList<GenericObject> list = goh.get(rObject);
					//System.out.println(rObject+","+list);
					list.remove(minObject);
					if(list.size() == 0){
						goh.remove(rObject);
					}
				}
			}
			
			Enumeration<GenericObject> left = goh.keys();
			ArrayList<GenericObject> separatedSuccessors = new ArrayList<GenericObject>();
			while(left.hasMoreElements()){
				separatedSuccessors.add(left.nextElement());
			}
			
			//System.out.println("[MAX CONNECT DEGREE]:"+separatedSuccessors.size());
			if(separatedSuccessors.size() >= n){
				//System.out.println("enough");
				return results;
			}				
			
			ArrayList<AtomicVariable> newVariables = new ArrayList<AtomicVariable>();
			ArrayList<AtomicRole> roles = ConstraintSystem.deriveRole(R);
			
			for(int i=separatedSuccessors.size();i < n;i++){				
				AtomicVariable av = result.cs.createVariable();				
				for(AtomicRole ar: roles) {
					result.cs.addConstraint(new RelationConstraint(ar, go, av));
				}				
				newVariables.add(av);		
			}
			
			for(int i=0;i<separatedSuccessors.size();i++) {
				for(int j=0;j<newVariables.size();j++){
					result.cs.addConstraint(new InequalityConstraint(separatedSuccessors.get(i), newVariables.get(j)));
				}
			}
			
			for(int i=0;i<newVariables.size();i++) {
				for(int j=i+1;j<newVariables.size();j++){
					result.cs.addConstraint(new InequalityConstraint(newVariables.get(i), newVariables.get(j)));
				}
			}			
		}
		
		node.children.add(result);
		result.parent = node;
		result.usedRule = this;
		results.add(result);
		
		return results;
	}

	private GenericObject getMin(
			Hashtable<GenericObject, ArrayList<GenericObject>> goh) {
		// TODO Auto-generated method stub
		GenericObject result = null;
		
		int min = Integer.MAX_VALUE;
		Enumeration<GenericObject> keys = goh.keys();
		while(keys.hasMoreElements()){
			GenericObject key = keys.nextElement();
			if(goh.get(key).size() < min){
				//System.out.println(key);
				result = key;
				break;
			}
		}
		
		return result;
	}

	private boolean isQualified(
			Hashtable<GenericObject, ArrayList<GenericObject>> goh) {
		// TODO Auto-generated method stub
		int nodeCount = goh.entrySet().size();
		
		Enumeration<GenericObject> keys = goh.keys();
		while(keys.hasMoreElements()){
			GenericObject key = keys.nextElement();
			if(goh.get(key).size() != nodeCount-1){
				return false;
			}
		}
		
		return true;
	}

}
