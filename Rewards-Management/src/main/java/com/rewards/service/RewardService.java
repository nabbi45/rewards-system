package com.rewards.service;

import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Service;

import com.rewards.exception.InvalidTransactionException;
import com.rewards.model.RewardSummary;
import com.rewards.model.Transaction;

/**
 Service class for calculating the points for customer
 based on the transactions.
 */
@Service
public class RewardService {

	
	/**
	 * calculate reward points for list of transactions
	 * @param transactions
	 * @return list of reward summaries for each valid transaction
	 */
	public List<RewardSummary> calculateRewards(List<Transaction> transactions) {
		List<RewardSummary> rewards = new ArrayList<>();

		for (Transaction t : transactions) {
			try {

				validateTransaction(t);
				int points = calculatePoints(t.getAmount());
				String customerId = t.getCustomerId();
				String month = t.getDate().getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH) + "-"
						+ t.getDate().getYear();
				rewards.add(new RewardSummary(customerId, month, points));
			} catch (InvalidTransactionException e) {
				System.err.println("Skipping invalid transactions: " + e.getMessage());
			}
		}

		return rewards;
	}

	/**
	 * calculates reward points based on purchase amount
	 * @param amount
	 * @return reward points
	 */
	private int calculatePoints(double amount) {
		int points = 0;

		if (amount > 100) {
			points += (int) (2 * (amount - 100));
			amount = 100;
		}

		if (amount > 50) {
			points += (int) (amount - 50);
		}

		return points;
	}

	/**
	 * validates a transaction for null or invalid values
	 * @param t transaction
	 */
	private void validateTransaction(Transaction t) {
		if (t.getCustomerId()==null || t.getCustomerId().isEmpty()) {
			throw new InvalidTransactionException("Customer id is missing");
		}

		if (t.getAmount() < 0) {
			throw new InvalidTransactionException(
					"Transaction aomunt cannot be negative for customer: " + t.getCustomerId());
		}

		if (t.getDate() == null) {
			throw new InvalidTransactionException("Date is missing for customer: " + t.getCustomerId());
		}
	}

}
