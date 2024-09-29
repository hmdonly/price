package com.module.price.client;

import com.module.price.client.response.CurrencyResponse;
import com.module.price.enums.ErrorMessage;
import com.module.price.exception.PriceCalculationException;
import io.github.resilience4j.retry.annotation.Retry;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;


@Component
@Slf4j
public class CurrencyExchangeClient {

    @Value("${exchange.api.key}") //
    private String apiKey;

    @Value("${exchange.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    public CurrencyExchangeClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Retry(name = "currency_exchange")
    @CircuitBreaker(name = "currency_exchange")
    @Cacheable(value = "exchangeRates", key = "#baseCurrency + '_' + #targetCurrency")
    public double getExchangeRate(String baseCurrency, String targetCurrency) {
        CurrencyResponse response = restTemplate.getForObject(apiUrl, CurrencyResponse.class,
                apiKey, baseCurrency);

        if (response != null && response.getConversion_rates() != null) {
            Double rate = response.getConversion_rates().get(targetCurrency);
            if (rate != null) {
                return rate;
            } else {
                throw new PriceCalculationException(ErrorMessage.CURRENCY_EXCHANGE_ERROR);
            }
        } else {
            throw new RuntimeException("Failed to retrieve exchange rates.");
        }
    }


}
