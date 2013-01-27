package alcnr.kb.parser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;

import alcnr.element.GenericElement;
import alcnr.element.concept.impl.LNumberRestrictionConcept;
import alcnr.element.concept.impl.UNumberRestrictionConcept;
import alcnr.kb.ALCNRKB;
import alcnr.kb.abox.Assertion;
import alcnr.kb.parser.rule.GenericRule;
import alcnr.kb.parser.rule.impl.AInstanceOfRule;
import alcnr.kb.parser.rule.impl.ARelationRule;
import alcnr.kb.parser.rule.impl.CAtomicRule;
import alcnr.kb.parser.rule.impl.CBottomRule;
import alcnr.kb.parser.rule.impl.CComplementRule;
import alcnr.kb.parser.rule.impl.CConjunctionRule;
import alcnr.kb.parser.rule.impl.CDisjunctionRule;
import alcnr.kb.parser.rule.impl.CEQuantificationRule;
import alcnr.kb.parser.rule.impl.CLNumberRestrictionRule;
import alcnr.kb.parser.rule.impl.CTopRule;
import alcnr.kb.parser.rule.impl.CUNumberRestrictionRule;
import alcnr.kb.parser.rule.impl.CUQuantificationRule;
import alcnr.kb.parser.rule.impl.IAtomicRule;
import alcnr.kb.parser.rule.impl.RAtomicRule;
import alcnr.kb.parser.rule.impl.RConjunctionRule;
import alcnr.kb.parser.rule.impl.TStatementRule;
import alcnr.kb.tbox.Statement;

public class Parser {
	
	static final ArrayList<GenericRule> rules = new ArrayList<GenericRule>();
	
	static final String TAG_CONCEPT = ".CONCEPT";
	static final String TAG_ROLE = ".ROLE";
	static final String TAG_INDIVIDUAL = ".INDIVIDUAL";
	static final String TAG_TBOX = ".TBOX";
	static final String TAG_ABOX = ".ABOX";
	static final String TAG_SABOX = ".SABOX";
	
	static final int STATE_CONCEPT = 1;
	static final int STATE_ROLE = 2;
	static final int STATE_INDIVIDUAL = 3;
	static final int STATE_TBOX = 10;
	static final int STATE_ABOX = 20;
	static final int STATE_SABOX = 30;
	static final int STATE_NONE = 0;
	
	static int state = STATE_NONE;
	
	static {
		rules.add(new CBottomRule());
		rules.add(new CTopRule());
		rules.add(new CConjunctionRule());
		rules.add(new CDisjunctionRule());
		rules.add(new CComplementRule());
		rules.add(new CEQuantificationRule());
		rules.add(new CUQuantificationRule());
		rules.add(new CLNumberRestrictionRule());
		rules.add(new CUNumberRestrictionRule());
		rules.add(new RConjunctionRule());
		rules.add(new TStatementRule());
		rules.add(new AInstanceOfRule());
		rules.add(new ARelationRule());		
	}
	
	private Parser(){}
	
	public static ALCNRKB parseFile(String fileAddr) throws Exception{
		
		ALCNRKB kb = new ALCNRKB();
		
		BufferedReader fileReader = null;
		
		try {
			InputStream fis = new FileInputStream(fileAddr);
			fileReader = new BufferedReader(new InputStreamReader(fis));
		} catch (Exception e) {
			throw e;
		}
		
		String temp = null; //int i = 0;
		while((temp = fileReader.readLine()) != null){
			//if(i == 0){
			//	temp = temp.trim().substring(1);
			//}else{
				temp = temp.trim();
			//}
			
			//i++;
			
			if(temp.equals(TAG_CONCEPT)){
				state = STATE_CONCEPT;
				continue;
			}
			if(temp.equals(TAG_ROLE)){
				state = STATE_ROLE;
				continue;
			}
			if(temp.equals(TAG_INDIVIDUAL)){			
				state = STATE_INDIVIDUAL;
				continue;
			}
			if(temp.equals(TAG_TBOX)){
				state = STATE_TBOX;
				continue;
			}
			if(temp.equals(TAG_ABOX)){
				state = STATE_ABOX;
				continue;
			}
			if(temp.equals(TAG_SABOX)){
				state = STATE_SABOX;
				continue;
			}
			
			if(state == STATE_CONCEPT){
				if(!temp.equals("")){
					rules.add(new CAtomicRule(temp));
					kb.addConcept(temp);
				}
			}
			
			if(state == STATE_ROLE){				
				if(!temp.equals("")){
					rules.add(new RAtomicRule(temp));
					kb.addRole(temp);
				}
			}
			
			if(state == STATE_INDIVIDUAL){
				if(!temp.equals("")){
					rules.add(new IAtomicRule(temp));
					kb.addIndividual(temp);
				}
			}
			
			if(state == STATE_TBOX){				
				if(!temp.equals("")){
					kb.addInclusion((Statement)parse(temp, kb));
				}
			}
			
			if(state == STATE_ABOX){
				if(!temp.equals("")){
					kb.addAssertion((Assertion)parse(temp, kb));
				}
			}
			
			if(state == STATE_SABOX){
				if(!temp.equals("")){
					kb.addSpeicalAssertion((Assertion)parse(temp, kb));
				}
			}
		}
		
		fileReader.close();		
		return kb;
		
	}
	
	public static GenericElement parse(String str, ALCNRKB kb){

		//Use
		LinkedList<ParseNode> queue = new LinkedList<ParseNode>();
		
		ParseNode pnode = new ParseNode(str);		
		queue.addFirst(pnode);	//INSERT(p, Q)
		ParseNode q = null;
		
		do{	//repeat			
			q = queue.removeLast();	//q ← REMOVE(Q)			
			for(GenericRule r: rules){	//for each rule A → w in P do
				ArrayList<Integer> indices = r.matches(q);				
				for(Integer i: indices){	//for each decomposition uwv of q with v ∈ Σ* do
					ParseNode node = r.parse(q, i);
					queue.addFirst(node); //INSERT(uAv, Q)
					q.children.add(node);
					node.parent = q; //	Add node uAv as a child of q					
				}	//end for
			}	//end for				
		} while( !q.equals("S") && !queue.isEmpty());	//until q = S or EMPTY(Q)

		Hashtable<Integer, GenericElement> map = new Hashtable<Integer, GenericElement>();
		Hashtable<GenericElement, ArrayList<Integer>> vmap = new Hashtable<GenericElement, ArrayList<Integer>>();
		GenericElement S = null;
		//map.put(0, new GenericElement());

		ParseNode tempNode = q;
		while(tempNode != null){	
			int index = tempNode.index;
			GenericRule rule = tempNode.usedRule;
			System.out.printf("%40s,%20s,%d\n",tempNode.text,tempNode.usedRule,tempNode.index);
			if(rule != null){

				// create node
				GenericElement ge = rule.createElement(kb);
				
				// add restriction number if LNR or UNR
				if(rule instanceof CLNumberRestrictionRule){
					((LNumberRestrictionConcept)ge).addNumber(tempNode.number);
				}
				if(rule instanceof CUNumberRestrictionRule){
					((UNumberRestrictionConcept)ge).addNumber(tempNode.number);
				}

				// append to parent
				GenericElement parent = map.remove(index);
				//System.out.println(parent);
				if(parent != null){
					int child_index = vmap.get(parent).indexOf(index);
					//System.out.println(child_index);
					parent.addChild(ge, child_index);

					// get diff
					int diff = 0;
					if(rule instanceof CLNumberRestrictionRule || rule instanceof CUNumberRestrictionRule){
						int number = tempNode.number, count = 1;
						while(number > 9){
							count++;
							number /= 10;
						}
						diff = count + 6 ;
					}else{
						diff = rule.diff();
					}
					
					// update map
					int key = 0;
					Enumeration<Integer> keys = map.keys();
					ArrayList<Integer> tempList = new ArrayList<Integer>();
					while(keys.hasMoreElements()){
						key = keys.nextElement();
						if( key > index ) {
							//System.out.println(key );
							tempList.add(key);
						}
					}
					for(Integer i: tempList){						
						GenericElement temp = map.remove(i);
						//System.out.println(i+","+(i+diff)+","+diff+","+temp);
						map.put(i+diff, temp);
						ArrayList<Integer> tpList = vmap.get(temp);
						listReplace(tpList, i, diff);
					}
				} else {
					S = ge;					
				}
				// add new map element
				tempNode.usedRule.addMap(index, map, vmap, ge);
			}
			/*System.out.print("Map:[");
			for(Entry<Integer, GenericElement> e : map.entrySet()){
				System.out.print("(" + e.getKey() + ":" + e.getValue() + ") ");
			}
			System.out.println("]");
			if(S != null){
				System.out.println("Root:" + S.toString());
			}
			System.out.println("+++++++++++++++++++++++++++++++");*/
			tempNode = tempNode.parent;	
		}

		return S;
	}

	private static void listReplace(ArrayList<Integer> tpList, int key, int diff) {
		for(int i = 0; i < tpList.size(); i++ ) {
			if(tpList.get(i) == key){
				tpList.set(i, key + diff);
				break;
			}
		}
	}
	
}
