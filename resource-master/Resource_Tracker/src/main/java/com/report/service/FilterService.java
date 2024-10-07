package com.report.service;


import com.report.entity.User;
import com.report.repository.CustomerTrackRepository;
import com.report.repository.ManagerDetailsRepository;
import com.report.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FilterService {
    @Autowired
    ManagerDetailsRepository managerDetailsRepository;

    @Autowired
    CustomerTrackRepository customerTrackRepository;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    UserRepository userRepository;

    public List<String> getAllCustomers(){
        String email = authenticationService.getCurrentUsername();
        Optional<User> user = userRepository.findByEmail(email);
        return managerDetailsRepository.findCustomerNamesByManagerName(user.get().getFullName());
    }

    public List<String> getStreamForCustomer(String customerName){
        return customerTrackRepository.findStreamByCustomerName(customerName);
    }
}

