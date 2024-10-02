package com.hms.api.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.hms.api.entity.TransactionDetails;

@Transactional
public interface TransactionService {

	public int generateSalaryForUser(TransactionDetails transactionDetails);

	public List<String> generateSalaryreportForUser(String username, int from, int to);

}
