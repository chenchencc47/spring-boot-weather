package com.mnls.controller;

import com.mnls.pojo.WeatherFullVO;
import com.mnls.pojo.WeatherResponseAll;
import com.mnls.pojo.WeatherResponseBase;
import com.mnls.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    //高德天气接口本身既支持中文城市名，也支持城市编码（adcode）作为 city 参数的值。
    @GetMapping("/{city}")
    public WeatherResponseBase getWeatherByCity(@PathVariable String city) throws IOException {
        return weatherService.getWeatherByCityBase(city);
    }

//    @GetMapping("/{city}")
//    public WeatherResponseAll getWeatherByCity(@PathVariable String city) throws IOException {
//        return weatherService.getWeatherByCityAll(city);
//    }

    @GetMapping("/full/{city}")
    public WeatherFullVO getWeatherFull(@PathVariable String city) throws IOException {
        return weatherService.getWeatherFull(city);
    }
}
