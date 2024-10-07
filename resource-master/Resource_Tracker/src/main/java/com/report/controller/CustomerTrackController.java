package com.report.controller;

import com.report.config.CustomerTrackId;
import com.report.dto.CustomerTrackDto;
import com.report.entity.CustomerTrack;
import com.report.service.CustomerTrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/customerTrack")
public class CustomerTrackController {

    @Autowired
    private CustomerTrackService customerTrackService;

    @GetMapping("/all")
    public List<CustomerTrack> getAllCustomerTrack() {
        return customerTrackService.findAll();
    }

    @PostMapping("/save")
    public CustomerTrack createCustomerTrack(@RequestBody CustomerTrackDto customerTrackDto) {
        return customerTrackService.save(customerTrackDto);
    }

//    @PutMapping("/update")
//    public CustomerTrack updateCustomerTrack(@RequestBody CustomerTrackDto customerTrackDto) {
//        return customerTrackService.save(customerTrackDto);
//    }

    @DeleteMapping("/delete/{customerName}/{track}")
    public String deleteCustomerTrack(@PathVariable String customerName, @PathVariable String track) {
        CustomerTrackId id = new CustomerTrackId(customerName, track);
        customerTrackService.deleteById(id);
        return "deleted successfully";
    }
}


