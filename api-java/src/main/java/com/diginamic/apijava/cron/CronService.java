package com.diginamic.apijava.cron;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.diginamic.apijava.entity.Absence;
import com.diginamic.apijava.entity.AbsenceOrganization;
import com.diginamic.apijava.entity.Account;
import com.diginamic.apijava.entity.Organization;
import com.diginamic.apijava.enums.AbsenceOrganizationStatus;
import com.diginamic.apijava.enums.AbsenceOrganizationType;
import com.diginamic.apijava.enums.AbsenceStatus;
import com.diginamic.apijava.enums.AbsenceType;
import com.diginamic.apijava.helper.AbsenceOrganizationScoreHelper;
import com.diginamic.apijava.helper.AbsenceScoreHelper;
import com.diginamic.apijava.repository.AbsenceOrganizationRepository;
import com.diginamic.apijava.repository.AbsenceRepository;
import com.diginamic.apijava.repository.AccountRepository;
import com.diginamic.apijava.repository.OrganizationRepository;

@Service
public class CronService {
	
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private AbsenceScoreHelper absenceScoreHelper;
	
	@Autowired
	private AbsenceOrganizationScoreHelper absenceOrganizationScoreHelper;
	
	@Autowired
	private AbsenceRepository absenceRepository;
	
	@Autowired
	private AbsenceOrganizationRepository absenceOrganizationRepository;
	
	@Autowired
	private OrganizationRepository organizationRepository;

	//last day of the month at 20h
	@Scheduled(cron = "0 0 21 L * *")
	public void cronHandlePaidHolidayThisYear() {
		LOG.info("CRON : Handle Paid Holiday");
		List<Account> accounts = accountRepository.findAll();
		for(Account account :accounts) {
			account.setPaidHolidayThisYear(account.getPaidHolidayThisYear()+2.08F);
			accountRepository.save(account);
		}
	}
	
	//Last day of the year at 21h
	@Scheduled(cron = "0 0 22 31 12 *")
	public void cronHandleIncreasePaidHolidayThisYear() {
		LOG.info("CRON : Handle Increase Paid Holiday");
		List<Account> accounts = accountRepository.findAll();
		for(Account account :accounts) {
			account.setPaidHolidayThisYear((float) Math.ceil(account.getPaidHolidayThisYear()));
			accountRepository.save(account);
		}
	}
	
	//First day of the year at 22h
	@Scheduled(cron = "0 0 23 1 1 *")
	public void cronHandleUnusedPaidHoliday() {
		LOG.info("CRON : Handle Unused Paid Holiday");
		List<Account> accounts = accountRepository.findAll();
		for(Account account :accounts) {
			account.setPaidHolidayLastYear(absenceScoreHelper.calculateAbsenceScore(account).getPaidHolidayLastYearSolde() + account.getPaidHolidayThisYear());
			account.setPaidHolidayThisYear(0F);
			accountRepository.save(account);
		}
	}
	
	//Once a day at 23h
	@Scheduled(cron = "0 0 23 * * *")
	public void cronHandleNightTreatment() {
		LOG.info("CRON : Handle Night Treatment");
		
		//Absence Organization
		List<Organization> organizations = organizationRepository.findAll();
		LOG.info("CRON : Handle Absence Organization");
		LOG.info("Total number of organizations to be processed : "+organizations.size());
		for(Organization organization : organizations) {
			HandleNightTreatmentAbsenceOrganization(organization);
		}
		
		//Absence Account
		List<Account> accounts = accountRepository.findAll();
		LOG.info("CRON : Handle Absence Account");
		LOG.info("Total number of accounts to be processed : "+accounts.size());
		for(Account account : accounts) {
			HandleNightTreatmentAbsence(account);
		}
		
	}
	
	public void HandleNightTreatmentAbsence(Account account) {
		LOG.info("Account to treat  : Account_Id = "+account.getId());
		List<Absence> absencesInitial = absenceRepository.findByAccountAndAbsenceStatus(account, AbsenceStatus.INITIALE);
		Collections.sort(absencesInitial);
		List<Absence> absencesThisYearInitial = absencesInitial.stream().filter(a -> a.getStartDate().getYear() == LocalDate.now().getYear()).toList();
		LOG.info("Total number of absences to be processed : "+absencesThisYearInitial.size());
		if(absencesThisYearInitial.size() > 0) {
			List<AbsenceOrganization> absencesOrganizationValidated = absenceOrganizationRepository.findByOrganizationAndAbsenceOrganizationStatus(account.getGroupe().getDepartment().getOrganization(), AbsenceOrganizationStatus.VALIDEE);
			List<LocalDate> absencesOrganizationThisYearValidatedDate = absencesOrganizationValidated.stream().filter(a -> a.getDate().getYear() == LocalDate.now().getYear()).map(a -> a.getDate()).toList();
			List<Absence> absencesValidated = absenceRepository.findByAccountAndAbsenceStatus(account, AbsenceStatus.VALIDEE);
			List<Absence> absencesThisYearValidated = absencesValidated.stream().filter(a -> a.getStartDate().getYear() == LocalDate.now().getYear()).toList();
			List<Absence> absencesWaitingValidation = absenceRepository.findByAccountAndAbsenceStatus(account, AbsenceStatus.EN_ATTENTE_VALIDATION);
			List<Absence> absencesThisYearWaitingValidation = absencesWaitingValidation.stream().filter(a -> a.getStartDate().getYear() == LocalDate.now().getYear()).toList();
			List<Absence> absencesThisYearWaitingValidationNew = new ArrayList<Absence>();
			for(Absence absence : absencesThisYearInitial) {
				Long durationNumberLong =  ChronoUnit.DAYS.between(absence.getStartDate(), absence.getEndDate());
				Integer durationNumber = Long.valueOf(durationNumberLong).intValue() + 1;
				Integer realDurationNumber = 0;
				for(int i=0; i<durationNumber; i++) {
					if(!absence.getStartDate().plusDays(i).getDayOfWeek().equals(java.time.DayOfWeek.SATURDAY) && !absence.getStartDate().plusDays(i).getDayOfWeek().equals(java.time.DayOfWeek.SUNDAY) && !absencesOrganizationThisYearValidatedDate.contains(absence.getStartDate().plusDays(i))) {
						realDurationNumber++;
					}
				}
				if(absence.getStartDate().getDayOfWeek().equals(java.time.DayOfWeek.SATURDAY) || absence.getStartDate().getDayOfWeek().equals(java.time.DayOfWeek.SUNDAY)) {
					absence.setAbsenceStatus(AbsenceStatus.REJETEE);
					absenceRepository.save(absence);
					LOG.info("Absence rejected because the start date or end date is a weekend : Absence_Id = "+absence.getId());
				}
				else if(((absence.getAbsenceType().equals(AbsenceType.RTT_EMPLOYEE)) && (absenceScoreHelper.calculateAbsenceScore(account).getEmployeeRttSolde() < realDurationNumber)) || ((absence.getAbsenceType().equals(AbsenceType.CONGE_PAYE)) && ((absenceScoreHelper.calculateAbsenceScore(account).getPaidHolidayLastYearSolde() + absenceScoreHelper.calculateAbsenceScore(account).getPaidHolidayThisYearSolde()) < realDurationNumber))) {
					absence.setAbsenceStatus(AbsenceStatus.REJETEE);
					absenceRepository.save(absence);
					LOG.info("Absence rejected because the number of RTT_EMPLOYEE or PAID_HOLIDAY remaining is insufficient : Absence_Id = "+absence.getId());
				}
				else if(absencesOrganizationThisYearValidatedDate.contains(absence.getStartDate()) || absencesOrganizationThisYearValidatedDate.contains(absence.getEndDate())) {
					absence.setAbsenceStatus(AbsenceStatus.REJETEE);
					absenceRepository.save(absence);
					LOG.info("Absence rejected because the start date or end date is a RTT_EMPLOYER or PUBLIC_HOLIDAY : Absence_Id = "+absence.getId());
				}
				else if(isOverLap(absence,absencesThisYearValidated,absencesThisYearWaitingValidation,absencesThisYearWaitingValidationNew)) {
					absence.setAbsenceStatus(AbsenceStatus.REJETEE);
					absenceRepository.save(absence);
					LOG.info("Absence rejected because overlaps with another absence : Absence_Id = "+absence.getId());
				}
				else {
					absence.setAbsenceStatus(AbsenceStatus.EN_ATTENTE_VALIDATION);
					absenceRepository.save(absence);
					absencesThisYearWaitingValidationNew.add(absence);
					LOG.info("Absence waiting validation : Absence_Id = "+absence.getId());
				}	
			}
		}
	}
	
	public boolean isOverLap(Absence absence, List<Absence> absencesValidated, List<Absence> absencesWaitingValidation, List<Absence> absencesThisYearWaitingValidationNew) {
		for(Absence absenceValidated : absencesValidated) {
			if(absence.getStartDate().isEqual(absenceValidated.getStartDate())) {
				return true;
			}
			else if (absence.getStartDate().isEqual(absenceValidated.getEndDate())) {
				return true;
			}
			else if(absence.getEndDate().isEqual(absenceValidated.getStartDate())) {
				return true;
			}
			else if (absence.getEndDate().isEqual(absenceValidated.getEndDate())) {
				return true;
			}
			else if (absence.getStartDate().isAfter(absenceValidated.getStartDate()) && absence.getStartDate().isBefore(absenceValidated.getEndDate())) {
				return true;
			}
			else if (absence.getEndDate().isAfter(absenceValidated.getStartDate()) && absence.getEndDate().isBefore(absenceValidated.getEndDate())) {
				return true;
			}
		}
		for(Absence absenceWaitingValidation : absencesWaitingValidation) {
			if(absence.getStartDate().isEqual(absenceWaitingValidation.getStartDate())) {
				return true;
			}
			else if (absence.getStartDate().isEqual(absenceWaitingValidation.getEndDate())) {
				return true;
			}
			else if(absence.getEndDate().isEqual(absenceWaitingValidation.getStartDate())) {
				return true;
			}
			else if (absence.getEndDate().isEqual(absenceWaitingValidation.getEndDate())) {
				return true;
			}
			else if (absence.getStartDate().isAfter(absenceWaitingValidation.getStartDate()) && absence.getStartDate().isBefore(absenceWaitingValidation.getEndDate())) {
				return true;
			}
			else if (absence.getEndDate().isAfter(absenceWaitingValidation.getStartDate()) && absence.getEndDate().isBefore(absenceWaitingValidation.getEndDate())) {
				return true;
			}
		}
		for(Absence absenceThisYearWaitingValidationNew : absencesThisYearWaitingValidationNew) {
			if(absence.getStartDate().isEqual(absenceThisYearWaitingValidationNew.getStartDate())) {
				return true;
			}
			else if (absence.getStartDate().isEqual(absenceThisYearWaitingValidationNew.getEndDate())) {
				return true;
			}
			else if(absence.getEndDate().isEqual(absenceThisYearWaitingValidationNew.getStartDate())) {
				return true;
			}
			else if (absence.getEndDate().isEqual(absenceThisYearWaitingValidationNew.getEndDate())) {
				return true;
			}
			else if (absence.getStartDate().isAfter(absenceThisYearWaitingValidationNew.getStartDate()) && absence.getStartDate().isBefore(absenceThisYearWaitingValidationNew.getEndDate())) {
				return true;
			}
			else if (absence.getEndDate().isAfter(absenceThisYearWaitingValidationNew.getStartDate()) && absence.getEndDate().isBefore(absenceThisYearWaitingValidationNew.getEndDate())) {
				return true;
			}
		}
		return false;
	}

	public void HandleNightTreatmentAbsenceOrganization(Organization organization) {
		LOG.info("Organization to treat : Organization_Id = "+organization.getId());
		List<AbsenceOrganization> absencesOrganizationInitial = absenceOrganizationRepository.findByOrganizationAndAbsenceOrganizationStatus(organization, AbsenceOrganizationStatus.INITIALE);
		Collections.sort(absencesOrganizationInitial);
		List<AbsenceOrganization> absencesOrganizationThisYearInitial = absencesOrganizationInitial.stream().filter(a -> a.getDate().getYear() == LocalDate.now().getYear()).toList();
		LOG.info("Total number of absencesOrganization to be processed : "+absencesOrganizationThisYearInitial.size());
		if(absencesOrganizationThisYearInitial.size() > 0) {
			List<AbsenceOrganization> absencesOrganizationValidated = absenceOrganizationRepository.findByOrganizationAndAbsenceOrganizationStatus(organization, AbsenceOrganizationStatus.VALIDEE);
			List<LocalDate> absencesOrganizationThisYearValidatedDate = absencesOrganizationValidated.stream().filter(a -> a.getDate().getYear() == LocalDate.now().getYear()).map(a -> a.getDate()).toList();
			List<LocalDate> absencesOrganizationThisYearValidatedDateNew = new ArrayList<LocalDate>();
			for(AbsenceOrganization absenceOrganization : absencesOrganizationThisYearInitial) {
				if(absenceOrganization.getAbsenceOrganizationType().equals(AbsenceOrganizationType.RTT_EMPLOYEUR)) {
					if(absenceOrganization.getDate().getDayOfWeek().equals(java.time.DayOfWeek.SATURDAY) || absenceOrganization.getDate().getDayOfWeek().equals(java.time.DayOfWeek.SUNDAY)) {
						absenceOrganization.setAbsenceOrganizationStatus(AbsenceOrganizationStatus.REJETEE);
						absenceOrganizationRepository.save(absenceOrganization);
						LOG.info("AbsenceOrganization rejected because the start date or end date is a weekend : AbsenceOrganization_Id = "+absenceOrganization.getId());
					}
					else if(absenceOrganizationScoreHelper.calculateAbsenceOrganizationScore(organization).getEmployerRttSolde() <= 0) {
						absenceOrganization.setAbsenceOrganizationStatus(AbsenceOrganizationStatus.REJETEE);
						absenceOrganizationRepository.save(absenceOrganization);
						LOG.info("AbsenceOrganization rejected because the number of RTT_EMPLOYER remaining is insufficient : AbsenceOrganization_Id = "+absenceOrganization.getId());
					}
					else if (absencesOrganizationThisYearValidatedDate.contains(absenceOrganization.getDate()) || absencesOrganizationThisYearValidatedDateNew.contains(absenceOrganization.getDate())) {
						absenceOrganization.setAbsenceOrganizationStatus(AbsenceOrganizationStatus.REJETEE);
						absenceOrganizationRepository.save(absenceOrganization);
						LOG.info("AbsenceOrganization rejected because overlaps with another absence : AbsenceOrganization_Id = "+absenceOrganization.getId());
					}
					else {
						absenceOrganization.setAbsenceOrganizationStatus(AbsenceOrganizationStatus.VALIDEE);
						absenceOrganizationRepository.save(absenceOrganization);
						absencesOrganizationThisYearValidatedDateNew.add(absenceOrganization.getDate());
						LOG.info("AbsenceOrganization validated : AbsenceOrganization_Id = "+absenceOrganization.getId());
					}
				}
				else if(absenceOrganization.getAbsenceOrganizationType().equals(AbsenceOrganizationType.JOUR_FERIE)) {
					if(absenceOrganizationScoreHelper.calculateAbsenceOrganizationScore(organization).getPublicHolidaySolde() <= 0) {
						absenceOrganization.setAbsenceOrganizationStatus(AbsenceOrganizationStatus.REJETEE);
						absenceOrganizationRepository.save(absenceOrganization);
						LOG.info("AbsenceOrganization rejected because the number of PUBLIC_HOLIDAY remaining is insufficient : AbsenceOrganization_Id = "+absenceOrganization.getId());
					}
					else if (absencesOrganizationThisYearValidatedDate.contains(absenceOrganization.getDate()) || absencesOrganizationThisYearValidatedDateNew.contains(absenceOrganization.getDate())) {
						absenceOrganization.setAbsenceOrganizationStatus(AbsenceOrganizationStatus.REJETEE);
						absenceOrganizationRepository.save(absenceOrganization);
						LOG.info("AbsenceOrganization rejected because overlaps with another absenceOrganization : AbsenceOrganization_Id = "+absenceOrganization.getId());
					}
					else {
						absenceOrganization.setAbsenceOrganizationStatus(AbsenceOrganizationStatus.VALIDEE);
						absenceOrganizationRepository.save(absenceOrganization);
						absencesOrganizationThisYearValidatedDateNew.add(absenceOrganization.getDate());
						LOG.info("AbsenceOrganization validated : AbsenceOrganization_Id = "+absenceOrganization.getId());
					}
				}
			}
		}
	}
	
}
