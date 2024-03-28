package com.diginamic.apijava.helper;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diginamic.apijava.dto.AbsenceOrganizationScoreDto;
import com.diginamic.apijava.entity.AbsenceOrganization;
import com.diginamic.apijava.entity.Organization;
import com.diginamic.apijava.enums.AbsenceOrganizationStatus;
import com.diginamic.apijava.enums.AbsenceOrganizationType;
import com.diginamic.apijava.repository.AbsenceOrganizationRepository;

@Service
public class AbsenceOrganizationScoreHelper {

	@Autowired
	private AbsenceOrganizationRepository absenceOrganizationRepository;
	
	public AbsenceOrganizationScoreDto calculateAbsenceOrganizationScore(Organization organization) {
		
		Integer publicHolidayAcquired = organization.getPublicHoliday();
		Integer publicHolidayTaken = 0;
		Integer publicHolidaySolde = 0;
		Integer employerRttAcquired = organization.getEmployerRtt();
		Integer employerRttTaken = 0;
		Integer employerRttSolde =0;
		
		List<AbsenceOrganization> absencesOrganizationValidated = absenceOrganizationRepository.findByOrganizationAndAbsenceOrganizationStatus(organization, AbsenceOrganizationStatus.VALIDEE);
		List<AbsenceOrganization> absencesOrganizationThisYearValidated = absencesOrganizationValidated.stream().filter(a -> a.getDate().getYear() == LocalDate.now().getYear()).toList();
		
		for(AbsenceOrganization absenceOrganization : absencesOrganizationThisYearValidated) {
			if(absenceOrganization.getAbsenceOrganizationType().equals(AbsenceOrganizationType.JOUR_FERIE)) {
				publicHolidayTaken++;
			}
			else if(absenceOrganization.getAbsenceOrganizationType().equals(AbsenceOrganizationType.RTT_EMPLOYEUR)) {
				employerRttTaken++;
			}
		}
		
		publicHolidaySolde = publicHolidayAcquired - publicHolidayTaken;
		employerRttSolde = employerRttAcquired - employerRttTaken;
		
		AbsenceOrganizationScoreDto absenceOrganizationScoreDto = new AbsenceOrganizationScoreDto(publicHolidayAcquired, publicHolidayTaken, publicHolidaySolde, employerRttAcquired, employerRttTaken, employerRttSolde);
		
		return absenceOrganizationScoreDto;
	}
}
