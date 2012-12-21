package alcnr.cs.propagation;

import java.util.ArrayList;
import java.util.ListIterator;

import alcnr.cs.constraint.GenericConstraint;
import alcnr.cs.constraintsystem.ConstraintSystem;
import alcnr.cs.propagation.rule.GenericPropagationRule;
import alcnr.element.individual.impl.AtomicIndividual;
import alcnr.element.variable.impl.AtomicVariable;

public class PropagationNode {

	public ConstraintSystem cs;
	
	public GenericPropagationRule usedRule;
	
	public PropagationNode parent = null;
	public ArrayList<PropagationNode> children = new ArrayList<PropagationNode>();
	
	public PropagationNode(ConstraintSystem cs){
		this.cs = cs;
	}
	
	public PropagationNode(){}
	
	public PropagationNode clone(){
		
		PropagationNode node = new PropagationNode();
		
		node.cs = new ConstraintSystem();
		
		ListIterator<GenericConstraint> constraints = cs.getConstraints();
		GenericConstraint gc = null;
		while(constraints.hasNext()){
			gc = constraints.next();
			node.cs.addConstraint(gc);
		}
		
		ListIterator<GenericConstraint> sconstraints = cs.getSpecialConstraints();
		gc = null;
		while(sconstraints.hasNext()){
			gc = sconstraints.next();
			node.cs.addSpecialConstraint(gc);
		}
		
		ListIterator<AtomicVariable> ovars = cs.getVariables();
		node.cs.setVariables(ovars);
		
		ListIterator<AtomicIndividual> idis = cs.getIndividuals();
		while(idis.hasNext()){
			node.cs.addIndividual(idis.next());
		}
		
		return node;
		
	}
	
	public String toString(){
		return cs.toString();
	}
}
