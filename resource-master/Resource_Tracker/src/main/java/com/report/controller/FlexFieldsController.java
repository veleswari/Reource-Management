package com.report.controller;

import com.report.dto.FlexFieldsDto;
import com.report.dto.FlexFieldsFilter;
import com.report.entity.FlexFields;
import com.report.service.FlexFieldsFilteringService;
import com.report.service.FlexFieldsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flexFields")
public class FlexFieldsController {
    @Autowired
    FlexFieldsService flexFieldsService;
    @Autowired
  FlexFieldsFilteringService flexFieldsFilteringService;
    @GetMapping("/all")
    public List<FlexFields> getAll(){
        return flexFieldsService.getAll();
    }

    @GetMapping("/values")
    public List<String> getByCustomerNameAndSkill(@RequestParam(name = "customerName")String customerName,@RequestParam(name = "skillSet")String skillSet){
        return flexFieldsService.getByCustomerNameAndSkill(customerName, skillSet);
    }

    @PostMapping("/save")
    public FlexFields saveFlexField(@RequestBody FlexFieldsDto flexFieldsDto){
        return flexFieldsService.saveFlexField(flexFieldsDto);
    }

    @PutMapping("/update")
    public FlexFields updateFlexFields(@RequestBody FlexFieldsDto flexFieldsDto){
        return flexFieldsService.updateFlexFields(flexFieldsDto);
    }

    @DeleteMapping("/delete")
    public String deleteFlex(@RequestParam(name = "customId") String customId){
        return flexFieldsService.deleteFlex(customId);
    }

    @GetMapping("/skillset")
    public List<String> getSkillSetForCustomer(@RequestParam(name = "customerName") String customerName){
        return flexFieldsService.getSkillSetForCustomer(customerName);
    }
    @GetMapping("/flex-fields")
    public List<FlexFieldsDto> getFlexFieldsBySkillSet(@RequestParam String skillSet) {
        return flexFieldsService.getFlexFieldsBySkillSet(skillSet);
    }
    @GetMapping("/flexfield")
    public ResponseEntity<List<FlexFields>> filterCustomers(FlexFieldsFilter filter) {
        List<FlexFields> customers = flexFieldsFilteringService.filterCustomers(filter);
        return ResponseEntity.ok(customers);
    }
}
