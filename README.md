# junit5-log-test-stub
A junit 5 stub to help unit test log events and statements 


See the following blog post for more information: https://www.callibrity.com/articles/articles/unit-testing-log-statements-with-junit5-a-comprehensive-guide

The jar file can be pulled in and used via Maven Central Repository.
```
<dependency>
  <groupId>com.callibrity.logging</groupId>
  <artifactId>log-tracker</artifactId>
  <version>1.0.3</version>
</dependency>
```

Quick Summary
CUT ( Class Under Test ) . 
In order to illustrate how to use the new JUnit 5 Extension model to unit test logging statements, let’s pretend we have the follow CUT.


public class CUT {

	Logger logger = LoggerFactory.getLogger(CUT.class);
	
	public void doNothingButLog() {
		logger.debug("Inside CUT.doNothingButLog");
	}
	
	public void doNothingButLogTwo() {
		logger.info("Start CUT.doNothingButLogTwo");

		logger.info("End CUT.doNothingButLogTwo");
	}
}


Use the LogTrackerStub, which is a JUnit 5 Extension enabled class.  It is used as follows:


public class CUTTest {

	@RegisterExtension
	LogTrackerStub logTrackerStub = LogTrackerStub.create().recordForLevel(LogTracker.LogLevel.INFO)
		.recordForType(CUT.class);
	

	@Test
	public void testCUTMethodOne() {
		logTrackerStub.recordForLevel(LogTracker.LogLevel.DEBUG);
		CUT classUnderTest = new CUT();
		classUnderTest.doNothingButLog();
		
		assertTrue(logTrackerStub.contains("Inside CUT.doNothingButLog"));
		assertEquals(1, logTrackerStub.size());
	}
	
	@Test
	public void testCUTMethodTwo() {
		CUT classUnderTest = new CUT();
		classUnderTest.doNothingButLogTwo();
		assertTrue(logTrackerStub.contains("Start CUT.doNothingButLogTwo"));
		assertTrue(logTrackerStub.contains("End CUT.doNothingButLogTwo"));
		assertEquals(2, logTrackerStub.size());

	}

}
