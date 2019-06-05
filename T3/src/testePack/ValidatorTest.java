package testePack;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class ValidatorTest  {
	
	Validator valid;
	
	@Before
	public void setUp() throws Exception{
		valid = new Validator();
	}
	
	@Test	
	public void testGroup() {
		assertTrue(valid.isValidGroup());
	}
	
	
}
