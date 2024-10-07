package com.report.controller;

import com.report.service.FilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth/filter")
public class FilterController {
    @Autowired
    FilterService filterService;

    @GetMapping("/manager")
    public List<String> getAllCustomers(){
        return filterService.getAllCustomers();
    }

    @GetMapping("/stream")
    public List<String> getStreamForCustomer(@RequestParam(name = "customerName")String customerName){
        return filterService.getStreamForCustomer(customerName);
    }

}