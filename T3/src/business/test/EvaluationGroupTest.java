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
		
		
		for(int i = -3; i <= 4; i++) {
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
				
				products.add( ProductToAdd );
				
				
			}
			
		}
		
		
	}

	@Test
	public void randomGroupTest() {
		groupA.getAcceptableProducts();
		groupA.getNotAcceptableProducts();
		
		assertTrue(groupA.getAcceptableProducts().size() > 0);
		assertTrue(groupA.getNotAcceptableProducts().size() > 0);
	}
	
	@Test
	public void allocationLowerBoundTest() {
		EvaluationGroup group = database.getEvaluationGroup("SPF A");
		group.allocate(2);
		assertTrue(group.isAllocated());
		assertFalse(group.evaluationDone());
		assertTrue(group.getAcceptableProducts().size() == 0);
		assertTrue(group.getNotAcceptableProducts().size() == 0);
	}
	
	@Test
	public void allocationBelowMinimum() {
		EvaluationGroup group = database.getEvaluationGroup("SPF A");
		group.allocate(1);
		assertFalse(group.isAllocated());
		assertFalse(group.evaluationDone());
	}
	
	@Test
	public void allocationHigherBoundTest() {
		EvaluationGroup group = database.getEvaluationGroup("SPF A");
		group.allocate(5);
		assertTrue(group.isAllocated());
		assertFalse(group.evaluationDone());
		assertTrue(group.getAcceptableProducts().size() == 0);
		assertTrue(group.getNotAcceptableProducts().size() == 0);
	}
	
	@Test
	public void allocationAboveMaximum() {
		EvaluationGroup group = database.getEvaluationGroup("SPF A");
		group.allocate(6);
		assertFalse(group.isAllocated());
		assertFalse(group.evaluationDone());
		assertTrue(group.getAcceptableProducts().size() == 0);
		assertTrue(group.getNotAcceptableProducts().size() == 0);
	}
	
	@Test
	public void isSPFBAlocatted() {
		EvaluationGroup group = database.getEvaluationGroup("SPF B");
		assertTrue(group.isAllocated());
		assertTrue(group.evaluationDone());
	}
	
	@Test
	public void isSPFCAlocatted() {
		EvaluationGroup group = database.getEvaluationGroup("SPF C");
		group.allocate(5); //Garantir que grupo já alocado não causa falhas se há outra tentativa de alocação
		assertTrue(group.isAllocated());
		assertFalse(group.evaluationDone());
	}
	
	@Test
	public void ProductListWithNullMember() {
		EvaluationGroup group = database.getEvaluationGroup("SPF B");
		group.addMember(null);
		assertTrue(group.isAllocated());
		assertTrue(group.evaluationDone());
		
		assertTrue(group.getAcceptableProducts().size() > 0);
		assertTrue(group.getNotAcceptableProducts().size() > 0);
		
	}
	
	@Test
	public void ProductListWithNullAttributesMember() {
		EvaluationGroup group = database.getEvaluationGroup("SPF B");
		group.addMember(new User(0,null,null,null));
		assertTrue(group.isAllocated());
		assertTrue(group.evaluationDone());
		
		assertTrue(group.getAcceptableProducts().size() > 0);
		assertTrue(group.getNotAcceptableProducts().size() > 0);
		
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void addEvaluationArgumentException1(){
		EvaluationGroup group = database.getEvaluationGroup("SPF B");
		
		Product NullProduct = null;
		Evaluation NullEvaluation = null;
		
		group.addEvaluation( NullProduct,  NullEvaluation);
		
		
		
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void addEvaluationArgumentException2(){
		EvaluationGroup group = database.getEvaluationGroup("SPF B");
		
		Product NullProduct = null;
		User NullUser = null;
		
		group.addEvaluation( NullProduct,  NullUser);
		
		
		
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void addUnallocatedProductArgumentException() {
		EvaluationGroup group = database.getEvaluationGroup("SPF B");
		
		group.AddUnallocatedProduct(null);
		
	}
	
	
	
	
	
	

}
