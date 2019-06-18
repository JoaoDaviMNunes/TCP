package business.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import business.*;

public class ProductTest {
	
	Product product;
	User evaluator;
	User solicitor;
	EvaluationGroup group;
	Evaluation evaluation;
	ProductCategory category1;
	ProductCategory category2;

	@Before
	public void setUp() throws Exception {

		evaluator = new User();
		solicitor = new User();
		group = new EvaluationGroup();
		category1 = new ProductCategory("DUWANG");
		product = new Product(5,solicitor,"MORIOH",category1,group);
		
		
	}

	@Test
	public void test() {
		evaluation = new Evaluation(group,product,evaluator);
		product.addEvaluation(evaluation);
		assertFalse(product.evaluationDone());
		
		product.addScore(evaluator, 2);
	}
	
	@Test
	public void test1() {
		evaluation = new Evaluation(group,product,evaluator);
		product.addEvaluation(evaluation);
		assertFalse(product.evaluationDone());
		
		product.addScore(evaluator, 2);
		assertEquals(product.getAverageScore(), new Double(2.00));
		assertTrue(product.isAcceptable());
		
		product.addScore(evaluator, 3);
		
		assertEquals(product.getAverageScore(), new Double(3.00));
			
	}
	@Test
	public void test2() {
		
		
		
		for(int i=0;i<6;i++) {
			User evaluator1 = new User();
			User evaluator2 = new User();
			Evaluation evaluation1  = new Evaluation(group,product,evaluator1);
			
			Evaluation evaluation2  = new Evaluation(group,product,evaluator2);
			
			
			product.addEvaluation(evaluation1);
			product.addEvaluation(evaluation2);
			
			product.addScore(evaluator1, -3);
			product.addScore(evaluator2, 1);
			
		}
		
		BigDecimal ExpectedAverage = new BigDecimal(-1.00).setScale(2);
		BigDecimal CalculatedAverage = new BigDecimal(product.getAverageScore()).setScale(2);
		
		
		assertTrue(ExpectedAverage.compareTo(CalculatedAverage) == 0);
		assertTrue(product.evaluationDone());
		
		
		
	}
	
	

}
