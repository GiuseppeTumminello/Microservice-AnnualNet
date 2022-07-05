package com.acoustic.service;

import com.acoustic.rate.RatesConfigurationProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class AnnualNetServiceTest {

    public static final double TOTAL_ZUS_RATE = 0.1371;
    public static final double HEALTH_RATE = 0.09;
    public static final int TAX_GROSS_AMOUNT_THRESHOLD = 120000;
    public static final String ANNUAL_NET_DESCRIPTION = "Annual net";
    @InjectMocks
    private AnnualNetService annualNetService;
    @Mock
    private RatesConfigurationProperties ratesConfigurationProperties;

    @Test
    void getDescription() {
        assertThat(this.annualNetService.getDescription()).isEqualTo(ANNUAL_NET_DESCRIPTION);
    }

    @ParameterizedTest
    @CsvSource({"10188.77,82259.16, 0.1432", "15999.72, 129174.00, 0.1432"})
    public void getAnnualNetBasedOnRate32(BigDecimal input, BigDecimal expected, BigDecimal rate) {
        given(this.ratesConfigurationProperties.getTaxRate32Rate()).willReturn(rate);
        given(this.ratesConfigurationProperties.getTotalZusRate()).willReturn(BigDecimal.valueOf(TOTAL_ZUS_RATE));
        given(this.ratesConfigurationProperties.getHealthRate()).willReturn(BigDecimal.valueOf(HEALTH_RATE));
        given(this.ratesConfigurationProperties.getTaxGrossAmountThreshold()).willReturn(BigDecimal.valueOf(TAX_GROSS_AMOUNT_THRESHOLD));
        assertThat(this.annualNetService.apply(input)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"6000,51833.28, 0.0832", "7000, 60472.20, 0.0832", "8555,73905.72, 0.0832"})
    public void getAnnualNetBasedOnRate17(BigDecimal input, BigDecimal expected, BigDecimal rate) {
        given(this.ratesConfigurationProperties.getTaxRate17Rate()).willReturn(rate);
        given(this.ratesConfigurationProperties.getTotalZusRate()).willReturn(BigDecimal.valueOf(TOTAL_ZUS_RATE));
        given(this.ratesConfigurationProperties.getHealthRate()).willReturn(BigDecimal.valueOf(HEALTH_RATE));
        given(this.ratesConfigurationProperties.getTaxGrossAmountThreshold()).willReturn(BigDecimal.valueOf(TAX_GROSS_AMOUNT_THRESHOLD));
        assertThat(this.annualNetService.apply(input)).isEqualTo(expected);
    }
}