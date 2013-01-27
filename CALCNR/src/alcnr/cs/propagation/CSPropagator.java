package alcnr.cs.propagation;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;

import alcnr.cs.constraint.GenericConstraint;
import alcnr.cs.constraintsystem.ConstraintSystem;
import alcnr.cs.propagation.rule.GenericPropagationRule;
import alcnr.cs.propagation.rule.impl.CSAllVarRule;
import alcnr.cs.propagation.rule.impl.CSConjunctionRule;
import alcnr.cs.propagation.rule.impl.CSDisjunctionRule;
import alcnr.cs.propagation.rule.impl.CSEQuantificationRule;
import alcnr.cs.propagation.rule.impl.CSLNumberRestrictionRule;
import alcnr.cs.propagation.rule.impl.CSUQuantificationRule;
import alcnr.element.individual.impl.AtomicIndividual;
import alcnr.element.variable.impl.AtomicVariable;

public class CSPropagator {
	
	static final ArrayList<GenericPropagationRule> PlainRules = new ArrayList<GenericPropagationRule>();
	static final ArrayList<GenericPropagationRule> NDRules = new ArrayList<GenericPropagationRule>();
	static final ArrayList<GenericPropagationRule> GenRules = new ArrayList<GenericPropagationRule>();
	
	static{
		PlainRules.add(new CSConjunctionRule());
		PlainRules.add(new CSUQuantificationRule());
		PlainRules.add(new CSAllVarRule());
		NDRules.add(new CSDisjunctionRule());
		GenRules.add(new CSEQuantificationRule());		
		GenRules.add(new CSLNumberRestrictionRule());		
	}

	private CSPropagator(){	}
	
	public static PropagationNode propagate(ConstraintSystem cs){
		
		System.out.println("Propagation Begin:");
		PropagationNode root = new PropagationNode(cs);
		
		LinkedList<PropagationNode> queue = new LinkedList<PropagationNode>();
		queue.addFirst(root);
		PropagationNode node = null;
		
		boolean isRuleApplied = false;
		int j = 30;
		
		do{
			node = queue.removeLast();
			printNode(node);
			ArrayList<GenericConstraint> clashes = node.cs.getClashes();
			if(clashes.size() > 0){
				System.out.println("Clash!");
				clashes = node.cs.removeClashes(clashes);
				if(clashes.size() > 0){
					System.out.println("Clash Couldn't removed!");
					continue;
				}
			}
			
			isRuleApplied = false;
			
			ListIterator<AtomicIndividual> ail = null;
			AtomicIndividual ai = null;
			ListIterator<AtomicVariable> avl = null;
			AtomicVariable av = null;
			
			outer:{
				ail = node.cs.getIndividuals();
				while(ail.hasNext()) {
					
					ai = ail.next();
					for(GenericPropagationRule rule: PlainRules) {
						
						ArrayList<PropagationNode> resultNodes = rule.apply(node, ai);
						if(resultNodes.size() > 0) {
							for(PropagationNode newPnode: resultNodes){
								queue.addFirst(newPnode);
							}							
							isRuleApplied = true;
							break outer;
						}
					}
					
					for(GenericPropagationRule rule: NDRules) {
						ArrayList<PropagationNode> resultNodes = rule.apply(node, ai);
						if(resultNodes.size() > 0) {
							for(PropagationNode newPnode: resultNodes){
								queue.addFirst(newPnode);
							}							
							isRuleApplied = true;
							break outer;
						}
					}
				}
				
				ail = node.cs.getIndividuals();
				ai = null;
				
				while(ail.hasNext()) {
					ai = ail.next();
					for(GenericPropagationRule rule: GenRules) {
						
						ArrayList<PropagationNode> resultNodes = rule.apply(node, ai);
						if(resultNodes.size() > 0) {
							for(PropagationNode newPnode: resultNodes){
								queue.addFirst(newPnode);
							}
							
							isRuleApplied = true;
							break outer;
						}
					}
				}
				
				avl = node.cs.getVariables();
				while(avl.hasNext()) {
					
					av = avl.next();
					for(GenericPropagationRule rule: PlainRules) {
						
						ArrayList<PropagationNode> resultNodes = rule.apply(node, av);
						if(resultNodes.size() > 0) {
							for(PropagationNode newPnode: resultNodes){
								queue.addFirst(newPnode);
							}
							
							isRuleApplied = true;
							break outer;
						}
					}
					
					for(GenericPropagationRule rule: NDRules) {
						
						ArrayList<PropagationNode> resultNodes = rule.apply(node, av);
						if(resultNodes.size() > 0) {
							for(PropagationNode newPnode: resultNodes){
								queue.addFirst(newPnode);
							}							

							isRuleApplied = true;
							break outer;
						}
					}
				}
				
				avl = node.cs.getVariables();
				av = null;
				
				while(avl.hasNext()) {
					av = avl.next();
					for(GenericPropagationRule rule: GenRules) {
						
						ArrayList<PropagationNode> resultNodes = rule.apply(node, av);
						if(resultNodes.size() > 0) {
							for(PropagationNode newPnode: resultNodes){
								queue.addFirst(newPnode);
							}							

							isRuleApplied = true;
							//System.out.println("LNUMBER APPLIED");
							break outer;
						}
					}
				}
				
			}
			
			if(!isRuleApplied) System.out.println("[No Rule Applied]");
			j = j-1;
			
		}while(isRuleApplied && !queue.isEmpty());
		
		return null;
		
	}

	private static void printNode(PropagationNode node) {
		System.out.println("=======================");
		System.out.println(node.usedRule);
		System.out.println(node);
		if(node.cs.getClashes().size() > 0){
			System.out.println(node.cs.getClashes().size()+" Clash:");
			for(GenericConstraint clash: node.cs.getClashes()){
				System.out.println("    " + clash);
			}
		}
		System.out.println("=======================");
	}
	
}
