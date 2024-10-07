package com.report.service;


import com.report.config.CustomerTrackId;
import com.report.dto.CustomerTrackDto;
import com.report.entity.CustomerTrack;
import com.report.mapper.CustomerTrackMapper;
import com.report.repository.CustomerTrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerTrackService {

    @Autowired
    private CustomerTrackRepository repository;


    public List<CustomerTrack> findAll() {
        return repository.findAll();
    }


    public CustomerTrack save(CustomerTrackDto customerTrackDto) {
        return repository.save(CustomerTrackMapper.INSTANCE.toEntity(customerTrackDto));
    }


//    public CustomerTrack update(CustomerTrackDto customerTrackDto) {
//        return repository.save(CustomerTrackMapper.INSTANCE.toEntity(customerTrackDto));
//    }


    public String deleteById(CustomerTrackId id) {
        repository.deleteById(id);
        return "deleted succefully";

    }
}

