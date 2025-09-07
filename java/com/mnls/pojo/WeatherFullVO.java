package com.mnls.pojo;

import lombok.Data;

import java.util.List;

@Data
public class WeatherFullVO {
    private List<WeatherResponseBase.Live> lives;      // 实时
    private List<WeatherResponseAll.Forecast> forecasts; // 7 日
}