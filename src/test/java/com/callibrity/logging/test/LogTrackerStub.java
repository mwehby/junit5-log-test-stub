package com.callibrity.logging.test;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class LogTrackerStub  implements BeforeTestExecutionCallback, AfterTestExecutionCallback
{
	LogTracker logTracker = LogTracker.create();
	
	private LogTrackerStub() {
		//hide the constructor to force the use of the "create" method
	}
	
	@Override
	public void beforeTestExecution(ExtensionContext context) throws Exception {
		logTracker.prepareLoggingFramework();
	}
	
	@Override
	public void afterTestExecution(ExtensionContext context) throws Exception {
		logTracker.resetLoggingFramework();
	}
	
	public static LogTrackerStub create() {
		return new LogTrackerStub();
	}
	
	public LogTrackerStub recordForLevel(LogTracker.LogLevel level) {
		logTracker.recordForLevel(level);
		return this;
	}

	public LogTrackerStub recordForObject(Object sut) {
		logTracker.recordForObject(sut);
		return this;
	}

	public LogTrackerStub recordForType(Class<?> type) {
		logTracker.recordForType(type);
		return this;
	}

	public boolean contains(String loggingStatement) {
		return logTracker.contains(loggingStatement);
	}
	
	public int size() {
		return logTracker.size();
	}

}