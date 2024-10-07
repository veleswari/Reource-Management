package com.report.service;

import com.report.entity.OpenNeeds;
import com.report.entity.ResourceDetails;
import com.report.repository.ResourceDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;

@Service
public class ResourceDetailsExcelService {
    @Autowired
    ResourceDetailsRepository resourceDetailsRepository;

    public ByteArrayInputStream load() {
        List<ResourceDetails> resourceDetails = resourceDetailsRepository.findAll();

        ByteArrayInputStream in = ResourceDetailsExcelHelper.resourceDetailsToExcel(resourceDetails);
        return in;
    }
}
