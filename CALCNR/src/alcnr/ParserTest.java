package alcnr;

import alcnr.cs.constraintsystem.ConstraintSystem;
import alcnr.cs.convertor.ALCNRtoCSConvertor;
import alcnr.cs.propagation.CSPropagator;
import alcnr.kb.ALCNRKB;
import alcnr.kb.parser.Parser;

public class ParserTest {

	public static void main(String[] args){
		
		ALCNRKB kb;
		try {
			kb = Parser.parseFile("files/mao.txt");
			ConstraintSystem cs = ALCNRtoCSConvertor.convertKB(kb);	
			//ListIterator<GenericConstraint> constraints =  cs.getConstraints();
			/*GenericConstraint c = null;
			for(;constraints.hasNext();){
				c = constraints.next();
				System.out.println("constraint: " + c);
			}*/
			//System.out.println(kb);
			//ArrayList<GenericConstraint> constraints1 = cs.getConstraints(ConstraintSystem.V_ALLVAR, null);
			//System.out.println("Constraint for FRIEND"+ constraints1.size());
			//for(GenericConstraint gc: constraints1){
			//	System.out.println(gc);
			//}

			CSPropagator.propagate(cs);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
