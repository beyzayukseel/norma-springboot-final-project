package com.tr.beyzanur.exchange;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public record Exchange(RestTemplate restTemplate) {

    public ResponseEntity<ExchangeDto> getExchange(String currency) {
        String api = "https://api.apilayer.com/exchangerates_data/latest?symbols=EUR,USD,TRY&base=" + currency;
        HttpHeaders headers = new HttpHeaders();
        headers.set("apikey", "8noh00RR5f6JUYtw2LC4t9NM4I33THGI");
        HttpEntity<String> httpEntity = new HttpEntity<>("apikey", headers);
        ResponseEntity<ExchangeDto> exchangeModelResponseEntity = restTemplate.exchange(api,
                HttpMethod.GET, httpEntity, ExchangeDto.class);
        ExchangeDto exchangeDto = exchangeModelResponseEntity.getBody();
        return ResponseEntity.ok(exchangeDto);
    }
}


