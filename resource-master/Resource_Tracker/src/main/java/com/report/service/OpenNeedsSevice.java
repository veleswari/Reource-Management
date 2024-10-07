package com.report.service;

import com.report.dto.OpenNeedsRequestdto;
import com.report.entity.OpenNeeds;
import com.report.mapper.OpenNeedsMapper;
import com.report.repository.OpenNeedsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class OpenNeedsSevice {

    @Autowired
    private OpenNeedsRepository openNeedsRepository;

    public Page<OpenNeeds> getAllOpenNeeds(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return openNeedsRepository.findAll(pageable);
    }

    public OpenNeeds saveOpenNeeds(OpenNeedsRequestdto requestDTO) {

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        Date profileSharedDate = requestDTO.getProfileSharedToDHDate();
        Date currentDate = new Date();

        long time_difference = currentDate.getTime() - profileSharedDate.getTime();
        long days_difference = time_difference / (24 * 60 * 60 * 1000);

//        System.out.println("Number of days between " + profileSharedDate + " and " + currentDate + " is: " + days_difference);

        requestDTO.setNoOfDaysAgeingForDHInterview(days_difference);
        OpenNeeds openNeeds = OpenNeedsMapper.INSTANCE.mapToEntity(requestDTO);
        return openNeedsRepository.save(openNeeds);
    }

    public List<OpenNeeds> getRecordsBySpid(Integer spid){
        List<OpenNeeds> openNeeds = openNeedsRepository.findBySPID(spid);
        return openNeeds;
    }


    public String deleteRecordBySpid(String customId) {
        if (openNeedsRepository.existsById(customId)) {
            openNeedsRepository.deleteById(customId);
            return "Record deleted successfully";
        } else {
            return "Record not present";
        }
    }

    public OpenNeeds updateRecord(OpenNeedsRequestdto requestdto){
        OpenNeeds openNeeds = openNeedsRepository.findByCustomId(requestdto.getCustomId());
        openNeeds.setAgeingForInterviewFeedback(requestdto.getAgeingForInterviewFeedback());
        openNeeds.setDHInterviewDate(requestdto.getDHInterviewDate());
        openNeeds.setDHFeedbackDate(requestdto.getDHFeedbackDate());
        openNeeds.setDHLeadNameForInterview(requestdto.getDHLeadNameForInterview());
        openNeeds.setExperience(requestdto.getExperience());
        openNeeds.setHVInterviewRating(requestdto.getHVInterviewRating());
        openNeeds.setInterviewSlots(requestdto.getInterviewSlots());
        openNeeds.setInitialStatus(requestdto.getInitialStatus());
        openNeeds.setLatestStatus(requestdto.getLatestStatus());
        openNeeds.setProject(requestdto.getProject());
        openNeeds.setProfileSharedToDHDate(requestdto.getProfileSharedToDHDate());
        openNeeds.setRole(requestdto.getRole());
        openNeeds.setRemarks(requestdto.getRemarks());
        openNeeds.setRoleClosedDate(requestdto.getRoleClosedDate());
        openNeeds.setResourceName(requestdto.getResourceName());
        openNeeds.setStatus(requestdto.getStatus());
        openNeeds.setSPID(requestdto.getSPID());

        return openNeedsRepository.save(openNeeds);
    }


}
