package com.mnls.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)   // <-- 忽略未知字段
public class WeatherResponseBase {
    private String status;
    private String info;
    private String infocode;
    private List<Live> lives;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Live {
        private String province;
        private String city;
        private String adcode;
        private String weather;
        private String temperature;
        private String winddirection;
        private String windpower;
        private String humidity;
        private String reporttime;
    }
}
