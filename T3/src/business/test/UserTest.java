package business.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import business.User;

public class UserTest {

	User user;
	@Before
	public void setUp() throws Exception {
		user = new User();
	}

	@Test
	public void test1() {
		User evaluator = new User(5,"MARIA","SP");
	}
	@Test
	public void test2() {
		User evaluator = new User(5,"O'REILY","SP");
	}
	
	@Test
	public void test5() {
		User evaluator = new User(5,"GONÇALVES","SP");
		
	}
	
	@Test
	public void test7() {
		User evaluator = new User(5,"DAMIÃO GÜNNER","SP");
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void test3() {
		User evaluator = new User(5,"MARIA++","SP");
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void test4() {
		User evaluator = new User(5,"MARIA0","SP");
		
	}
	
	
	
	@Test(expected = IllegalArgumentException.class)
	public void test6() {
		User evaluator = new User(5,"MARIA","OL");
		
	}

}
