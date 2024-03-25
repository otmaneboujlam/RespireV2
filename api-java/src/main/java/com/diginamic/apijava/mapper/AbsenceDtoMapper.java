package com.diginamic.apijava.mapper;

import org.springframework.stereotype.Service;

import com.diginamic.apijava.dto.AbsenceDto;
import com.diginamic.apijava.entity.Absence;

@Service
public class AbsenceDtoMapper {
	
	public AbsenceDto toDto(Absence absence) {
		
		AbsenceDto dto = new AbsenceDto(
				absence.getId(),
				absence.getStartDate().toString(),
				absence.getEndDate().toString(),
				absence.getReason(),
				absence.getAbsenceStatus().toString(),
				absence.getAbsenceType().toString(),
				absence.getAccount().getFirstname()+" "+absence.getAccount().getLastname()
				);
		
		return dto;
	}

}
