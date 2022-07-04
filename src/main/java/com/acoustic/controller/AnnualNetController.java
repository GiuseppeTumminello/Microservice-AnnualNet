package com.acoustic.controller;


import com.acoustic.entity.AnnualNet;
import com.acoustic.repository.AnnualNetRepository;
import com.acoustic.service.SalaryCalculatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;


@RestController
@RequestMapping("/annualNet")
@RequiredArgsConstructor
@Validated
public class AnnualNetController {

    private static final String MONTHLY_NET_ENDPOINT = "http://MONTHLY-NET/monthlyNet/getMonthlyNet/";
    private static final Object MONTHLY_NET_DESCRIPTION = "Monthly net";
    private final AnnualNetRepository annualNetRepository;
    private final SalaryCalculatorService salaryCalculatorService;

    private final RestTemplate restTemplate;


    @PostMapping("/getAnnualNet/{grossMonthlySalary}")
    public Map<String, BigDecimal> calculateTotalZus(@PathVariable @Min(2000)BigDecimal grossMonthlySalary){
        var annualNet = salaryCalculatorService.apply(getMonthlyNet(grossMonthlySalary));
        this.annualNetRepository.save(AnnualNet.builder().annualGrossAmount(annualNet).build());
        return new LinkedHashMap<>(Map.of(salaryCalculatorService.getDescription(), annualNet));
    }


    public BigDecimal getMonthlyNet(BigDecimal grossMonthlySalary) {
        var totalZus = Objects.requireNonNull(this.restTemplate.postForEntity(MONTHLY_NET_ENDPOINT + grossMonthlySalary, HttpMethod.POST, HashMap.class).getBody()).get(MONTHLY_NET_DESCRIPTION);
        return BigDecimal.valueOf(((Double) (totalZus)));
    }
}
