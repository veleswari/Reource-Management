package com.report.controller;

import com.report.dto.OpenNeedsRequestdto;
import com.report.entity.OpenNeeds;
import com.report.service.OpenNeedsSevice;
import com.report.service.OpenneedsExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/OpenNeeds")
public class OpenNeedsController {

    @Autowired
    private OpenNeedsSevice openNeedsSevice;

    @Autowired
    private OpenneedsExcelService openneedsExcelService;

    @GetMapping("/all")
    public Page<OpenNeeds> getAllOpenNeeds(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size) {
        return openNeedsSevice.getAllOpenNeeds(page, size);

    }

    @PostMapping("/save")
    public ResponseEntity<OpenNeeds> createOpenNeeds(@RequestBody OpenNeedsRequestdto requestDTO) {
        OpenNeeds savedOpenNeeds = openNeedsSevice.saveOpenNeeds(requestDTO);
        return new ResponseEntity<>(savedOpenNeeds, HttpStatus.CREATED);
    }

    @GetMapping("/{spid}")
    public List<OpenNeeds> getRecordsBySpid(@PathVariable Integer spid) {
        return openNeedsSevice.getRecordsBySpid(spid);
    }

    @PutMapping("/update")
    public OpenNeeds updateRecord(@RequestBody OpenNeedsRequestdto requestdto) {
        return openNeedsSevice.updateRecord(requestdto);
    }

    @DeleteMapping("/delete/{customId}")
    public ResponseEntity<String> deleteRecord(@PathVariable String customId) {
        String result = openNeedsSevice.deleteRecordBySpid(customId);
        if (result.equals("Record deleted successfully")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(404).body(result);
        }
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> getFile() {
        String filename = "openneds.xlsx";
        InputStreamResource file = new InputStreamResource(openneedsExcelService.load());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
    }
}
