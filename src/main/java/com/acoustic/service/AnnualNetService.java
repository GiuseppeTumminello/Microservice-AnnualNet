package com.acoustic.service;

import com.acoustic.rate.RatesConfigurationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
@RequiredArgsConstructor
public class AnnualNetService implements SalaryCalculatorService {

    public static final double MONTHS_NUMBER = 12.0;
    private final RatesConfigurationProperties rate;

    @Override
    public BigDecimal apply(BigDecimal grossMonthlySalary) {
        return (grossMonthlySalary.multiply(BigDecimal.valueOf(MONTHS_NUMBER))
                .compareTo(this.rate.getTaxGrossAmountThreshold()) < 0)
                ? getAnnualNetBasedOnRate(
                grossMonthlySalary,
                this.rate.getTaxRate17Rate())
                : getAnnualNetBasedOnRate(grossMonthlySalary, this.rate.getTaxRate32Rate());
    }

    @Override
    public String getDescription() {
        return "Annual net";
    }

    private BigDecimal calculateTotalZus(BigDecimal grossMonthlySalary) {
        return grossMonthlySalary.subtract(grossMonthlySalary.multiply(this.rate.getTotalZusRate())).setScale(2, RoundingMode.HALF_EVEN);
    }

    private BigDecimal calculateHealth(BigDecimal grossMonthlySalary) {
        return grossMonthlySalary.subtract(grossMonthlySalary.multiply(this.rate.getHealthRate())).setScale(2, RoundingMode.HALF_EVEN);
    }

    private BigDecimal calculateMonthlyNet(BigDecimal rate, BigDecimal salaryMinusHealth) {
        return salaryMinusHealth.subtract(salaryMinusHealth.multiply(rate)).setScale(2, RoundingMode.HALF_EVEN);
    }


    private BigDecimal getAnnualNetBasedOnRate(BigDecimal grossMonthlySalary, BigDecimal rate) {
        var salaryMinusTotalZus = this.calculateTotalZus(grossMonthlySalary);
        var salaryMinusHealth = this.calculateHealth(salaryMinusTotalZus);
        var salaryMonthlyNet = this.calculateMonthlyNet(rate, salaryMinusHealth);
        return salaryMonthlyNet.multiply(BigDecimal.valueOf(MONTHS_NUMBER).setScale(2, RoundingMode.HALF_EVEN)).setScale(2, RoundingMode.HALF_EVEN);

    }


}
