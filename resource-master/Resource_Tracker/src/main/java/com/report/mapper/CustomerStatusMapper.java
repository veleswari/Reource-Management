package com.report.mapper;

import com.report.dto.CustomerStatusDto;
import com.report.entity.CustomerStatus;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerStatusMapper {

    com.report.mapper.CustomerStatusMapper INSTANCE = Mappers.getMapper(com.report.mapper.CustomerStatusMapper.class);

    CustomerStatusDto toDTO(CustomerStatus customerStatus);

    CustomerStatus toEntity(CustomerStatusDto customerStatusDTO);
}
