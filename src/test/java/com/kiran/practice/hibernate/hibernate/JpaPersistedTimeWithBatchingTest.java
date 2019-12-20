package com.kiran.practice.hibernate.hibernate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.kiran.practice.hibernate.hibernate.service.PostService;
import com.kiran.practice.hibernate.hibernate.util.ElapsedTimeCalculator;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JpaPersistedTimeWithBatchingTest {

	Logger logger = LoggerFactory.getLogger(JpaPersistedTimeWithBatchingTest.class);

	@Autowired
	private PostService service;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testThousandRecordInsertsWithoutBatch() {
		logger.info("Running testThousandRecordInsertsWithoutBatch.");
		ElapsedTimeCalculator.start();
		service.savePostWithOutBatchUsingJPA();
		ElapsedTimeCalculator.stop();
	}

	@Test
	public void testThousandRecordInsertsWithBatch() {
		logger.info("Running testThousandRecordInsertsWithBatch.");
		ElapsedTimeCalculator.start();
		service.savePostWithBatchUsingJPA();
		ElapsedTimeCalculator.stop();
	}

	@Test
	public void test100UpdateWithOutBatchUsingNativeSql() {
		logger.info("Running test100UpdateWithOutBatchUsingNativeSql.");
		ElapsedTimeCalculator.start();
		service.updatePostWithNativeSqlWithOutBatch();
		ElapsedTimeCalculator.stop();
	}

	@Test
	public void test100UpdateWithBatchSize01UsingNativeSql() {
		logger.info("Running test100UpdateWithBatchSize01UsingNativeSql batch size of : 1");
		ElapsedTimeCalculator.start();
		service.updatePostWithNativeSqlWithBatch(1);
		ElapsedTimeCalculator.stop();
	}

	@Test
	public void test100UpdateWithBatchSize02UsingNativeSql() {
		logger.info("Running test100UpdateWithBatchSize02UsingNativeSql batch size of : 2");
		ElapsedTimeCalculator.start();
		service.updatePostWithNativeSqlWithBatch(2);
		ElapsedTimeCalculator.stop();
	}

	@Test
	public void test100UpdateWithBatchSize05UsingNativeSql() {
		logger.info("Running test100UpdateWithBatchSize05UsingNativeSql batch size of : 5");
		ElapsedTimeCalculator.start();
		service.updatePostWithNativeSqlWithBatch(5);
		ElapsedTimeCalculator.stop();
	}

	@Test
	public void test100UpdateWithBatchSize10UsingNativeSql() {
		logger.info("Running test100UpdateWithBatchSize10UsingNativeSql batch size of : 10");
		ElapsedTimeCalculator.start();
		service.updatePostWithNativeSqlWithBatch(10);
		ElapsedTimeCalculator.stop();
	}

	@Test
	public void test100UpdateWithBatchSize20UsingNativeSql() {
		logger.info("Running test100UpdateWithBatchSize20UsingNativeSql batch size of : 20");
		ElapsedTimeCalculator.start();
		service.updatePostWithNativeSqlWithBatch(20);
		ElapsedTimeCalculator.stop();
	}

	@Test
	public void test100UpdateWithBatchSize50UsingNativeSql() {
		logger.info("Running test100UpdateWithBatchSize50UsingNativeSql batch size of : 50");
		ElapsedTimeCalculator.start();
		service.updatePostWithNativeSqlWithBatch(50);
		ElapsedTimeCalculator.stop();
	}

	@Test
	public void test100UpdateWithBatchSize100UsingNativeSql() {
		logger.info("Running test100UpdateWithBatchSize100UsingNativeSql batch size of : 100");
		ElapsedTimeCalculator.start();
		service.updatePostWithNativeSqlWithBatch(100);
		ElapsedTimeCalculator.stop();
	}

	@Test
	public void test100UpdateWithBatchSize05UsingJdbcPreparedStatement() {
		logger.info("Running test100UpdateWithBatchSize05UsingJdbcPreparedStatement batch size of : 05");
		ElapsedTimeCalculator.start();
		service.updatePostWithUsingJdbcPreparedStatementWithBatch(05);
		ElapsedTimeCalculator.stop();
	}

	@Test
	public void test100UpdateWithBatchSize10UsingJdbcPreparedStatement() {
		logger.info("Running test100UpdateWithBatchSize10UsingJdbcPreparedStatement batch size of : 10");
		ElapsedTimeCalculator.start();
		service.updatePostWithUsingJdbcPreparedStatementWithBatch(10);
		ElapsedTimeCalculator.stop();
	}

	@Test
	public void test100UpdateWithBatchSize20UsingJdbcPreparedStatement() {
		logger.info("Running test100UpdateWithBatchSize20UsingJdbcPreparedStatement batch size of : 20");
		ElapsedTimeCalculator.start();
		service.updatePostWithUsingJdbcPreparedStatementWithBatch(20);
		ElapsedTimeCalculator.stop();
	}

	@Test
	public void test100UpdateWithBatchSize50UsingJdbcPreparedStatement() {
		logger.info("Running test100UpdateWithBatchSize50UsingJdbcPreparedStatement batch size of : 50");
		ElapsedTimeCalculator.start();
		service.updatePostWithUsingJdbcPreparedStatementWithBatch(50);
		ElapsedTimeCalculator.stop();
	}

	@Test
	public void testMaxNumberOfConnectionsPossibleAtAnyTime() {
		service.createConnections();
	}
	
	@Test
	public void testMaxNumberOfConnectionsPossibleJdbcAtAnyTime() {
		service.createConnectionsJdbc();
	}

}
