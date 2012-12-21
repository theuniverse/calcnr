package alcnr.cs.constraintsystem;

import java.util.ArrayList;
import java.util.ListIterator;

import alcnr.cs.constraint.GenericConstraint;
import alcnr.cs.constraint.impl.AllVarConstraint;
import alcnr.cs.constraint.impl.InequalityConstraint;
import alcnr.cs.constraint.impl.MemberConstraint;
import alcnr.cs.constraint.impl.RelationConstraint;
import alcnr.element.GenericObject;
import alcnr.element.concept.GenericConcept;
import alcnr.element.concept.impl.AtomicConcept;
import alcnr.element.concept.impl.BottomConcept;
import alcnr.element.concept.impl.ComplementConcept;
import alcnr.element.concept.impl.ConjunctionConcept;
import alcnr.element.concept.impl.DisjunctionConcept;
import alcnr.element.concept.impl.EQuantificationConcept;
import alcnr.element.concept.impl.LNumberRestrictionConcept;
import alcnr.element.concept.impl.UNumberRestrictionConcept;
import alcnr.element.concept.impl.UQuantificationConcept;
import alcnr.element.individual.impl.AtomicIndividual;
import alcnr.element.role.GenericRole;
import alcnr.element.role.impl.AtomicRole;
import alcnr.element.role.impl.ConjunctionRole;
import alcnr.element.variable.impl.AtomicVariable;

public class ConstraintSystem {
	
	public static final int O_ALL = -1;

	public static final int O_MEMBER = 0;	
	public static final int O_MEMBER_CONJUNCTION = 1;
	public static final int O_MEMBER_DISJUNCTION = 2;
	public static final int O_MEMBER_UQUANTICATION = 3;
	public static final int O_MEMBER_EQUANTICATION = 4;
	public static final int O_MEMBER_UNUMBERRESTRICTION = 5;
	public static final int O_MEMBER_LNUMBERRESTRICTION = 6;
	
	public static final int O_RELATION = 10;
	
	public static final int O_INEQUAL = 20;
	
	public static final int V_ALLVAR = 30;

	ArrayList<GenericConstraint> constraints;
	ArrayList<GenericConstraint> specialConstraints;
	
	ArrayList<AtomicVariable> orderedVars;
	ArrayList<AtomicIndividual> individuals;

	public ConstraintSystem() {
		constraints = new ArrayList<GenericConstraint>();
		specialConstraints = new ArrayList<GenericConstraint>();
		orderedVars = new ArrayList<AtomicVariable>();
		individuals = new ArrayList<AtomicIndividual>();
	}

	public GenericConstraint getConstraint(int i) {
		GenericConstraint c = constraints.get(i);
		return c;
	}

	public ListIterator<GenericConstraint> getConstraints() {
		return constraints.listIterator();
	}

	public void addConstraint(GenericConstraint c) {
		if (c == null)
			return;
		constraints.add(c);
	}
	
	public GenericConstraint getSpecialConstraint(int i) {
		GenericConstraint c = specialConstraints.get(i);
		return c;
	}

	public ListIterator<GenericConstraint> getSpecialConstraints() {
		return specialConstraints.listIterator();
	}

	public void addSpecialConstraint(GenericConstraint c) {
		if (c == null)
			return;
		specialConstraints.add(c);
	}

	public AtomicVariable getVariable(String name) {
		for (AtomicVariable v : orderedVars) {
			if (v.name == name) {
				return v;
			}
		}
		return null;
	}

	public ListIterator<AtomicVariable> getVariables() {
		return orderedVars.listIterator();
	}

	public AtomicVariable createVariable() {
		int length = orderedVars.size();
		AtomicVariable newVar = new AtomicVariable("y" + (length + 1));
		orderedVars.add(newVar);
		return newVar;
	}

	public void setVariables(ListIterator<AtomicVariable> li) {
		AtomicVariable av = null;
		while (li.hasNext()) {
			av = li.next();
			this.orderedVars.add(av);
		}
	}

	public AtomicIndividual getIndividual(String name) {
		for (AtomicIndividual i : individuals) {
			if (i.individual == name) {
				return i;
			}
		}
		return null;
	}

	public ListIterator<AtomicIndividual> getIndividuals() {
		return individuals.listIterator();
	}

	public void addIndividual(AtomicIndividual ai) {
		if (ai == null)
			return;
		individuals.add(ai);
	}
	
	public boolean isSeparated(GenericObject s, GenericObject t){
		
		//if(s==t) return false;
		ArrayList<GenericConstraint> gcl = getInequalConstraints(s);
		for(GenericConstraint gc: gcl){
			if(((InequalityConstraint)gc).t == t){
				return true;
			}
			if(((InequalityConstraint)gc).s == t){
				return true;
			}
		}
		return false;
	}
	
	public boolean hasEquivalence(AtomicVariable x) {
		
		ListIterator<AtomicVariable> avl = this.getVariables();
		while(avl.hasNext()){
			AtomicVariable av = avl.next();			
			if(av == x) return false;
			if(isEquivalence(av, x)) return true;
		}
		
		return false;		
	}
	
	public boolean isEquivalence(AtomicVariable x, AtomicVariable y) {
		
		ArrayList<GenericConstraint> xCs = getConstraints(O_MEMBER, x);
		ArrayList<GenericConstraint> yCs = getConstraints(O_MEMBER, y);
		
		//System.out.print("X: " + x);
		ArrayList<GenericConcept> xConcept = new ArrayList<GenericConcept>();
		for(GenericConstraint gcx: xCs){
			if(gcx instanceof MemberConstraint){
				xConcept.add(((MemberConstraint)gcx).C);
			}			
		}
		
		//System.out.print("\nY: " + y);
		ArrayList<GenericConcept> yConcept = new ArrayList<GenericConcept>();
		for(GenericConstraint gcy: yCs){
			if(gcy instanceof MemberConstraint){
				yConcept.add(((MemberConstraint)gcy).C);
			}
		}
		
		boolean result = xConcept.containsAll(yConcept) && yConcept.containsAll(xConcept);
		
		return result;
		
	}

	public ArrayList<GenericConstraint> getConstraints(int TYPE, GenericObject o) {

		ArrayList<GenericConstraint> result = new ArrayList<GenericConstraint>();
		
		if (TYPE == O_ALL) {
			result = getAllConstraints(o);
		} else if (TYPE <= O_MEMBER_LNUMBERRESTRICTION && TYPE >= O_MEMBER) {
			result = getMemberConstraints(TYPE, o);
		} else if(TYPE == V_ALLVAR) {
			result =  getAllVarConstraints();
		} else if(TYPE == O_INEQUAL) {
			result = getInequalConstraints(o);
		} else if(TYPE == O_RELATION) {
			result = getRelationConstraints(o);
		}

		return result;
	}

	private ArrayList<GenericConstraint> getAllConstraints(GenericObject o) {
		// SEEMS NOTHING HELPFUL
		ArrayList<GenericConstraint> result = new ArrayList<GenericConstraint>();
		return result;
	}

	private ArrayList<GenericConstraint> getMemberConstraints(int TYPE, GenericObject o) {
		
		ArrayList<GenericConstraint> result = new ArrayList<GenericConstraint>();
		
		for (GenericConstraint gc : constraints) {

			if ((gc instanceof MemberConstraint) && (((MemberConstraint) gc).s == o)) {
				switch (TYPE) {
					case O_MEMBER: {
						result.add(gc);
					}
					break;
					case O_MEMBER_CONJUNCTION: {
						if (((MemberConstraint) gc).C instanceof ConjunctionConcept) {
							result.add(gc);
						}
					}
					break;
					case O_MEMBER_DISJUNCTION: {
						if (((MemberConstraint) gc).C instanceof DisjunctionConcept) {
							result.add(gc);
						}
					}
					break;
					case O_MEMBER_UQUANTICATION: {
						if (((MemberConstraint) gc).C instanceof UQuantificationConcept) {
							result.add(gc);
						}
					}
					break;
					case O_MEMBER_EQUANTICATION: {
						if (((MemberConstraint) gc).C instanceof EQuantificationConcept) {
							result.add(gc);
						}
					}
					break;
					case O_MEMBER_UNUMBERRESTRICTION: {
						if (((MemberConstraint) gc).C instanceof UNumberRestrictionConcept) {
							result.add(gc);
						}
					}
					break;
					case O_MEMBER_LNUMBERRESTRICTION: {
						if (((MemberConstraint) gc).C instanceof LNumberRestrictionConcept) {
							result.add(gc);
						}
					}
					break;
				}

			}
		}
		
		return result;
	}

	private ArrayList<GenericConstraint> getInequalConstraints(GenericObject o) {
		ArrayList<GenericConstraint> result = new ArrayList<GenericConstraint>();
		//System.out.println(o);
		if (o != null) {
			for (GenericConstraint gc : constraints) {
				//System.out.println(gc);
				// && ((((InequalityConstraint) gc).s == o) || (((InequalityConstraint)gc).t == o))
				if ((gc instanceof InequalityConstraint)) {
					if(((InequalityConstraint) gc).s == o){
						result.add(gc);
					}
					if(((InequalityConstraint) gc).t == o){
						result.add(gc);
					}				
				}
			}
		} else {
			for (GenericConstraint gc : constraints) {
				if ((gc instanceof InequalityConstraint)) {
						result.add(gc);
				}
			}
		}
		
		//System.out.println(result.size());
		
		return result;
	}

	private ArrayList<GenericConstraint> getAllVarConstraints() {
		
		ArrayList<GenericConstraint> result = new ArrayList<GenericConstraint>();
		
		for (GenericConstraint gc : constraints) {
			if(gc instanceof AllVarConstraint){
				result.add(gc);
			}
		}
		
		return result;
	}
	
	private ArrayList<GenericConstraint> getRelationConstraints(GenericObject o) {
		ArrayList<GenericConstraint> result = new ArrayList<GenericConstraint>();
		
		if (o != null) {
			for (GenericConstraint gc : constraints) {
				if ((gc instanceof RelationConstraint)) {
					if(((RelationConstraint) gc).s == o){
						result.add(gc);
					}
					if(((RelationConstraint) gc).t == o){
						result.add(gc);
					}				
				}
			}
		} else {
			for (GenericConstraint gc : constraints) {
				if ((gc instanceof RelationConstraint)) {
					result.add(gc);
				}
			}
		}
		
		return result;
	}

	public boolean containMember(GenericObject o, GenericConcept c) {
		for (GenericConstraint gc : constraints) {
			if (gc instanceof MemberConstraint
					&& ((MemberConstraint) gc).s == o
					&& ((MemberConstraint) gc).C.equals(c)) {
				return true;
			}
		}

		return false;
	}
	
	public boolean containRelation(GenericObject s, GenericRole P, GenericObject t) {
		for (GenericConstraint gc : constraints) {
			if (gc instanceof RelationConstraint
					&& ((RelationConstraint)gc).s == s
					&& ((RelationConstraint)gc).P == P
					&& ((RelationConstraint)gc).t == t) {
				return true;
			}
		}

		return false;
	}
	
	public boolean containInequality(GenericObject s, GenericObject t) {
		for (GenericConstraint gc : constraints) {
			if (gc instanceof InequalityConstraint
					&& ((InequalityConstraint)gc).s == s
					&& ((InequalityConstraint)gc).t == t) {
				return true;
			}
		}

		return false;
	}
	
	public boolean containSpecialMember(GenericObject o, GenericConcept c) {
		for (GenericConstraint gc : specialConstraints) {
			if (gc instanceof MemberConstraint
					&& ((MemberConstraint) gc).s == o
					&& ((MemberConstraint) gc).C.equals(c)) {
				return true;
			}
		}

		return false;
	}
	
	public ArrayList<GenericObject> getSuccessor(GenericObject s, GenericRole R){
		
		ArrayList<GenericObject> results = new ArrayList<GenericObject>();
		ArrayList<AtomicRole> roles = deriveRole(R);
		
		for (AtomicIndividual ai: individuals) {
			//System.out.print(s+""+R+""+ai+" ");
			boolean isResult = true;
			for(AtomicRole r: roles){
				if(!containRelation(s, r, ai)){
					isResult = false;
				}
			}
			if(isResult)
				results.add(ai);
		}
		
		for (AtomicVariable av: orderedVars) {
			boolean isResult = true;
			for(AtomicRole r: roles){
				if(!containRelation(s, r, av)){
					isResult = false;
				}
			}
			if(isResult)
				results.add(av);
		}
		
		//System.out.println();
		
		return results;		
		
	}
	
	public ArrayList<GenericConstraint> getClashes(){
		
		ArrayList<GenericConstraint> results = new ArrayList<GenericConstraint>();
		
		for(GenericConstraint gc: constraints) {
			if(gc instanceof MemberConstraint) {
				GenericConcept concept = ((MemberConstraint)gc).C;
				GenericObject s = ((MemberConstraint)gc).s;
				if(concept instanceof ComplementConcept){
					// CHECK COMPLEMENT PARADOX
					if(((ComplementConcept)concept).C instanceof AtomicConcept && this.containMember(s, ((ComplementConcept)concept).C)){
						results.add(gc);
					}
				}else if(concept instanceof UNumberRestrictionConcept){
					int number = ((UNumberRestrictionConcept)concept).n;
					GenericRole R = ((UNumberRestrictionConcept)concept).R;
					ArrayList<GenericObject> successors = this.getSuccessor(s, R);
					if(successors.size() > number) {
						results.add(gc);
					}
				}else if(concept instanceof BottomConcept){
					results.add(gc);
				}
			}
		}
		
		return results;
	}
	
	public ArrayList<GenericConstraint> removeClashes(ArrayList<GenericConstraint> clashedConstraints) {
		
		ArrayList<GenericConstraint> results = new ArrayList<GenericConstraint>();
		
		for(GenericConstraint clash: clashedConstraints) {
			boolean removed = false;
			System.out.println("[TRY TO REMOVE] "+clash);
			GenericConcept concept = ((MemberConstraint)clash).C;
			GenericObject s = ((MemberConstraint)clash).s;
			if(concept instanceof ComplementConcept){
				if(this.containSpecialMember(s, concept)){
					System.out.println("[REMOVED] "+clash);
					removed= true;
				}
				if(this.containSpecialMember(s, ((ComplementConcept)concept).C)){
					System.out.println("[REMOVED] "+clash);
					removed= true;
				}
			}else if(concept instanceof UNumberRestrictionConcept){
				UNumberRestrictionConcept unrConcept = (UNumberRestrictionConcept)concept;
				GenericRole R = unrConcept.R;
				int number = unrConcept.n;
				ListIterator<GenericConstraint> sConstraints = this.getSpecialConstraints();
				while(sConstraints.hasNext()){
					GenericConstraint sc = sConstraints.next();
					if(sc instanceof MemberConstraint && ((MemberConstraint)sc).C instanceof LNumberRestrictionConcept){
						LNumberRestrictionConcept lnrConcept = (LNumberRestrictionConcept)((MemberConstraint)sc).C;
						GenericRole tempR = lnrConcept.R;
						int tempNumber = lnrConcept.n;
						if(tempR == R && tempNumber == number) {
							System.out.println("[REMOVED] "+clash);
							removed= true;
							break;
						}
					}
				}
			}
			
			if(!removed)
				results.add(clash);
		}		
		
		return results;		
	}
	
	public static ArrayList<AtomicRole> deriveRole(GenericRole p) {
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

	public String toString(){
		String str = "";
		for (GenericConstraint gc: constraints) {
			str += "[C]: " + gc + "\n";
		}
		for (GenericConstraint gc: specialConstraints) {
			str += "[S]: " + gc + "\n";
		}
		return str;
	}

	public void replace(AtomicVariable av, AtomicIndividual ai) {
		
		ArrayList<GenericConstraint> varMembers = this.getMemberConstraints(O_ALL, av);
		//ArrayList<GenericConstraint> indMembers = this.getMemberConstraints(O_ALL, ai);
		
		ArrayList<GenericConstraint> varRelation = this.getRelationConstraints(av);
		//ArrayList<GenericConstraint> indRelation = this.getRelationConstraints(ai);
		
		ArrayList<GenericConstraint> varInequality = this.getInequalConstraints(av);
		//ArrayList<GenericConstraint> indInequality =  this.getInequalConstraints(ai);
		
		for(GenericConstraint vMember: varMembers) {
			MemberConstraint mc = (MemberConstraint)vMember;
			if (this.containMember(ai, mc.C)) {
				constraints.remove(vMember);
			} else {
				mc.s = ai;
			}
		}
		
		for(GenericConstraint vRelation: varRelation) {
			RelationConstraint rc = (RelationConstraint)vRelation;
			if (rc.s == av) {
				if(this.containRelation(ai, rc.P, rc.t)) {
					constraints.remove(vRelation);
				} else {
					rc.s = ai;
				}
			} else if ( rc.t == av) {
				if(this.containRelation(rc.s, rc.P, ai)) {
					constraints.remove(vRelation);
				} else {
					rc.t = ai;
				}
			}
		}
		
		for(GenericConstraint vInequality: varInequality) {
			InequalityConstraint ic = (InequalityConstraint)vInequality;
			if (ic.s == av) {
				if(this.containInequality(ai, ic.t)) {
					constraints.remove(vInequality);
				} else {
					ic.s = ai;
				}
			} else if ( ic.t == av) {
				if(this.containInequality(ic.s, ai)) {
					constraints.remove(vInequality);
				} else {
					ic.t = ai;
				}
			}
		}		
		
	}
}
