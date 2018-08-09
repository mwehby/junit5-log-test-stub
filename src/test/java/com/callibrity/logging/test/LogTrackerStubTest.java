package com.callibrity.logging.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.callibrity.logging.test.LogTrackerStub.LogLevel;

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

		LogTrackerStub logTracker = LogTrackerStub.create();
		logTracker.recordForLevel(LogLevel.INFO);
		logTracker.recordForType(TestInterface.class);
		logTracker.beforeTestExecution(null);
		underTest.causeALog();
		logTracker.afterTestExecution(null);
		assertTrue(logTracker.contains("test"));
		assertEquals(1, logTracker.size());
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

		LogTrackerStub logTracker = LogTrackerStub.create();
		logTracker.recordForLevel(LogLevel.INFO);
		logTracker.recordForType(TestInterface.class);
		logTracker.beforeTestExecution(null);
		underTest.causeALog();
		logTracker.afterTestExecution(null);
		assertTrue(logTracker.contains("info log statement"));
		assertFalse(logTracker.contains("debug log statement"));
		assertEquals(1, logTracker.size());
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

		LogTrackerStub logTracker = LogTrackerStub.create();
		logTracker.recordForLevel(LogLevel.INFO);
		logTracker.recordForObject(underTest);
		logTracker.beforeTestExecution(null);
		underTest.causeALog();
		logTracker.afterTestExecution(null);
		assertTrue(logTracker.contains("test"));
		assertEquals(1, logTracker.size());
	}
}
