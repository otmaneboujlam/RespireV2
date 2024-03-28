package com.diginamic.apijava.helper;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diginamic.apijava.dto.AbsenceScoreDto;
import com.diginamic.apijava.entity.Absence;
import com.diginamic.apijava.entity.AbsenceOrganization;
import com.diginamic.apijava.entity.Account;
import com.diginamic.apijava.enums.AbsenceOrganizationStatus;
import com.diginamic.apijava.enums.AbsenceStatus;
import com.diginamic.apijava.enums.AbsenceType;
import com.diginamic.apijava.repository.AbsenceOrganizationRepository;
import com.diginamic.apijava.repository.AbsenceRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AbsenceScoreHelper {
	
	@Autowired
	private AbsenceRepository absenceRepository;
	
	@Autowired
	private AbsenceOrganizationRepository absenceOrganizationRepository;

	public AbsenceScoreDto calculateAbsenceScore(Account account) {
		
		Float paidHolidayLastYearAcquired = account.getPaidHolidayLastYear();
		Float paidHolidayThisYearAcquired = account.getPaidHolidayThisYear();
		Float paidHolidayLastYearTaken = 0F;
		Float paidHolidayThisYearTaken = 0F;
		Float paidHolidayLastYearSolde = 0F;
		Float paidHolidayThisYearSolde = 0F;
		Integer employeeRttAcquired = account.getEmployeeRtt();
		Integer employeeRttTaken = 0;
		Integer employeeRttSolde = 0;
		
		List<Absence> absencesValidated = absenceRepository.findByAccountAndAbsenceStatus(account, AbsenceStatus.VALIDEE);
		if(account.getGroupe() == null) {
			throw new EntityNotFoundException("Current user has no group");
		}
		if(account.getGroupe().getDepartment() == null) {
			throw new EntityNotFoundException("Current user has no department");
		}
		if(account.getGroupe().getDepartment().getOrganization() == null) {
			throw new EntityNotFoundException("Current user has no organization");
		}
		List<AbsenceOrganization> absencesOrganizationValidated = absenceOrganizationRepository.findByOrganizationAndAbsenceOrganizationStatus(account.getGroupe().getDepartment().getOrganization(), AbsenceOrganizationStatus.VALIDEE);
		List<LocalDate> absencesOrganizationValidatedDate = absencesOrganizationValidated.stream().filter(a -> (a.getDate().getYear() == LocalDate.now().getYear() || a.getDate().getYear() == LocalDate.now().getYear() - 1)).map(a -> a.getDate()).toList();
		for(Absence absence : absencesValidated) {
			if(absence.getAbsenceType().equals(AbsenceType.CONGE_PAYE)) {
				if(absence.getStartDate().getYear() == LocalDate.now().getYear() - 1 && absence.getEndDate().getYear() == LocalDate.now().getYear() - 1) {
					Long durationNumberLong =  ChronoUnit.DAYS.between(absence.getStartDate(), absence.getEndDate());
					Integer durationNumber = Long.valueOf(durationNumberLong).intValue();
					for(int i =0; i<= durationNumber; i++) {
						if(!absence.getStartDate().plusDays(i).getDayOfWeek().equals(java.time.DayOfWeek.SATURDAY) && !absence.getStartDate().plusDays(i).getDayOfWeek().equals(java.time.DayOfWeek.SUNDAY)) {
							if(!absencesOrganizationValidatedDate.contains(absence.getStartDate().plusDays(i))) {
								paidHolidayLastYearTaken ++;
							}
						}
					}
				}
				else if(absence.getStartDate().getYear() == LocalDate.now().getYear() && absence.getEndDate().getYear() == LocalDate.now().getYear()) {
					Long durationNumberLong =  ChronoUnit.DAYS.between(absence.getStartDate(), absence.getEndDate());
					Integer durationNumber = Long.valueOf(durationNumberLong).intValue();
					for(int i =0; i<= durationNumber; i++) {
						if(!absence.getStartDate().plusDays(i).getDayOfWeek().equals(java.time.DayOfWeek.SATURDAY) && !absence.getStartDate().plusDays(i).getDayOfWeek().equals(java.time.DayOfWeek.SUNDAY)) {
							if(!absencesOrganizationValidatedDate.contains(absence.getStartDate().plusDays(i))) {
								if(paidHolidayLastYearAcquired - paidHolidayLastYearTaken >= 1) {
									paidHolidayLastYearTaken ++;
								}
								else {
									paidHolidayThisYearTaken ++;
								}
							}
						}
					}
				}
				else if(absence.getStartDate().getYear() != absence.getEndDate().getYear()) {
					System.out.println("The absence request Id = "+absence.getId()+" has an error : Start date and end date must be in the same year !");
				}
			}
			else if(absence.getAbsenceType().equals(AbsenceType.RTT_EMPLOYEE)) {
				if(absence.getStartDate().getYear() == LocalDate.now().getYear()) {
					employeeRttTaken ++;
				}
			}	
		}
		
		paidHolidayLastYearSolde = (float) Math.round((paidHolidayLastYearAcquired - paidHolidayLastYearTaken)*100)/100;
		paidHolidayThisYearSolde = (float) Math.round ((paidHolidayThisYearAcquired - paidHolidayThisYearTaken)*100)/100;
		employeeRttSolde = employeeRttAcquired - employeeRttTaken;
		
		AbsenceScoreDto absenceScoreDto = new AbsenceScoreDto(paidHolidayLastYearAcquired, paidHolidayThisYearAcquired, paidHolidayLastYearTaken, paidHolidayThisYearTaken, paidHolidayLastYearSolde, paidHolidayThisYearSolde, employeeRttAcquired, employeeRttTaken, employeeRttSolde);
		
		return absenceScoreDto;
	}
}
