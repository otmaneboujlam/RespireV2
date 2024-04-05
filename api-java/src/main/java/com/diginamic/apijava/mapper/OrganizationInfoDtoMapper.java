package com.diginamic.apijava.mapper;

import org.springframework.stereotype.Service;

import com.diginamic.apijava.dto.OrganizationInfoDto;
import com.diginamic.apijava.entity.Organization;

@Service
public class OrganizationInfoDtoMapper {

	public OrganizationInfoDto toDto(Organization organization) {
		
		OrganizationInfoDto dto = new OrganizationInfoDto(
				organization.getId(),
				organization.getName(),
				organization.getPublicHoliday(),
				organization.getEmployerRtt()
				);
		
		return dto;
	}
}
