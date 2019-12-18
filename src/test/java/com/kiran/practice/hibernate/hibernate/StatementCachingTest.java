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
public class StatementCachingTest {
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
	public void test1FetchWithoutClientCache() {
		logger.info("Running test1FetchWithoutClientCache");
		ElapsedTimeCalculator.start();
		service.fetchRecordsWithoutStatementCache();
		ElapsedTimeCalculator.stop();
	}
	
	@Test
	public void test2FetchWithClientCache() {
		logger.info("Running test2FetchWithClientCache");
		ElapsedTimeCalculator.start();
		service.fetchRecordsWithStatementCache();
		ElapsedTimeCalculator.stop();
	}
}
