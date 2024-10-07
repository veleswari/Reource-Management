package com.report.controller;

import com.report.dto.ResourceDetailsDto;
import com.report.entity.ResourceDetails;
import com.report.service.ResourceDetailsExcelService;
import com.report.service.ResourceDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/auth/resource")
public class ResourceDetailsController {

    @Autowired
    private ResourceDetailsService resourceDeatilsService;

    @Autowired
    private ResourceDetailsExcelService resourceDetailsExcelService;

    @GetMapping("/all")
    public List<ResourceDetails> getAllDetails() {
        return resourceDeatilsService.getAllDetails();
    }


    @GetMapping("/{customerName}/{stream}")
    public List<ResourceDetails> getAllDetailsByCustomer(@PathVariable(name = "customerName") String customerName, @PathVariable(name = "stream") String stream) {
        return resourceDeatilsService.getAllDetailsByCustomer(customerName, stream);

    }

    @PostMapping("/save")
    public ResourceDetails saveResources(@RequestBody ResourceDetailsDto resourceDetailsDto) {
        return resourceDeatilsService.saveResources(resourceDetailsDto);
    }

    @PutMapping("/update")
    public ResourceDetails editResources(@RequestBody ResourceDetailsDto resourceDetailsDto) {
        return resourceDeatilsService.editResources(resourceDetailsDto);
    }

    @DeleteMapping("/remove")
    public String deleteResources(@RequestBody ResourceDetails resourceDetails) {
        return resourceDeatilsService.deleteResources(resourceDetails);
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> getFile() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String timestamp = LocalDateTime.now().format(formatter);
        String filename = "resource_" + timestamp + ".xlsx";

        InputStreamResource file = new InputStreamResource(resourceDetailsExcelService.load());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
    }

    @GetMapping("/search")
    public List<String> searchText(@RequestParam String stream) {
        return resourceDeatilsService.findTextStartingWith(stream);
    }

    @GetMapping("/overview")
    public Page<ResourceDetails> getAllDetailsByPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return resourceDeatilsService.getAllDetailsByPagination(pageable);
    }


    @GetMapping("/manager-details")
    public List<ResourceDetails> getManagerDetails(
            @RequestParam Map<String, String> allParams,
            @RequestParam(defaultValue = "managerName") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Map<String, Object> filterCriteria = new HashMap<>(allParams);
        filterCriteria.remove("sortBy");
        filterCriteria.remove("direction");
        if ("oldest".equalsIgnoreCase(direction)) {
            sortBy = "requestedDate";  // Assuming 'requestedDate' is the date field to sort by
            direction = "asc";
        } else if ("newest".equalsIgnoreCase(direction)) {
            sortBy = "requestedDate";
            direction = "desc";
        }
        return resourceDeatilsService.getFilteredManagerDetails(filterCriteria, sortBy, direction);
    }


    @GetMapping("/filter")
    public List<ResourceDetails> getResourceDetails(
            @RequestParam(required = false) String customId,
            @RequestParam(required = false) Integer spid,
            @RequestParam(required = false) LocalDate requestedDate,
            @RequestParam(required = false) String customerMgrName,
            @RequestParam(required = false) Double billRate,
            @RequestParam(required = false) String overallStatus,
            @RequestParam(required = false) String internalExternal,
            @RequestParam(required = false) String resourceName,
            @RequestParam(required = false) Integer noOfYears,
            @RequestParam(required = false) LocalDate profileSharedDate,
            @RequestParam(required = false) LocalDate customerInterviewDate,
            @RequestParam(required = false) LocalDate l1InterviewDate,
            @RequestParam(required = false) LocalDate dateOfJoin,
            @RequestParam(required = false) String customerName,
            @RequestParam(required = false) String stream,
            @RequestParam(required = false) String managerName,
            @RequestParam(required = false) String skillSet
    ) {
        return resourceDeatilsService.getFilteredResourceDetails(
                customId, spid, requestedDate, customerMgrName, billRate, overallStatus, internalExternal, resourceName,
                noOfYears, profileSharedDate, customerInterviewDate, l1InterviewDate, dateOfJoin, customerName, stream,
                managerName, skillSet
        );
    }
}
