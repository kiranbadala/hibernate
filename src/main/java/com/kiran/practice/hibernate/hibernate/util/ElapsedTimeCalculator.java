package com.kiran.practice.hibernate.hibernate.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

//Run these tests individually to see actual difference of execution time.

public class ElapsedTimeCalculator {

	static Logger logger = LoggerFactory.getLogger(ElapsedTimeCalculator.class);
	private static StopWatch stopWatch = null;

	public static void start() {
		stopWatch = new StopWatch();
		stopWatch.start();
	}

	// Run these tests individually to see actual difference of execution time.

	public static void stop() {
		stopWatch.stop();
		logger.info("********************==========================>Time taken for execution : "
				+ stopWatch.getTotalTimeMillis());
		stopWatch = null;
	}

	public static long getTimeInMills() {
		return stopWatch.getTotalTimeMillis();
	}

	public static double getTimeInSecs() {
		return stopWatch.getTotalTimeSeconds();
	}

}
