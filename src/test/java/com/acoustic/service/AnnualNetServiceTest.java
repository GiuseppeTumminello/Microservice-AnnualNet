package com.acoustic.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@ExtendWith(MockitoExtension.class)
class AnnualNetServiceTest {

    public static final String ANNUAL_NET_DESCRIPTION = "Annual net";

    @InjectMocks
    private AnnualNetService salaryCalculatorService;



    @Test
    void getDescription() {
        assertThat(salaryCalculatorService.getDescription()).isEqualTo(ANNUAL_NET_DESCRIPTION);
    }

    @ParameterizedTest
    @CsvSource({"4319.44, 51833.28", "5039.35, 60472.20", "10188.77,122265.24"})
    public void getAnnualNet(BigDecimal input, BigDecimal expected) {
        assertThat(salaryCalculatorService.apply(input)).isEqualTo(expected);
    }
}