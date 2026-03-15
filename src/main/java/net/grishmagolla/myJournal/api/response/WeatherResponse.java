package net.grishmagolla.myJournal.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WeatherResponse {

    private Current current;

    private Location location;

    @Data
    public static class Current {
        private int temperature;

        @JsonProperty("weather_descriptions")
        private String[] weatherDescriptions;

        @JsonProperty("wind_speed")
        private int windSpeed;

        private int humidity;

        private int feelslike;
    }

    @Data
    public static class Location {
        private String name;
        private String country;
        private String region;
    }
}