package com.callibrity.logging.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



class LogTrackerStubTest {

	public interface TestInterface {
		public void causeALog();
	}
	
	
	@Test
	void testRecordingInfoLevelLogMessageByType() throws Exception {

		TestInterface underTest = new TestInterface() {
			Logger logger = LoggerFactory.getLogger(TestInterface.class);

			public void causeALog() {
				logger.info("test");
			}
		};

		LogTrackerStub logTrackerStub = LogTrackerStub.create();
		logTrackerStub.recordForLevel(LogTracker.LogLevel.INFO);
		logTrackerStub.recordForType(TestInterface.class);
		logTrackerStub.beforeTestExecution(null);
		underTest.causeALog();
		logTrackerStub.afterTestExecution(null);
		assertTrue(logTrackerStub.contains("test"));
		assertEquals(1, logTrackerStub.size());
	}
	
	@Test
	void testRecordingInfoLevelLogMessageByObject() throws Exception {

		TestInterface underTest = new TestInterface() {
			//note the difference here, I am asking for the runtime class
			//this is so the logger name matches up with what is returned
			//on line 67 in LogTrackerStub
			Logger logger = LoggerFactory.getLogger(this.getClass());

			public void causeALog() {
				logger.info("test");
			}
			
		};

		LogTrackerStub logTrackerStub = LogTrackerStub.create();
		logTrackerStub.recordForLevel(LogTracker.LogLevel.INFO);
		logTrackerStub.recordForObject(underTest);
		logTrackerStub.beforeTestExecution(null);
		underTest.causeALog();
		logTrackerStub.afterTestExecution(null);
		assertTrue(logTrackerStub.contains("test"));
		assertEquals(1, logTrackerStub.size());
	}
	
	@Test
	void testRecordingAtHigherLevelDoesRecordLowerLevel() throws Exception {

		TestInterface underTest = new TestInterface() {
			Logger logger = LoggerFactory.getLogger(TestInterface.class);

			public void causeALog() {
				logger.info("info log statement");
				logger.debug("debug log statement");
			}
		};

		LogTrackerStub logTrackerStub = LogTrackerStub.create();
		logTrackerStub.recordForLevel(LogTracker.LogLevel.INFO);
		logTrackerStub.recordForType(TestInterface.class);
		logTrackerStub.beforeTestExecution(null);
		underTest.causeALog();
		logTrackerStub.afterTestExecution(null);
		assertTrue(logTrackerStub.contains("info log statement"));
		assertFalse(logTrackerStub.contains("debug log statement"));
		assertEquals(1, logTrackerStub.size());
	}
}
