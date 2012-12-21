package alcnr.cs.convertor;

import java.util.ArrayList;
import java.util.ListIterator;

import alcnr.cs.constraint.GenericConstraint;
import alcnr.cs.constraint.impl.AllVarConstraint;
import alcnr.cs.constraint.impl.InequalityConstraint;
import alcnr.cs.constraint.impl.MemberConstraint;
import alcnr.cs.constraint.impl.RelationConstraint;
import alcnr.cs.constraintsystem.ConstraintSystem;
import alcnr.element.concept.GenericConcept;
import alcnr.element.concept.impl.AtomicConcept;
import alcnr.element.concept.impl.BottomConcept;
import alcnr.element.concept.impl.ComplementConcept;
import alcnr.element.concept.impl.ConjunctionConcept;
import alcnr.element.concept.impl.DisjunctionConcept;
import alcnr.element.concept.impl.EQuantificationConcept;
import alcnr.element.concept.impl.LNumberRestrictionConcept;
import alcnr.element.concept.impl.TopConcept;
import alcnr.element.concept.impl.UNumberRestrictionConcept;
import alcnr.element.concept.impl.UQuantificationConcept;
import alcnr.element.individual.GenericIndividual;
import alcnr.element.individual.impl.AtomicIndividual;
import alcnr.element.role.GenericRole;
import alcnr.element.role.impl.AtomicRole;
import alcnr.element.role.impl.ConjunctionRole;
import alcnr.element.variable.impl.AtomicVariable;
import alcnr.kb.ALCNRKB;
import alcnr.kb.abox.Assertion;
import alcnr.kb.abox.InstanceOfAssertion;
import alcnr.kb.abox.RelationAssertion;
import alcnr.kb.tbox.InclusionStatement;
import alcnr.kb.tbox.Statement;

public class ALCNRtoCSConvertor {
	
	private ALCNRtoCSConvertor(){	}
	
	public static ConstraintSystem convertKB(ALCNRKB kb){
		
		ConstraintSystem cs = new ConstraintSystem();
		ArrayList<GenericIndividual> indis = new ArrayList<GenericIndividual>(); 
	
		Statement s = null;
		ListIterator<Statement> statements = kb.getInclusions();
		while(statements.hasNext()){
			s = statements.next();
			//System.out.println(s);
			GenericConstraint c = convert(s, kb);
			if ( c != null ) cs.addConstraint(c);
		}
		
		Assertion a = null;
		ListIterator<Assertion> assertions = kb.getAssertions();
		while(assertions.hasNext()){
			a = assertions.next();
			//System.out.println(a);
			ArrayList<GenericConstraint> constraints = convert(a, indis, kb);
			for(GenericConstraint c: constraints){
				if ( c != null ) cs.addConstraint(c);
			}		
		}
		
		a = null;
		ListIterator<Assertion> sassertions = kb.getSpeicalAssertions();
		while(sassertions.hasNext()){
			a = sassertions.next();
			//System.out.println(a);
			ArrayList<GenericConstraint> constraints = convert(a, indis, kb);
			for(GenericConstraint c: constraints){
				if ( c != null ) cs.addSpecialConstraint(c);
			}		
		}
		
		for(GenericIndividual i: indis){
			//System.out.println("individual: "+i);
			if(i instanceof AtomicIndividual){
				cs.addIndividual((AtomicIndividual)i);
			}			
		}
		
		AddInequalityAssertions(indis, cs);
		
		return cs;
		
	}
	
	private static void AddInequalityAssertions(ArrayList<GenericIndividual> indis, ConstraintSystem cs) {
		int length = indis.size();
		for(int i = 0; i < length; i++){
			for(int j = i+1; j < length; j++){
				if(indis.get(i) != indis.get(j)){
					cs.addConstraint(new InequalityConstraint(indis.get(i), indis.get(j)));
				}
			}
		}
	}

	static GenericConstraint convert(Statement s, ALCNRKB kb){
		GenericConcept C = ((InclusionStatement)s).C;
		GenericConcept D = ((InclusionStatement)s).D;
		ComplementConcept notC = new ComplementConcept(C);
		DisjunctionConcept disjunct = new DisjunctionConcept(rewriteConcept(notC, kb), rewriteConcept(D, kb));
		AtomicVariable x = new AtomicVariable();
		return new AllVarConstraint(x, disjunct);
	}
	
	static ArrayList<GenericConstraint> convert(Assertion a, ArrayList<GenericIndividual> indis, ALCNRKB kb){
		
		ArrayList<GenericConstraint> result = new ArrayList<GenericConstraint>();
		
		if(a instanceof InstanceOfAssertion){
			GenericIndividual s = ((InstanceOfAssertion)a).a;
			GenericConcept C = ((InstanceOfAssertion)a).C;
			if (!indis.contains(s)) indis.add(s);
			result.add(new MemberConstraint(rewriteConcept(C, kb), s));
		} else {
			GenericIndividual s = ((RelationAssertion)a).a;
			GenericIndividual t = ((RelationAssertion)a).b;
			GenericRole P = ((RelationAssertion)a).R;
			ArrayList<AtomicRole> Ps = deriveRole(P);
			for(AtomicRole p: Ps){
				result.add(new RelationConstraint(p, s, t));
			}
			if (!indis.contains(s)) indis.add(s);
			if (!indis.contains(t)) indis.add(t);
		}
		
		return result;
	}

	private static ArrayList<AtomicRole> deriveRole(GenericRole p) {
		ArrayList<AtomicRole> result = new ArrayList<AtomicRole>();
		
		GenericRole role = p;
		derive(role, result);
		
		return result;
	}

	private static void derive(GenericRole p, ArrayList<AtomicRole> result) {
		if(p instanceof AtomicRole){
			result.add((AtomicRole)p);
		}else{
			derive(((ConjunctionRole)p).R, result);
			derive(((ConjunctionRole)p).P, result);
		}
	}
	
	private static GenericConcept rewriteConcept(GenericConcept concept, ALCNRKB kb){
		if (!(concept instanceof ComplementConcept))
			return concept;
		
		GenericConcept inner = ((ComplementConcept)concept).C, result = null;
		if (inner instanceof AtomicConcept) {
			return concept;
		} else if(inner instanceof ConjunctionConcept) {
			GenericConcept notC = new ComplementConcept(((ConjunctionConcept)inner).C);
			GenericConcept notD = new ComplementConcept(((ConjunctionConcept)inner).D);
			return new DisjunctionConcept(rewriteConcept(notC, kb), rewriteConcept(notD, kb));
		} else if(inner instanceof DisjunctionConcept) {
			GenericConcept notC = new ComplementConcept(((DisjunctionConcept)inner).C);
			GenericConcept notD = new ComplementConcept(((DisjunctionConcept)inner).D);
			return new ConjunctionConcept(rewriteConcept(notC, kb), rewriteConcept(notD, kb));
		} else if(inner instanceof TopConcept) {
			return kb.getBottomConcept();	
		} else if(inner instanceof BottomConcept) {
			return kb.getTopConcept();
		} else if(inner instanceof ComplementConcept) {
			GenericConcept C = ((ComplementConcept)inner).C;
			return rewriteConcept(C, kb);
		} else if(inner instanceof EQuantificationConcept) {
			GenericConcept notC = new ComplementConcept(((EQuantificationConcept)inner).C);
			GenericRole R = ((EQuantificationConcept)inner).R;
			return new UQuantificationConcept(R, rewriteConcept(notC, kb));
		} else if(inner instanceof UQuantificationConcept) {
			GenericConcept notC = new ComplementConcept(((UQuantificationConcept)inner).C);
			GenericRole R = ((UQuantificationConcept)inner).R;
			return new EQuantificationConcept(R, rewriteConcept(notC, kb));
		} else if(inner instanceof LNumberRestrictionConcept) {
			GenericRole R = ((LNumberRestrictionConcept)inner).R;
			int n = ((LNumberRestrictionConcept)inner).n;
			if (n == 0) {
				return kb.getBottomConcept();
			} else {
				return new UNumberRestrictionConcept(n-1, R);
			}
		} else if(inner instanceof UNumberRestrictionConcept) {
			GenericRole R = ((UNumberRestrictionConcept)inner).R;
			int n = ((UNumberRestrictionConcept)inner).n;
			return new LNumberRestrictionConcept(n+1, R);
		}
		
		return result;
	}
	
}
