package com.sales.last;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LastminuteApplicationTests {
	
	@Test
	public void Test() {
		LastminuteApplication last = new LastminuteApplication(); 
		
		last.customOpenAPIData();
		
		String[] args = new String[1];
		args[0] = "test";		
		LastminuteApplication.main( args  );
		
		assertNotNull(last);
		assertNotNull(last.getClass());
	}

}
