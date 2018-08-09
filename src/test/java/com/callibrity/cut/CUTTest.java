package com.callibrity.cut;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import com.callibrity.logging.test.LogTrackerStub;
import com.callibrity.logging.test.LogTrackerStub.LogLevel;




public class CUTTest {


	@RegisterExtension
	LogTrackerStub logTrackerStub = LogTrackerStub.create().recordForLevel(LogLevel.INFO)
		.recordForType(CUT.class);
	


	@Test
	public void testCUTMethodOne() {
		CUT classUnderTest = new CUT();
		classUnderTest.doNothingButLog();
		
		assertTrue(logTrackerStub.contains("Inside CUT.doNothingButLog"));
		assertEquals(1, logTrackerStub.size());
	}
	
	@Test
	public void testCUTMethodTwo() {
		CUT classUnderTest = new CUT();
		classUnderTest.doNothingButLogTwo();
		
		assertTrue(logTrackerStub.contains("Start CUT.doNothingBugLogTwo"));
		assertTrue(logTrackerStub.contains("End CUT.doNothingBugLogTwo"));
		assertEquals(2, logTrackerStub.size());
		
	}

}
