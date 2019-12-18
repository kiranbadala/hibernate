package com.kiran.practice.hibernate.hibernate;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.kiran.practice.hibernate.hibernate.service.PostService;
import com.kiran.practice.hibernate.hibernate.util.ElapsedTimeCalculator;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ResultSetScrollabilityAndFetchSizeTest {
	Logger logger = LoggerFactory.getLogger(StatementCachingTest.class);

	@Autowired
	private PostService service;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFetchSize() {
		logger.info("Running testFetchSize with fetch size 4");
		ElapsedTimeCalculator.start();
		service.fetchRecordsWithFetchSize(4);
		ElapsedTimeCalculator.stop();
		
		logger.info("Running testFetchSize with fetch size 8");
		ElapsedTimeCalculator.start();
		service.fetchRecordsWithFetchSize(8);
		ElapsedTimeCalculator.stop();
		
		logger.info("Running testFetchSize with fetch size 16");
		ElapsedTimeCalculator.start();
		service.fetchRecordsWithFetchSize(16);
		ElapsedTimeCalculator.stop();
/*		
		logger.info("Running testFetchSize with fetch size 31");
		ElapsedTimeCalculator.start();
		service.fetchRecordsWithFetchSize(31);
		ElapsedTimeCalculator.stop();
		
		logger.info("Running testFetchSize with fetch size 62");
		ElapsedTimeCalculator.start();
		service.fetchRecordsWithFetchSize(62);
		ElapsedTimeCalculator.stop();
		
		logger.info("Running testFetchSize with fetch size 125");
		ElapsedTimeCalculator.start();
		service.fetchRecordsWithFetchSize(125);
		ElapsedTimeCalculator.stop();
		
		logger.info("Running testFetchSize with fetch size 250");
		ElapsedTimeCalculator.start();
		service.fetchRecordsWithFetchSize(250);
		ElapsedTimeCalculator.stop();
		
		logger.info("Running testFetchSize with fetch size 500");
		ElapsedTimeCalculator.start();
		service.fetchRecordsWithFetchSize(500);
		ElapsedTimeCalculator.stop();
*/
		
	}
}
