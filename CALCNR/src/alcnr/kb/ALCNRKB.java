package alcnr.kb;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.ListIterator;

import alcnr.element.concept.impl.AtomicConcept;
import alcnr.element.concept.impl.BottomConcept;
import alcnr.element.concept.impl.TopConcept;
import alcnr.element.individual.impl.AtomicIndividual;
import alcnr.element.role.impl.AtomicRole;
import alcnr.kb.abox.Assertion;
import alcnr.kb.tbox.Statement;

public class ALCNRKB {
	
	private static final TopConcept TOP_CONCEPT = new TopConcept();
	private static final BottomConcept BOTTOM_CONCEPT = new BottomConcept();

	private Hashtable<String, AtomicConcept> concepts;
	private Hashtable<String, AtomicRole> roles;
	private Hashtable<String, AtomicIndividual> individuals;
	
	private ArrayList<Statement> tbox;
	private ArrayList<Assertion> abox;
	private ArrayList<Assertion> sabox;
	
	public ALCNRKB(){
		tbox = new ArrayList<Statement>();
		abox = new ArrayList<Assertion>();
		sabox = new ArrayList<Assertion>();
		concepts = new Hashtable<String, AtomicConcept>();
		roles = new Hashtable<String, AtomicRole>();
		individuals = new Hashtable<String, AtomicIndividual>();
	}
	
	public Statement getInclusion(int i){
		Statement s = tbox.get(i);
		return s;
	}
	
	public ListIterator<Statement> getInclusions(){
		return tbox.listIterator();		
	}
	
	public void addInclusion(Statement s){
		if(s == null) return;
		tbox.add(s);
	}
	
	public Statement removeInclusion(int i){
		if( i < 0 || i >= tbox.size() ) return null;
		else return tbox.remove(i);
	}
	
	public Statement removeInclusion(Statement s){
		if(s == null) return null;
		if(abox.remove(s)) return s;
		else return null;
	}
	
	public Assertion getAssertion(int i){
		Assertion s = abox.get(i);
		return s;
	}
	
	public ListIterator<Assertion> getAssertions(){
		return abox.listIterator();		
	}
	
	public void addAssertion(Assertion a){
		if(a == null) return;
		abox.add(a);
	}
	
	public Assertion removeAssertion(int i){
		if( i < 0 || i >= abox.size() ) return null;
		else return abox.remove(i);
	}
	
	public Assertion removeAssertion(Assertion s){
		if(s == null ) return null;
		if(abox.remove(s)) return s;
		else return null;
	}
	
	public Assertion getSpeicalAssertion(int i){
		Assertion s = sabox.get(i);
		return s;
	}
	
	public ListIterator<Assertion> getSpeicalAssertions(){
		return sabox.listIterator();		
	}
	
	public void addSpeicalAssertion(Assertion a){
		if(a == null) return;
		sabox.add(a);
	}
	
	public Assertion removeSpeicalAssertion(int i){
		if( i < 0 || i >= sabox.size() ) return null;
		else return sabox.remove(i);
	}
	
	public Assertion removeSpeicalAssertion(Assertion s){
		if(s == null ) return null;
		if(sabox.remove(s)) return s;
		else return null;
	}
	
	public AtomicConcept getConcept(String name){
		AtomicConcept s = concepts.get(name);
		return s;
	}
	
	public void addConcept(String name){
		if(name == null) return;
		concepts.put(name, new AtomicConcept(name));
	}
	
	public AtomicConcept removeConcept(String name){
		if(name == null) return null;
		return concepts.remove(name);
	}
	
	public AtomicRole getRole(String name){
		AtomicRole r = roles.get(name);
		return r;
	}
	
	public void addRole(String name){
		if(name == null) return;
		roles.put(name, new AtomicRole(name));
	}
	
	public AtomicRole removeRole(String name){
		if(name == null) return null;
		return roles.remove(name);
	}
	
	public AtomicIndividual getIndividual(String name){
		AtomicIndividual r = individuals.get(name);
		return r;
	}
	
	public void addIndividual(String name){
		if(name == null) return;
		individuals.put(name, new AtomicIndividual(name));
	}
	
	public AtomicIndividual removeIndividual(String name){
		if(name == null) return null;
		return individuals.remove(name);
	}

	public TopConcept getTopConcept() {
		return TOP_CONCEPT;
	}
	
	public BottomConcept getBottomConcept(){
		return BOTTOM_CONCEPT;
	}
	
}
