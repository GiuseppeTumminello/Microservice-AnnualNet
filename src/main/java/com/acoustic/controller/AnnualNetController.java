package com.acoustic.controller;


import com.acoustic.entity.AnnualNet;
import com.acoustic.repository.AnnualNetRepository;
import com.acoustic.service.SalaryCalculatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.Map;


@RestController
@RequestMapping("/annualNet")
@RequiredArgsConstructor
@Validated
@CrossOrigin
public class AnnualNetController {

    public static final String DESCRIPTION = "description";
    public static final String VALUE = "value";
    private final AnnualNetRepository annualNetRepository;
    private final SalaryCalculatorService salaryCalculatorService;




    @PostMapping("/getAnnualNet/{grossMonthlySalary}")
    public Map<String, String> calculateAnnualNet(@PathVariable @Min(2000)BigDecimal grossMonthlySalary){
        var annualNet = this.salaryCalculatorService.apply(grossMonthlySalary);
        this.annualNetRepository.save(AnnualNet.builder().annualNetAmount(annualNet).build());
        return Map.of(DESCRIPTION,this.salaryCalculatorService.getDescription(), VALUE, String.valueOf(annualNet));
    }



}
