package com.diginamic.apijava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diginamic.apijava.cron.CronService;

@Service
public class AdminCronService {
	
	@Autowired
	private CronService cronService;
	
	public void startCron() {
		cronService.cronHandleNightTreatment();
	}
	
}
