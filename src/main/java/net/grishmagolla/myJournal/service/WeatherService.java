package net.grishmagolla.myJournal.service;

import net.grishmagolla.myJournal.api.response.WeatherResponse;
import net.grishmagolla.myJournal.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    //@Value ->> security, flexibility, easy to change; final cannot be used; can be place in environment variable or applications.yml
    private String apiKey;

    private static final String API = "http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

    @Autowired
    private RestTemplate restTemplate;

    public WeatherResponse getWeather(String city) {
        String finalAPI = API.replace("CITY", city).replace("API_KEY", apiKey);
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(
                finalAPI, HttpMethod.GET, null, WeatherResponse.class);
        return response.getBody();
    }
}