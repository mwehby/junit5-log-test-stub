package com.callibrity.logging.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



class LogTrackerTest {

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


		LogTracker logTracker = LogTracker.create();
		logTracker.recordForLevel(LogTracker.LogLevel.INFO);
		logTracker.recordForType(TestInterface.class);
		
		underTest.causeALog();
		
		assertTrue(logTracker.contains("test"));
		assertEquals(1, logTracker.size());
	}
	
	@Test
	void testRecordingInfoLevelLogMessageByObject() throws Exception {

		TestInterface underTest = new TestInterface() {
			//note the difference here, I am asking for the runtime class
			//this is so the logger name matches up with what is returned
			//on line 67 in LogTracker
			Logger logger = LoggerFactory.getLogger(this.getClass());

			public void causeALog() {
				logger.info("test");
			}
			
		};

		LogTracker logTracker = LogTracker.create();
		logTracker.recordForLevel(LogTracker.LogLevel.INFO);
		logTracker.recordForObject(underTest);
		underTest.causeALog();
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

		LogTracker logTracker = LogTracker.create();
		logTracker.recordForLevel(LogTracker.LogLevel.INFO);
		logTracker.recordForType(TestInterface.class);
		underTest.causeALog();
		assertTrue(logTracker.contains("info log statement"));
		assertFalse(logTracker.contains("debug log statement"));
		assertEquals(1, logTracker.size());
	}
}
