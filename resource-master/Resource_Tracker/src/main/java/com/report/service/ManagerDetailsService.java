package com.report.service;

import com.report.config.ManagerDetailsId;
import com.report.dto.ManagerDetailsDto;
import com.report.entity.ManagerDetails;
import com.report.mapper.ManagerDetailsMapper;
import com.report.repository.ManagerDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerDetailsService {

    @Autowired
    private ManagerDetailsRepository managerDetailsRepository;


    public List<ManagerDetails> findAll() {
        return managerDetailsRepository.findAll();
    }


    public ManagerDetails save(ManagerDetailsDto managerDetailsDto) {
        return managerDetailsRepository.save(ManagerDetailsMapper.INSTANCE.toEntity(managerDetailsDto));
    }


//    public ManagerDetails update(ManagerDetailsDto managerDetailsDto) {
//        return managerDetailsRepository.save(ManagerDetailsMapper.INSTANCE.toEntity(managerDetailsDto));
//    }


    public String deleteById(ManagerDetailsId id) {
        managerDetailsRepository.deleteById(id);
        return "deleted succefully";

    }


}
