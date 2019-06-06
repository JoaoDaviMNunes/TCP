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
		try {
			user.setStateOfResidence("SP");
			assertEquals(user.getStateOfResidence(),"SP");
		}
		catch(IllegalArgumentException e) {
			System.out.println(e.getMessage());
			
		}
	}
	@Test
	public void test2() {
		try {
			user.setStateOfResidence("KO");
		}
		catch(IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

}
