package com.report.controller;

import com.report.config.CustomerStatusId;
import com.report.dto.CustomerStatusDto;
import com.report.entity.CustomerStatus;
import com.report.service.CustomerStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customerStatus")
public class CustomerStatusController {

    @Autowired
    private CustomerStatusService service;

    @GetMapping("/all")
    public List<CustomerStatus> getAllCustomerStatuses() {
        return service.findAll();
    }

    @PostMapping("/save")
    public CustomerStatus createCustomerStatus(@RequestBody CustomerStatusDto customerStatusDTO) {
        return service.save(customerStatusDTO);
    }

//    @PutMapping("/update")
//    public CustomerStatus updateCustomerStatus(@RequestBody CustomerStatusDto customerStatusDTO) {
//        return service.update(customerStatusDTO);
//    }

    @DeleteMapping("/delete/{customerName}/{status}")
    public String deleteCustomerStatus(@PathVariable String customerName, @PathVariable String status) {
        CustomerStatusId id = new CustomerStatusId(customerName, status);
        service.deleteById(id);
        return "deleted successfully";
    }
}
