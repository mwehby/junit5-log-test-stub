package com.callibrity.cut;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * CUT stands for Class Under Test
 *
 */
public class CUT {
	
	Logger logger = LoggerFactory.getLogger(CUT.class);
	
	public void doNothingButLog() {
		logger.debug("Inside CUT.doNothingButLog");
	}
	
	public void doNothingButLogTwo() {
		logger.info("Start CUT.doNothingBugLogTwo");
		
		logger.info("End CUT.doNothingBugLogTwo");
	}
}
