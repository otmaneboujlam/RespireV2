package com.diginamic.apijava.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diginamic.apijava.dto.OrganizationInfoDto;
import com.diginamic.apijava.mapper.OrganizationInfoDtoMapper;
import com.diginamic.apijava.repository.OrganizationRepository;

@Service
public class OrganizationService {

	@Autowired
	private OrganizationRepository organizationRepository;
	
	@Autowired
	private OrganizationInfoDtoMapper organizationInfoDtoMapper;
	
	public List<OrganizationInfoDto> findAll() {
		return organizationRepository.findAll().stream().map((organization) -> organizationInfoDtoMapper.toDto(organization)).toList();
	}
}
