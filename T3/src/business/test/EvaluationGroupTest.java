package business.test;

import business.*;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class EvaluationGroupTest {
	
	EvaluationGroup groupA;
	List<User> evaluators = new ArrayList<User>();
	List<Product> products = new ArrayList<Product>();
	

	@Before
	public void setUp() throws Exception {
		
		groupA = new EvaluationGroup("ABC");
		
		
		for(int i = 0; i <= 5; i++) {
			User evaluator = new User(i,"MARIA","SP");
			evaluators.add(evaluator);
			evaluators.add(new User(i+3,"ROBERTO","RS"));
			evaluators.add(new User(i+2,"ALBERTA","RS"));
			evaluators.add(new User(i+5,"RODRIGO","RJ"));
			
			Product ProductToAdd = new Product(i,evaluator,"PRODUTO" + i);
			Evaluation EvaluationToAdd = new Evaluation(groupA, ProductToAdd,evaluator,i/2+0);
			
			
			ProductToAdd.addEvaluation(EvaluationToAdd);
			
			System.out.println(ProductToAdd);
			
			products.add( ProductToAdd );
		}
		
		Iterator<Product> EvaluationProductIterator = products.iterator();
		Iterator<User> UserIterator = evaluators.iterator();
		
		while(EvaluationProductIterator.hasNext()) {
			groupA.addEvaluation(EvaluationProductIterator.next(), UserIterator.next());
		}
		
		
	}

	@Test
	public void test1() {
		System.out.println("Acceptable products:\n");
		System.out.println(groupA.getAcceptableProducts());
		System.out.println("End list acceptable\n");
		
		assertEquals(groupA.getAcceptableProducts().size(),6);
	}
	
	@Test
	public void test2() {
		groupA.allocate(2);
		
	}
	
	
	

}
