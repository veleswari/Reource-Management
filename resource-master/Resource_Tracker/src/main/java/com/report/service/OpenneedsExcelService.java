package com.report.service;

import com.report.entity.OpenNeeds;
import com.report.repository.OpenNeedsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;

@Service
public class OpenneedsExcelService {
    @Autowired
    OpenNeedsRepository openNeedsRepository;

    public ByteArrayInputStream load() {
        List<OpenNeeds> openNeeds = openNeedsRepository.findAll();

        ByteArrayInputStream in = OpenneedsExcelHelper.openneedsToExcel(openNeeds);
        return in;
    }
}
