package com.report.mapper;

import com.report.dto.ManagerDetailsDto;
import com.report.entity.ManagerDetails;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ManagerDetailsMapper {

    com.report.mapper.ManagerDetailsMapper INSTANCE = Mappers.getMapper(com.report.mapper.ManagerDetailsMapper.class);

    ManagerDetailsDto toDTO(ManagerDetails managerDetails);

    ManagerDetails toEntity(ManagerDetailsDto managerDetailsDto);

}
