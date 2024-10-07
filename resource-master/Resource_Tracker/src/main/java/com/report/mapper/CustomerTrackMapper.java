package com.report.mapper;

import com.report.dto.CustomerTrackDto;
import com.report.entity.CustomerTrack;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerTrackMapper {

    com.report.mapper.CustomerTrackMapper INSTANCE = Mappers.getMapper(com.report.mapper.CustomerTrackMapper.class);


    CustomerTrackDto toDTO(CustomerTrack customerTrack);

    CustomerTrack toEntity(CustomerTrackDto customerTrackDto);
}
