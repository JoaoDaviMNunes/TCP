package business.test;

import static org.junit.Assert.*;
import business.*;
import data.Database;

import org.junit.Before;
import org.junit.Test;



public class EvaluationTest {
	
	Evaluation evaluation;
	Database database;

	@Before
	public void setUp() throws Exception {
		database = new Database();
	}
	
	
	@Test
	public void test1() {
		evaluation = new Evaluation();
		evaluation.setScore(Evaluation.maxScore);
		assertEquals(evaluation.getScore().intValue(),Evaluation.maxScore);
		assertTrue(evaluation.isDone());
	}
	
	@Test
	public void test2() {
		evaluation = new Evaluation();
		evaluation.setScore(evaluation.minScore);
		assertEquals(evaluation.getScore().intValue(),Evaluation.minScore);
		assertTrue(evaluation.isDone());
	
	}
	@Test
	public void test3() {
		evaluation = new Evaluation();
		assertFalse(evaluation.isDone());
		
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void test4() {
			evaluation = new Evaluation();
			evaluation.setScore(-5);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void test5() {
			evaluation = new Evaluation();
			evaluation.setScore(10);

	}
	
	@Test
	public void test6() {
		User user = new User(1,"MARIA","RJ",null);
		EvaluationGroup group = database.getEvaluationGroup("SPF B");
		Product product = new Product(5,user, "PRODUTO0",null,group);
		
		Evaluation evaluation = new Evaluation(group,product,user);
		evaluation.setScore(0);
		
	}

	@Test
	public void test7() {
		User user = new User(1,"MARIA","RJ",null);
		EvaluationGroup group = database.getEvaluationGroup("SPF B");
		Product product = new Product(5,user, "PRODUTO0",null,group);
		
		evaluation = new Evaluation(group,product,null);
		evaluation.setScore(0);
		
	}

	@Test
	public void test8() {
		User user = new User(1,"MARIA","RJ",null);
		EvaluationGroup group = database.getEvaluationGroup("SPF B");
		Product product = new Product(5,user, "PRODUTO0",null,group);
		
		evaluation = new Evaluation(null,product,user);
		evaluation.setScore(0);
		
	}

	@Test
	public void test9() {
		User user = new User(1,"MARIA","RJ",null);
		EvaluationGroup group = database.getEvaluationGroup("SPF B");
		Product product = new Product(5,user, "PRODUTO0",null,group);
		
		evaluation = new Evaluation(group,null,user);
		evaluation.setScore(0);
		
	}
	
	@Test
	public void test10() {
		
		evaluation = new Evaluation(null,null,null);
		evaluation.setScore(0);
		
	}
	
	
}
