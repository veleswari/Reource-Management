package com.report.service;

import com.report.Util.ResourceDetailsSpecification;
import com.report.Util.SpecificationBuilder;
import com.report.dto.ResourceDetailsDto;
import com.report.entity.CustomerTrack;
import com.report.entity.ResourceDetails;
import com.report.entity.User;
import com.report.mapper.ResourceDetailsMapper;
import com.report.repository.CustomerTrackRepository;
import com.report.repository.ResourceDetailsRepository;
import com.report.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ResourceDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    private ResourceDetailsRepository resourceDetailsRepository;

    @Autowired
    private CustomerTrackRepository customerTrackRepository;

    private String getCurrentManagerName() {
        String email = authenticationService.getCurrentUsername();
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return user.get().getFullName();
        }
        throw new IllegalArgumentException("User not found");
    }

    public Page<ResourceDetails> getAllDetailsByPagination(Pageable pageable) {
        return resourceDetailsRepository.findAll(pageable);
    }

    public List<ResourceDetails> getAllDetails() {
        String managerName = getCurrentManagerName();
        return resourceDetailsRepository.findByManagerName(managerName);
    }

    public List<ResourceDetails> getAllDetailsByCustomer(String customerName, String stream) {
        String managerName = getCurrentManagerName();
        if ("all".equals(stream)) {
            return resourceDetailsRepository.findByCustomerNameAndManagerName(customerName, managerName);
        } else {
            return resourceDetailsRepository.findByCustomerNameAndStreamAndManagerName(customerName, stream, managerName);
        }
    }

    public ResourceDetails saveResources(ResourceDetailsDto resourceDetailsDto) {
        String managerName = getCurrentManagerName();
        resourceDetailsDto.setManagerName(managerName);
        String stream = resourceDetailsDto.getStream();
        String customerName = resourceDetailsDto.getCustomerName();
        Optional<CustomerTrack> customerTrack = customerTrackRepository.findByCustomerNameAndStream(customerName, stream);
        if (!customerTrack.isPresent()){
            CustomerTrack customerTrackNew = new CustomerTrack();
            customerTrackNew.setCustomerName(customerName);
            customerTrackNew.setStream(stream);
            customerTrackRepository.save(customerTrackNew);
        }
        return resourceDetailsRepository.save(ResourceDetailsMapper.INSTANCE.mapToResourceDetailsDto(resourceDetailsDto));
    }

    public ResourceDetails editResources(ResourceDetailsDto resourceDetailsDto) {
        String managerName = getCurrentManagerName();
        ResourceDetails existingResource = resourceDetailsRepository.findById(resourceDetailsDto.getCustomId())
                .orElseThrow(() -> new IllegalArgumentException("Resource not found"));

        if (!existingResource.getManagerName().equals(managerName)) {
            throw new SecurityException("You are not authorized to edit this resource");
        }

        resourceDetailsDto.setManagerName(managerName);
        return resourceDetailsRepository.save(ResourceDetailsMapper.INSTANCE.mapToResourceDetailsDto(resourceDetailsDto));
    }

    public String deleteResources(ResourceDetails resourceDetails) {
        resourceDetailsRepository.delete(resourceDetails);
        return "Resource deleted successfully";
    }

    public List<String> findTextStartingWith(String stream) {
        return resourceDetailsRepository.findByStreamStartingWith(stream);
    }


    public List<ResourceDetails> getFilteredManagerDetails(Map<String, Object> filterCriteria, String sortBy, String direction) {
        Specification<ResourceDetails> spec = SpecificationBuilder.getSpecifications(filterCriteria);

        Sort sort = Sort.by(direction.equalsIgnoreCase("asc") ? Sort.Order.asc(sortBy) : Sort.Order.desc(sortBy));

        return resourceDetailsRepository.findAll(spec, sort);
    }
    public List<ResourceDetails> getFilteredResourceDetails(
            String customId,Integer spid, LocalDate requestedDate, String customerMgrName,  Double billRate,String overallStatus, String internalExternal, String resourceName,Integer noOfYears,LocalDate profileSharedDate,LocalDate customerInterviewDate,
            LocalDate l1InterviewDate,LocalDate dateOfJoin,String customerName, String stream,  String managerName, String skillSet
    ) {
        Specification<ResourceDetails> specification = ResourceDetailsSpecification.getResourceDetailsSpecification(
                customId, spid, customerMgrName, billRate, overallStatus, internalExternal, resourceName,
                noOfYears, profileSharedDate, customerInterviewDate, l1InterviewDate, dateOfJoin, customerName, stream,
                managerName, skillSet
        );
        return resourceDetailsRepository.findAll(specification);
    }
}
