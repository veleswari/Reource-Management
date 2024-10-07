package com.report.mapper;

import com.report.dto.OpenNeedsRequestdto;
import com.report.entity.OpenNeeds;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-17T13:44:51+0530",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 20.0.1 (Oracle Corporation)"
)
@Component
public class OpenNeedsMapperImpl implements OpenNeedsMapper {

    @Override
    public OpenNeeds mapToEntity(OpenNeedsRequestdto requestDTO) {
        if ( requestDTO == null ) {
            return null;
        }

        OpenNeeds openNeeds = new OpenNeeds();

        openNeeds.setSPID( requestDTO.getSPID() );
        openNeeds.setProject( requestDTO.getProject() );
        openNeeds.setRole( requestDTO.getRole() );
        openNeeds.setResourceName( requestDTO.getResourceName() );
        openNeeds.setStatus( requestDTO.getStatus() );
        openNeeds.setExperience( requestDTO.getExperience() );
        openNeeds.setInitialStatus( requestDTO.getInitialStatus() );
        openNeeds.setProfileSharedToDHDate( requestDTO.getProfileSharedToDHDate() );
        openNeeds.setDHInterviewDate( requestDTO.getDHInterviewDate() );
        openNeeds.setNoOfDaysAgeingForDHInterview( requestDTO.getNoOfDaysAgeingForDHInterview() );
        openNeeds.setDHFeedbackDate( requestDTO.getDHFeedbackDate() );
        openNeeds.setDHLeadNameForInterview( requestDTO.getDHLeadNameForInterview() );
        openNeeds.setAgeingForInterviewFeedback( requestDTO.getAgeingForInterviewFeedback() );
        openNeeds.setLatestStatus( requestDTO.getLatestStatus() );
        openNeeds.setRoleClosedDate( requestDTO.getRoleClosedDate() );
        openNeeds.setRemarks( requestDTO.getRemarks() );
        openNeeds.setHVInterviewRating( requestDTO.getHVInterviewRating() );
        openNeeds.setInterviewSlots( requestDTO.getInterviewSlots() );

        return openNeeds;
    }
}
