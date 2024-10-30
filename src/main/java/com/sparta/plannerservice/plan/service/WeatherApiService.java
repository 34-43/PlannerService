package com.sparta.plannerservice.plan.service;

import com.sparta.plannerservice.common.enums.FailedRequest;
import com.sparta.plannerservice.common.exception.FailedRequestException;
import com.sparta.plannerservice.plan.dto.WeatherApiResDto;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Service
public class WeatherApiService {
    private final RestTemplate restTemplate;

    public WeatherApiService(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public ResponseEntity<String> fetchWeather(LocalDate date) {
        // 정적인 API 경로 정의
        URI uri = UriComponentsBuilder
                .fromUriString("https://f-api.github.io")
                .path("/f-api/weather.json")
                .encode().build().toUri();

        // 전체 반환값을 Dto 리스트로 전환
        List<WeatherApiResDto> response = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<WeatherApiResDto>>() {
        }).getBody();
        if (Objects.isNull(response)) {
            throw new FailedRequestException(FailedRequest.API_WEATHER_MALFUNCTION);
        }

        // 추출할 일자를 API 와 동일한 형태로 변형
        String dateString = date.format(DateTimeFormatter.ofPattern("MM-dd"));

        // 필터로 해당 일자의 날씨만 추출하여 반환
        WeatherApiResDto today = response.stream().filter(w -> w.getDate().equals(dateString)).findFirst().orElseThrow(() -> new FailedRequestException(FailedRequest.API_WEATHER_MALFUNCTION));
        return ResponseEntity.ok(today.getWeather());
    }
}
