package com.diginamic.apijava.mapper;

import org.springframework.stereotype.Service;

import com.diginamic.apijava.dto.AbsenceOrganizationDto;
import com.diginamic.apijava.entity.AbsenceOrganization;

@Service
public class AbsenceOrganizationDtoMapper {
	
	public AbsenceOrganizationDto toDto(AbsenceOrganization absenceOrganization) {
		
		AbsenceOrganizationDto dto = new AbsenceOrganizationDto(
				absenceOrganization.getId(),
				absenceOrganization.getDate().toString(),
				absenceOrganization.getReason(),
				absenceOrganization.getAbsenceOrganizationStatus().toString(),
				absenceOrganization.getAbsenceOrganizationType().toString(),
				absenceOrganization.getOrganization().getName()
				);
		
		return dto;
	}

}
