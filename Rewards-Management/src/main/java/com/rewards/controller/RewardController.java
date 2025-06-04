package com.rewards.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rewards.model.RewardSummary;
import com.rewards.model.Transaction;
import com.rewards.service.RewardService;

@RestController
@RequestMapping("/api/rewards")
public class RewardController {

	@Autowired
	private RewardService rewardService;
	
	@PostMapping
	public List<RewardSummary> getRewards(@RequestBody List<Transaction> transactions){
		return rewardService.calculateRewards(transactions);
	}
}
