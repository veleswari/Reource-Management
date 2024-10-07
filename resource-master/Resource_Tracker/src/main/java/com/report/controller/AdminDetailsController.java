package com.report.controller;

import com.report.dto.AdminDetailsFilter;
import com.report.entity.AdminDetails;
import com.report.service.AdminDetailsFilterService;
import com.report.service.AdminDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/auth/admin")
public class AdminDetailsController {

    @Autowired
    private AdminDetailsService service;
    @Autowired
    private AdminDetailsFilterService adminDetailsService;
    @GetMapping("/all")
    public List<AdminDetails> getAllAdminDetails() {
        return service.getAllAdminDetails();
    }

//    @GetMapping("/{id}")
//    public AdminDetails getAdminDetailsById(@PathVariable Integer id) {
//        AdminDetails adminDetails = service.getAdminDetailsById(id);
//        if (adminDetails != null) {
//            return adminDetails;
//        } else {
//            return null;
//        }
//    }

    @PostMapping("/save")
    public AdminDetails saveAdminDetails(@RequestBody AdminDetails adminDetails) {
        return service.saveAdminDetails(adminDetails);
    }

    @PutMapping("/edit/{id}")
    public String updateAdminDetails(@PathVariable Integer id, @RequestBody AdminDetails adminDetails) {
        service.updateAdminDetails(id, adminDetails);
        return "updated successfully";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteAdminDetails(@PathVariable Integer id) {
        String deletedId = service.deleteAdminDetails(id);
        if (deletedId.isEmpty()) {
            return "can't delete";
        }
        return "deleted succesfully";
    }
    @GetMapping(
            "/admin-filter"
    )
    public ResponseEntity<List<AdminDetails>>
    filterAdminDetails
            (AdminDetailsFilter filter) {
        List<AdminDetails> adminDetailsList =
                adminDetailsService
                        .filterAdminDetails(filter);
        return
                ResponseEntity.
                        ok
                                (adminDetailsList);
    }
}
