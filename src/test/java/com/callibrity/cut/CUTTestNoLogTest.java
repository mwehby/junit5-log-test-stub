package com.callibrity.cut;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class CUTTestNoLogTest {

	
	@Test
	public void testSUTMethodOne() {
		CUT classUnderTest = new CUT();
		classUnderTest.doNothingButLog();
		assertNotNull(classUnderTest);
	}
	
	@Test
	public void testSUTMethodTwo() {
		CUT classUnderTest = new CUT();
		classUnderTest.doNothingButLogTwo();
		assertNotNull(classUnderTest);	
	}
}
