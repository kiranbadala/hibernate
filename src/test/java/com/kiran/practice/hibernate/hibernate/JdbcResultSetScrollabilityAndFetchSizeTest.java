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
public class JdbcResultSetScrollabilityAndFetchSizeTest {
	Logger logger = LoggerFactory.getLogger(JdbcResultSetScrollabilityAndFetchSizeTest.class);

	@Autowired
	private PostService service;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testPerformanceWithDifferentFetchSize() {
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
	
	@Test
	public void testPerformanceWithDifferentResultSetSize() {
		logger.info("Running testPerformanceWithDifferentResultSetSize 1000");
		ElapsedTimeCalculator.start();
		service.fetchRowsWithMaxRows(1000);
		ElapsedTimeCalculator.stop();
		
		
		logger.info("Running testPerformanceWithDifferentResultSetSize 500");
		ElapsedTimeCalculator.start();
		service.fetchRowsWithMaxRows(500);
		ElapsedTimeCalculator.stop();
		
		logger.info("Running testPerformanceWithDifferentResultSetSize 100");
		ElapsedTimeCalculator.start();
		service.fetchRowsWithMaxRows(100);
		ElapsedTimeCalculator.stop();
		
		logger.info("Running testPerformanceWithDifferentResultSetSize 50");
		ElapsedTimeCalculator.start();
		service.fetchRowsWithMaxRows(50);
		ElapsedTimeCalculator.stop();
		
		logger.info("Running testPerformanceWithDifferentResultSetSize 10");
		ElapsedTimeCalculator.start();
		service.fetchRowsWithMaxRows(10);
		ElapsedTimeCalculator.stop();
	}
	
	@Test
	public void testPerformanceWithDifferentResultSetSizingMechanism() {
		logger.info("Running test performance for all rows");
		ElapsedTimeCalculator.start();
		service.fetchAllRows();
		ElapsedTimeCalculator.stop();
		
		logger.info("Running test performance for max rows 100");
		ElapsedTimeCalculator.start();
		service.fetchRowsWithMaxRows(100);
		ElapsedTimeCalculator.stop();
		
		logger.info("Running test performance for query limit 100");
		ElapsedTimeCalculator.start();
		service.fetchRowsWithLimit(100);
		ElapsedTimeCalculator.stop();
	}
	
}
