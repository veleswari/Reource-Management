package com.report.mapper;

import com.report.dto.FlexFieldsDto;
import com.report.entity.FlexFields;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FlexFieldsMapper {
    com.report.mapper.FlexFieldsMapper INSTANCE = Mappers.getMapper(com.report.mapper.FlexFieldsMapper.class);
    FlexFields mapToFlexFieldsEntity(FlexFieldsDto flexFieldsDto);
}
