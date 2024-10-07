package com.report.controller;

import com.report.dto.OpenNeedsMasterdto;
import com.report.entity.OpenNeedsMaster;
import com.report.service.OpenNeedsMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/master")
public class OpenNeedsMasterController {
    @Autowired
    private OpenNeedsMasterService openNeedsMasterService;

    @PutMapping("/update")
    public OpenNeedsMasterdto updateRecordForMaster(@RequestBody OpenNeedsMasterdto openNeedsMasterdto){
        return openNeedsMasterService.updateRecordForMaster(openNeedsMasterdto);
    }


    @PostMapping("/save")
    public ResponseEntity<OpenNeedsMasterdto> createOpenNeeds(@RequestBody OpenNeedsMasterdto openNeedsMasterdto) {
        OpenNeedsMasterdto savedOpenNeeds = openNeedsMasterService.saveOpenNeeds(openNeedsMasterdto);
        return new ResponseEntity<>(savedOpenNeeds, HttpStatus.CREATED);
    }


    @GetMapping("/all")
    public Page<OpenNeedsMaster> getAllRecords(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size){
        return openNeedsMasterService.getAllRecords(page,size);

    }

}
