package business.test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import business.ProductCategory;

public class ProductCategoryTest {
	ProductCategory categoryTest1;
	ProductCategory categoryTest2;
	
	String nome1 = "SABONETE";

	@Before
	public void setUp() throws Exception {
		categoryTest1 = new ProductCategory(nome1);
		categoryTest2 = new ProductCategory(null);
	}

	@Test
	public void test1() {
		assertTrue(categoryTest1.compareName(nome1));
		
	}
	
	@Test
	public void test2() {
		assertFalse(categoryTest2.compareName(nome1));
	}

}
