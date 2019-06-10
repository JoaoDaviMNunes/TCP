package business.test;

import business.*;
import data.Database;

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
	Database database;
	

	@Before
	public void setUp() throws Exception {
		
		database = new Database();
		
		groupA = new EvaluationGroup("ABC");
		
		
		for(int i = -3; i <= 8; i++) {
			evaluators.add(new User(i+4,"ROBERTO","RS"));
			evaluators.add(new User(i+6,"ALBERTA","RS"));
			evaluators.add(new User(i+5,"RODRIGO","RJ"));
			
			
			
			
			for(User user : evaluators) {
				Product ProductToAdd = new Product(i+3,user,"PRODUTO" + i);
				Evaluation EvaluationToAdd;
				
				try {
					EvaluationToAdd = new Evaluation(groupA, ProductToAdd,user,i);
				}
				catch(IllegalArgumentException e) {
					EvaluationToAdd = new Evaluation(groupA, ProductToAdd,user,-2);
					
				}
				
				
				ProductToAdd.addEvaluation(EvaluationToAdd);
				
				System.out.println(ProductToAdd);
				
				products.add( ProductToAdd );
				
				
			}
			
		}
		
		
	}

	@Test
	public void test1() {
		groupA.getAcceptableProducts();
		groupA.getNotAcceptableProducts();
		
		assertTrue(groupA.getAcceptableProducts().size() > 0);
		assertTrue(groupA.getNotAcceptableProducts().size() > 0);
	}
	
	@Test
	public void test2() {
		EvaluationGroup group = database.getEvaluationGroup("SPF A");
		group.allocate(2);
		assertTrue(group.isAllocated());
		assertFalse(group.evaluationDone());
		assertTrue(group.getAcceptableProducts().size() == 0);
		assertTrue(group.getNotAcceptableProducts().size() == 0);
	}
	
	@Test
	public void test3() {
		EvaluationGroup group = database.getEvaluationGroup("SPF A");
		group.allocate(1);
		assertFalse(group.isAllocated());
		assertFalse(group.evaluationDone());
	}
	
	@Test
	public void test4() {
		EvaluationGroup group = database.getEvaluationGroup("SPF A");
		group.allocate(5);
		assertTrue(group.isAllocated());
		assertFalse(group.evaluationDone());
		assertTrue(group.getAcceptableProducts().size() == 0);
		assertTrue(group.getNotAcceptableProducts().size() == 0);
	}
	
	@Test
	public void test5() {
		EvaluationGroup group = database.getEvaluationGroup("SPF A");
		group.allocate(6);
		assertFalse(group.isAllocated());
		assertFalse(group.evaluationDone());
		assertTrue(group.getAcceptableProducts().size() == 0);
		assertTrue(group.getNotAcceptableProducts().size() == 0);
	}
	
	@Test
	public void test6() {
		EvaluationGroup group = database.getEvaluationGroup("SPF B");
		assertTrue(group.isAllocated());
		assertTrue(group.evaluationDone());
	}
	
	@Test
	public void test7() {
		EvaluationGroup group = database.getEvaluationGroup("SPF C");
		assertTrue(group.isAllocated());
		assertFalse(group.evaluationDone());
	}
	
	@Test
	public void test8() {
		EvaluationGroup group = database.getEvaluationGroup("SPF B");
		group.addMember(null);
		assertTrue(group.isAllocated());
		assertTrue(group.evaluationDone());
		
		assertTrue(group.getAcceptableProducts().size() > 0);
		assertTrue(group.getNotAcceptableProducts().size() > 0);
		
	}
	
	@Test
	public void test9() {
		EvaluationGroup group = database.getEvaluationGroup("SPF B");
		group.addMember(new User(0,null,null,null));
		assertTrue(group.isAllocated());
		assertTrue(group.evaluationDone());
		
		assertTrue(group.getAcceptableProducts().size() > 0);
		assertTrue(group.getNotAcceptableProducts().size() > 0);
		
	}
	
	
	
	
	

}
