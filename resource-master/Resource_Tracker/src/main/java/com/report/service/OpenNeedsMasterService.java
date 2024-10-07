package com.report.service;

import com.report.dto.OpenNeedsMasterdto;
import com.report.entity.OpenNeedsMaster;
import com.report.mapper.OpenNeedsMapper;
import com.report.mapper.OpenNeedsMasterMapper;
import com.report.repository.OpenNeedsMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class OpenNeedsMasterService {
    @Autowired
    OpenNeedsMasterRepository openNeedsMasterRepository;
    public OpenNeedsMasterdto updateRecordForMaster(OpenNeedsMasterdto openNeedsMasterDTO) {

        OpenNeedsMaster openNeedsMaster = openNeedsMasterRepository.findBySPID(openNeedsMasterDTO.getSPID());

        openNeedsMaster.setSPID(openNeedsMasterDTO.getSPID());

        openNeedsMaster.setRole(openNeedsMasterDTO.getRole());

        openNeedsMaster.setRemarks(openNeedsMasterDTO.getRemarks());

        openNeedsMaster.setProject(openNeedsMasterDTO.getProject());

        return OpenNeedsMapper.INSTANCE.mapToMasterDto(openNeedsMasterRepository.save(openNeedsMaster));

    }

    public Page<OpenNeedsMaster> getAllRecords(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return openNeedsMasterRepository.findAll(pageable);
    }

    public OpenNeedsMasterdto saveOpenNeeds(OpenNeedsMasterdto openNeedsMasterdto) {

        OpenNeedsMaster openNeedsMaster = OpenNeedsMasterMapper.INSTANCE.toEntity(openNeedsMasterdto);

        return OpenNeedsMasterMapper.INSTANCE.toEntitydto(openNeedsMasterRepository.save(openNeedsMaster));
    }

}
