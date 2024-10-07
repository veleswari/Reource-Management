package com.report.mapper;

import com.report.dto.ResourceDetailsDto;
import com.report.entity.ResourceDetails;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ResourceDetailsMapper {
    ResourceDetailsMapper INSTANCE = Mappers.getMapper(ResourceDetailsMapper.class);
    ResourceDetails mapToResourceDetailsDto(ResourceDetailsDto resourceDetailsDto);

    ResourceDetailsDto mapToResourceDetails(ResourceDetails resourceDetails);
}
