package com.report.mapper;

import com.report.dto.OpenNeedsMasterdto;
import com.report.entity.OpenNeedsMaster;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OpenNeedsMasterMapper {

    OpenNeedsMasterMapper INSTANCE = Mappers.getMapper(OpenNeedsMasterMapper.class);
    OpenNeedsMaster toEntity(OpenNeedsMasterdto openNeedsMasterdto);

    OpenNeedsMasterdto toEntitydto(OpenNeedsMaster openNeedsMasterdto);
}
