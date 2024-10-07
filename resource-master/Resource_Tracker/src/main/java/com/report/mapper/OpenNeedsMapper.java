package com.report.mapper;


import com.report.dto.OpenNeedsMasterdto;
import com.report.dto.OpenNeedsRequestdto;
import com.report.entity.OpenNeeds;
import com.report.entity.OpenNeedsMaster;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface OpenNeedsMapper {
    OpenNeedsMapper INSTANCE = Mappers.getMapper(OpenNeedsMapper.class);

    OpenNeeds mapToEntity(OpenNeedsRequestdto requestDTO);
    OpenNeedsRequestdto maptToDto(OpenNeeds openNeeds);

    OpenNeedsMaster mapToMasterEntity(OpenNeedsMasterdto openNeedsMasterdto);

    OpenNeedsMasterdto mapToMasterDto(OpenNeedsMaster openNeedsMaster);
}
