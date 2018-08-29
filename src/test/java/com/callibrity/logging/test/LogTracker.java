package com.callibrity.logging.test;

import java.util.List;
import java.util.Vector;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;

public class LogTracker {

	public enum LogLevel {
		TRACE(Level.TRACE), 
		DEBUG(Level.DEBUG), 
		INFO(Level.INFO), 
		WARN(Level.WARN), 
		ERROR(Level.ERROR);
	
		Level internalLevel;
	
		private LogLevel(Level level) {
			this.internalLevel = level;
		}
	}

	
	private final ListAppender<ILoggingEvent> listAppender = new ListAppender<ILoggingEvent>();

	private final LoggerContext loggerContext = (LoggerContext) LoggerFactory
			.getILoggerFactory();

	private final Vector<Class<?>> loggingSources = new Vector<Class<?>>();

	private LogTracker.LogLevel level = LogTracker.LogLevel.TRACE;

	
	
	
	public static LogTracker create() {
		return new LogTracker();
	}
	
	public LogTracker recordForLevel(LogTracker.LogLevel level) {
		this.level = level;
		resetLoggingFramework();
		prepareLoggingFramework();
		return this;
	}

	public LogTracker recordForObject(Object sut) {
		Class<?> type = sut.getClass();
		recordForType(type);
		return this;
	}

	public LogTracker recordForType(Class<?> type) {
		loggingSources.add(type);
		addAppenderToType(type);
		return this;
	}

	public boolean contains(String loggingStatement) {
		List<ILoggingEvent> list = listAppender.list;
		for (ILoggingEvent event : list) {
			if (event.getFormattedMessage().contains(loggingStatement)) {
				return true;
			}
		}
		return false;
	}
	
	public int size() {
		return listAppender.list.size();
	}
	
	void resetLoggingFramework() {
		listAppender.stop();
		resetLoggingContext();
	}
	
	void prepareLoggingFramework() {
		resetLoggingContext();
	    addAppenderToLoggingSources();
	    listAppender.start();
	}
	
	private void addAppenderToLoggingSources() {
		for (Class<?> logSource : loggingSources) {
			addAppenderToType(logSource);
		}
	}

	private void addAppenderToType(Class<?> type) {
		Logger logger = (Logger) LoggerFactory.getLogger(type);
		logger.addAppender(listAppender);
		logger.setLevel(level.internalLevel);
	}
	
	private void resetLoggingContext() {
		loggerContext.reset();
	}
	
}
