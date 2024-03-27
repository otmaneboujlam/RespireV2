package com.diginamic.apijava.cron;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.diginamic.apijava.entity.AbsenceOrganization;
import com.diginamic.apijava.entity.Account;
import com.diginamic.apijava.entity.Organization;
import com.diginamic.apijava.enums.AbsenceOrganizationStatus;
import com.diginamic.apijava.enums.AbsenceOrganizationType;
import com.diginamic.apijava.helper.AbsenceScoreHelper;
import com.diginamic.apijava.repository.AbsenceOrganizationRepository;
import com.diginamic.apijava.repository.AbsenceRepository;
import com.diginamic.apijava.repository.AccountRepository;
import com.diginamic.apijava.repository.OrganizationRepository;

@Service
public class CronService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private AbsenceScoreHelper absenceScoreHelper;
	
	@Autowired
	private AbsenceRepository absenceRepository;
	
	@Autowired
	private AbsenceOrganizationRepository absenceOrganizationRepository;
	
	@Autowired
	private OrganizationRepository organizationRepository;

	//last day of the month at 21h
	@Scheduled(cron = "0 0 21 L * *")
	public void cronHandlePaidHolidayThisYear() {
		System.out.println("CRON : Handle Paid Holiday");
		List<Account> accounts = accountRepository.findAll();
		for(Account account :accounts) {
			account.setPaidHolidayThisYear(account.getPaidHolidayThisYear()+2.08F);
			accountRepository.save(account);
		}
	}
	
	//Last day of the year at 22h
	@Scheduled(cron = "0 0 22 31 12 *")
	public void cronHandleIncreasePaidHolidayThisYear() {
		System.out.println("CRON : Handle Increase Paid Holiday");
		List<Account> accounts = accountRepository.findAll();
		for(Account account :accounts) {
			account.setPaidHolidayThisYear((float) Math.ceil(account.getPaidHolidayThisYear()));
			accountRepository.save(account);
		}
	}
	
	//First day of the year at 23h
	@Scheduled(cron = "0 0 23 1 1 *")
	public void cronHandleUnusedPaidHoliday() {
		System.out.println("CRON : Handle Unused Paid Holiday");
		List<Account> accounts = accountRepository.findAll();
		for(Account account :accounts) {
			account.setPaidHolidayLastYear(absenceScoreHelper.calculateAbsenceScore(account).getPaidHolidayLastYearSolde() + account.getPaidHolidayThisYear());
			account.setPaidHolidayThisYear(0F);
			accountRepository.save(account);
		}
	}
	
	//Once a day at midnight
	@Scheduled(cron = "0 0 0 * * *")
	public void cronHandleNightTreatment() {
		System.out.println("CRON : Handle Night Treatment");
		
		//Absence Organization
		List<AbsenceOrganization> absencesOrganizationInitial = absenceOrganizationRepository.findByAbsenceOrganizationStatus(AbsenceOrganizationStatus.INITIALE);
		if(absencesOrganizationInitial.size() > 0) {
			List<AbsenceOrganization> absencesOrganizationValidated = absenceOrganizationRepository.findByAbsenceOrganizationStatus(AbsenceOrganizationStatus.VALIDEE);
			Collections.sort(absencesOrganizationInitial);
			for(AbsenceOrganization absenceOrganization : absencesOrganizationInitial) {
				if(absenceOrganization.getAbsenceOrganizationType().equals(AbsenceOrganizationType.RTT_EMPLOYEUR)) {
					if(absenceOrganization.getDate().getDayOfWeek().equals(java.time.DayOfWeek.SATURDAY) || absenceOrganization.getDate().getDayOfWeek().equals(java.time.DayOfWeek.SUNDAY)) {
						absenceOrganization.setAbsenceOrganizationStatus(AbsenceOrganizationStatus.REJETEE);
						absenceOrganizationRepository.save(absenceOrganization);
					}
					//else if score organization RTT <= 0 then rejetee
					//else if absenceOrganization already exist absencesOrganizationValidated then rejette
					//else Validee and add absenceOrganization in absencesOrganizationValidated
				}
				else if(absenceOrganization.getAbsenceOrganizationType().equals(AbsenceOrganizationType.JOUR_FERIE)) {
					//if score organization Jour Ferie <= 0 then rejetee
					//else if absenceOrganization already exist in absencesOrganizationValidated then rejette
					//else Validee and add absenceOrganization in absencesOrganizationValidated
				}
			}
		}
		
		//Absence Account
	}
	
}
