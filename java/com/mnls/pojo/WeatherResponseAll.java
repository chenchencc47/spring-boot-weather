package com.mnls.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponseAll {
    private String status;
    private String info;
    private String infocode;
    private List<Live> lives;          // 实时天气（仍会返回）
    private List<Forecast> forecasts;  // 7 天预报

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Live { private String province;
        private String city;
        private String adcode;
        private String weather;
        private String temperature;
        private String winddirection;
        private String windpower;
        private String humidity;
        private String reporttime;
    }   // 原样保留

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Forecast {
        private String city;
        private String adcode;
        private String province;
        private List<Cast> casts;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Cast {
        private String date;         // 日期
        private String week;         // 星期
        private String dayweather;   // 白天天气
        private String nightweather; // 夜间天气
        private String daytemp;      // 白天温度
        private String nighttemp;    // 夜间温度
        private String daywind;      // 白天风向
        private String nightwind;    // 夜间风向
        private String daypower;     // 白天风力
        private String nightpower;   // 夜间风力
    }
}