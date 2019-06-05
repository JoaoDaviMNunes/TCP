package business.test;

import static org.junit.Assert.*;
import business.*;

import org.junit.Before;
import org.junit.Test;



public class EvaluationTest {
	
	Evaluation Evaluation1;
	Evaluation Evaluation2;
	Evaluation Evaluation3;
	Evaluation Evaluation4;
	Evaluation Evaluation5;

	@Before
	public void setUp() throws Exception {
		Evaluation1 = new Evaluation();
		Evaluation2 = new Evaluation();
		Evaluation3 = new Evaluation();
		Evaluation4 = new Evaluation();
		Evaluation5 = new Evaluation();
	}
	
	
	@Test
	public void test1() {
		Evaluation1.setScore(Evaluation1.maxScore);
		assertEquals(Evaluation1.getScore().intValue(),Evaluation1.maxScore);
		assertTrue(Evaluation1.isDone());
	}
	
	@Test
	public void test2() {
		Evaluation2.setScore(Evaluation1.minScore);
		assertEquals(Evaluation2.getScore().intValue(),Evaluation1.minScore);
		assertTrue(Evaluation2.isDone());
	
	}
	@Test
	public void test3() {
		assertFalse(Evaluation3.isDone());
		
	}
	
	@Test
	public void test4() {
		try {
			Evaluation4.setScore(-5);
		}
		
		catch(IllegalArgumentException e) {
			System.out.println(e.getMessage());
			assertFalse(Evaluation4.isDone());
		}
		
	}
	
	@Test
	public void test5() {
		try {
			Evaluation5.setScore(10);
		}
		
		catch(IllegalArgumentException e) {
			System.out.println(e.getMessage());
			assertFalse(Evaluation5.isDone());
		}
	}

}
