package business.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import business.*;

public class UserTest {

	User evaluator;
	User solicitor;
	ProductCategory category;
	ProductCategory OtherCategory;
	Product product;
	
	EvaluationGroup group;
	@Before
	public void setUp() throws Exception {
		category = new ProductCategory("FOOBAR");
		OtherCategory = new ProductCategory("GANJA");
		
	}

	@Test
	public void userCreation() {
		evaluator = new User(5,"MARIA","SP");
		evaluator = new User(5,"O'REILY","SP");
		evaluator = new User(5,"GONÇALVES","SP");
		evaluator = new User(5,"DAMIÃO GÜNNER","SP");
	}
	
	@Test
	public void canEvaluate1() {
		evaluator = new User(5,"MARIA","SP",Arrays.asList(category));
		solicitor = new User(2, "RICARDO","PE",Arrays.asList(category));
		product = new Product(6,solicitor,"COBOLSOAP",category);
		
		assertTrue(evaluator.canEvaluate(product));
		
		
		
	}
	
	@Test
	public void canEvaluate2() {
		evaluator = new User(5,"MARIA","SP",new ArrayList<>(Arrays.asList(category)));
		solicitor = new User(2, "RICARDO","SP",new ArrayList<>(Arrays.asList(category)));
		product = new Product(6,solicitor,"COBOLSOAP",category);
		
		assertFalse(evaluator.canEvaluate(product));
	}
	
	@Test
	public void canEvaluate3() {
		evaluator = new User(5,"MARIA","SP",new ArrayList<>(Arrays.asList(category)));
		solicitor = new User(2, "RICARDO","RS",new ArrayList<>(Arrays.asList(category)));
		product = new Product(6,solicitor,"COBOLSOAP",OtherCategory);
		
		
		assertFalse(evaluator.canEvaluate(product));
		
		evaluator.addCategory(OtherCategory);
		
		assertTrue(evaluator.canEvaluate(product));	
	}
	
	@Test
	public void canEvaluate4() {
		evaluator = new User(5,"MARIA","SP",new ArrayList<>(Arrays.asList(category)));
		product = new Product(6,evaluator,"COBOLSOAP",category);
		
		assertFalse(evaluator.canEvaluate(product));
	}
	
	@Test
	public void canEvaluate5() {
		evaluator = new User(5,"MARIA","SP",new ArrayList<>(Arrays.asList(category)));
		solicitor = new User(2, "RICARDO","RS",new ArrayList<>(Arrays.asList(category)));
		product = new Product(6,solicitor,"COBOLSOAP",category);
		group = new EvaluationGroup();
		
		assertTrue(evaluator.canEvaluate(product));
		assertEquals(evaluator.getEvaluationCount(group),0);
	
		
		Evaluation evaluation = new Evaluation(group,product,evaluator);
		assertFalse(evaluator.canEvaluate(product));
		assertEquals(evaluator.getEvaluationCount(group),1);
	}
		
	
	@Test(expected = IllegalArgumentException.class)
	public void nameWithSpecialSymbol() {
		User evaluator = new User(5,"MARIA++","SP");
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void nameWithNumber() {
		User evaluator = new User(5,"MARIA0","SP");
		
	}
	
	
	
	@Test(expected = IllegalArgumentException.class)
	public void invalidState() {
		User evaluator = new User(5,"MARIA","OL");
		
	}

}
