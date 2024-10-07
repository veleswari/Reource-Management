package com.report.service;

import com.report.config.CustomerStatusId;
import com.report.dto.CustomerStatusDto;
import com.report.entity.CustomerStatus;
import com.report.mapper.CustomerStatusMapper;
import com.report.repository.CustomerStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerStatusService {

    @Autowired
    private CustomerStatusRepository repository;


    public List<CustomerStatus> findAll() {
        return repository.findAll();
    }


    public CustomerStatus save(CustomerStatusDto customerStatusDTO) {
        return repository.save(CustomerStatusMapper.INSTANCE.toEntity(customerStatusDTO));
    }


//    public CustomerStatus update(CustomerStatusDto customerStatusDTO) {
//        CustomerStatus customerStatus=CustomerStatusMapper.INSTANCE.toEntity(customerStatusDTO);
//        return repository.save(customerStatus);
//    }


    public String deleteById(CustomerStatusId id) {
        repository.deleteById(id);
        return "deleted succefully";

    }
}
