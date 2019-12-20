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

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JdbcTransactionsPerformanceTest {

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
	public void testTransactionIsolationLevel() {
		service.fetchTransactionIsolationLevel();
	}
}
