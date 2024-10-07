package com.report.controller;


import com.report.config.ManagerDetailsId;
import com.report.dto.ManagerDetailsDto;
import com.report.entity.ManagerDetails;
import com.report.service.ManagerDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/managerDetails")
public class ManagerDetailsController {

    @Autowired
    private ManagerDetailsService managerDetailsService;

    @GetMapping("/all")
    public List<ManagerDetails> getAllManagerDetails() {
        return managerDetailsService.findAll();
    }

    @PostMapping("/save")
    public ManagerDetails createManagerDetails(@RequestBody ManagerDetailsDto managerDetailsDto) {
        return managerDetailsService.save(managerDetailsDto);
    }

//    @PutMapping("/update")
//    public ManagerDetails updateManagerDetails(@RequestBody ManagerDetailsDto managerDetailsDto) {
//        return managerDetailsService.update(managerDetailsDto);
//    }

    @DeleteMapping("/delete/{customerName}/{managerName}")
    public String deleteManagerDetails(@PathVariable String customerName, @PathVariable String managerName) {
        ManagerDetailsId id = new ManagerDetailsId(customerName, managerName);
        managerDetailsService.deleteById(id);
        return "deleted successfully";
    }


}
