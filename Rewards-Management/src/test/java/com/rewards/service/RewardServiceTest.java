package com.rewards.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.rewards.model.RewardSummary;
import com.rewards.model.Transaction;

public class RewardServiceTest {
	
	private RewardService rewardService;
	
	@BeforeEach
	public void setUp() {
		rewardService = new RewardService();
	}
	
	@Test
	public void testCalculateRewards_WithValidTransactions() {
		//arrange
		Transaction t1 = new Transaction("cust1", 120.0, LocalDate.of(2025, 04, 10));
		Transaction t2 = new Transaction("cust1", 80.0, LocalDate.of(2025, 04, 15));
		Transaction t3 = new Transaction("cust2", 130.0, LocalDate.of(2025, 05, 05));
		
		List<Transaction> transactions = Arrays.asList(t1,t2,t3);
		
		List<RewardSummary> result = rewardService.calculateRewards(transactions);
		
		assertEquals(3, result.size());
		
		RewardSummary r1 = result.get(0);
		
		assertEquals("cust1", r1.getCustomerId());
		
		assertEquals("Apr-2025", r1.getMonth());
		
		assertEquals(90, r1.getPoints());
		
		RewardSummary r2 = result.get(1);
		assertEquals(30, r2.getPoints());
		
		RewardSummary r3 = result.get(2);
		assertEquals("May-2025", r3.getMonth());
		assertEquals(110, r3.getPoints());
	}
	
	@Test
	public void testCalculateRewards_WithNegativeAmount_ShouldSkip() {
		Transaction t1 = new Transaction("cust1", -50.0, LocalDate.of(2025, 04, 10));
		Transaction t2 = new Transaction("cust2", 60.0, LocalDate.of(2025, 04, 11));

		List<Transaction> transactions = Arrays.asList(t1,t2);
		
		List<RewardSummary> result = rewardService.calculateRewards(transactions);
		
		assertEquals(1, result.size());
		assertEquals("cust2", result.get(0).getCustomerId());
		assertEquals(10, result.get(0).getPoints());
	}
	
	@Test
	public void testInvalidTransaction_NullCustomerId_ShouldSkip() {
		Transaction t = new Transaction(null, 100.0, LocalDate.of(2025, 04, 1));
		Transaction t2 = new Transaction("cust2", 110.0, LocalDate.of(2025, 04, 1));
		
		List<RewardSummary> result = rewardService.calculateRewards(List.of(t,t2));
		
		assertEquals(1, result.size());
		assertEquals("cust2", result.get(0).getCustomerId());
		
	}

}
