package com.report.service;

import com.report.entity.AdminDetails;
import com.report.entity.User;
import com.report.repository.AdminDetailsRepository;
import com.report.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminDetailsService {

    @Autowired
    private AdminDetailsRepository adminDetailsRepository;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    UserRepository userRepository;


    private String getCurrentManagerName() {
        String email = authenticationService.getCurrentUsername();
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return user.get().getFullName();
        }
        throw new IllegalArgumentException("User not found");
    }

    public List<AdminDetails> getAllAdminDetails() {
        String managerName = getCurrentManagerName();
        return adminDetailsRepository.findBymanagerName(managerName);
    }

//    public AdminDetails getAdminDetailsById(Integer id) {
//        return repository.findById(id).orElse(null);
//    }

    public AdminDetails saveAdminDetails(AdminDetails adminDetails) {
        String managerName = getCurrentManagerName();
        adminDetails.setManagerName(managerName);
        return adminDetailsRepository.save(adminDetails);
    }

    public String updateAdminDetails(Integer id, AdminDetails adminDetails) {
        String managerName = getCurrentManagerName();
        if (adminDetailsRepository.existsById(id)) {
            adminDetails.setId(id);
            adminDetails.setManagerName(managerName);
            adminDetailsRepository.save(adminDetails);
            return "updated successfully";
        }
        return "can't update";
    }

    public String deleteAdminDetails(Integer id) {
        if (adminDetailsRepository.existsById(id)) {
            adminDetailsRepository.deleteById(id);
            return "deleted succsesfully";
        }
        return "can't delete";
    }
}
