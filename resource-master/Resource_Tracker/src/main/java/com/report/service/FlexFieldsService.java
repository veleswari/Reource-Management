package com.report.service;

import com.report.dto.FlexFieldsDto;
import com.report.entity.FlexFields;
import com.report.entity.User;
import com.report.mapper.FlexFieldsMapper;
import com.report.repository.FlexFieldsRepository;
import com.report.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FlexFieldsService {
    @Autowired
    FlexFieldsRepository flexFieldsRepository;

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

    public List<FlexFields> getAll(){
        String managerName = getCurrentManagerName();
        return flexFieldsRepository.findBymanagerName(managerName);
    }

    public FlexFields saveFlexField(FlexFieldsDto flexFieldsDto) {
        String managerName = getCurrentManagerName();
        flexFieldsDto.setManagerName(managerName);
        return flexFieldsRepository.save(FlexFieldsMapper.INSTANCE.mapToFlexFieldsEntity(flexFieldsDto));
    }

    public List<String> getByCustomerNameAndSkill(String customerName,String skillSet){
        return flexFieldsRepository.findFlexValueByCustomerNameAndSkillSet(customerName, skillSet);
    }

    public FlexFields updateFlexFields(FlexFieldsDto flexFieldsDto){
        FlexFields flexFields = flexFieldsRepository.findByCustomId(flexFieldsDto.getCustomId());
        flexFields.setCustomerName(flexFieldsDto.getCustomerName());
        flexFields.setSkillSet(flexFieldsDto.getSkillSet());
        flexFields.setFlexValue(flexFieldsDto.getFlexValue());

        return flexFieldsRepository.save(flexFields);
    }


    public String deleteFlex(String customId){
        if (flexFieldsRepository.findByCustomId(customId) != null) {
            flexFieldsRepository.deleteById(customId);
            return "Flex field deleted successfully";

        } else {
            return "Flex field not found";

        }

    }
    public List<String> getSkillSetForCustomer(String customerName){
        return flexFieldsRepository.findSkillSetByCustomerName(customerName);
    }
    public List<FlexFieldsDto> getFlexFieldsBySkillSet(String skillSet) {
        List<FlexFields> flexFields = flexFieldsRepository.findBySkillSet(skillSet);
        String managerName = getCurrentManagerName();
        return flexFields.stream()
                .map(field -> new FlexFieldsDto(
                        field.getCustomId(),
                        field.getManagerName(),
                        field.getCustomerName(),
                        field.getSkillSet(),
                        field.getFlexValue()))
                .collect(Collectors.toList());
    }
}
