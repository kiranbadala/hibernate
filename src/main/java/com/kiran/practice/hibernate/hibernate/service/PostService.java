package com.kiran.practice.hibernate.hibernate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kiran.practice.hibernate.hibernate.PostDao;

@Component
public class PostService {

	@Autowired
	private PostDao dao;

	@Transactional(propagation = Propagation.REQUIRED)
	public void savePostWithOutBatchUsingJPA() {
		dao.savePostWithOutBatchUsingJPA();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void savePostWithBatchUsingJPA() {
		dao.savePostWithBatchUsingJPA();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updatePostWithNativeSqlWithOutBatch() {
		dao.updatePostWithNativeSqlWithOutBatch();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updatePostWithNativeSqlWithBatch(int batchSize) {
		dao.updatePostWithNativeSqlWithBatch(batchSize);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updatePostWithUsingJdbcPreparedStatementWithBatch(int batchSize) {
		dao.updatePostUsingJdbcPreparedStatementWithWithBatch(batchSize);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void fetchRecordsWithoutStatementCache() {
		dao.fetchRecordsWithoutStatementCache();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void fetchRecordsWithStatementCache() {
		dao.fetchRecordsWithStatementCache();
	}

	public void createConnections() {
		dao.createConnections();
	}
	
	public void createConnectionsJdbc() {
		dao.createConnectionsJdbc();
	}

	public void fetchRecordsWithFetchSize(int size) {
		dao.fetchRecordsWithFetchSize(size);
	}
}
