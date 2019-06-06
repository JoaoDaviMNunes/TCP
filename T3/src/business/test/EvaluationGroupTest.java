package business.test;

import business.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EvaluationGroupTest {
	
	EvaluationGroup groupA;
	List<User> evaluators = new ArrayList<User>();
	List<Product> products = new ArrayList<Product>();
	

	@BeforeEach
	void setUp() throws Exception {
		
		groupA = new EvaluationGroup("ABC");
		
		
		for(int i = 0; i <= 5; i++) {
			User evaluator = new User(i,"MARIA","SP");
			evaluators.add(evaluator);
			
			Product ProductToAdd = new Product(i,evaluator,"PRODUTO" + i);
			Evaluation EvaluationToAdd = new Evaluation(groupA, ProductToAdd,evaluator,2);
			
			
			
			
			ProductToAdd.addEvaluation(EvaluationToAdd, evaluator);
			products.add( ProductToAdd );
		}
		
		Iterator<Product> EvaluationProductIterator = products.iterator();
		Iterator<User> UserIterator = evaluators.iterator();
		
		while(EvaluationProductIterator.hasNext()) {
			groupA.addEvaluation(EvaluationProductIterator.next(), UserIterator.next());
		}
		
		
	}

	@Test
	void test() {
		
		assertEquals(groupA.getAcceptableProducts().size(),5);
	}

}
