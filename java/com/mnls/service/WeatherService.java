package com.mnls.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mnls.pojo.WeatherFullVO;
import com.mnls.pojo.WeatherResponseAll;
import com.mnls.pojo.WeatherResponseBase;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class WeatherService {

    //如需 7 天预报，把 extensions=base 改成 extensions=all，再解析 forecasts 数组即可。
    //高德只用小写
    private static final String GAODE_KEY = "24edb42e92816e57e89b2fb2a11e9347";
    private static final String GAODE_URL_BASE =
            "https://restapi.amap.com/v3/weather/weatherInfo?key=%s&city=%s&extensions=base";
    private static final String GAODE_URL_ALL =
            "https://restapi.amap.com/v3/weather/weatherInfo?key=%s&city=%s&extensions=all";

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    //实时报告用WeatherResponseBase，七天预报用WeatherResponseAll
    public WeatherResponseBase getWeatherByCityBase(String city) throws IOException {
        String url = String.format(GAODE_URL_BASE, GAODE_KEY, city);

        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("高德接口异常 " + response);
            // 直接把整个 JSON 映射成 WeatherResponseBase

            return mapper.readValue(response.body().byteStream(), WeatherResponseBase.class);
        }
    }

    public WeatherResponseAll getWeatherByCityAll(String city) throws IOException {
        String url = String.format(GAODE_URL_ALL, GAODE_KEY, city);

        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("高德接口异常 " + response);
            // 直接把整个 JSON 映射成 WeatherResponseBase
            //实时报告用WeatherResponseBase，七天预报用WeatherResponseAll
            return mapper.readValue(response.body().byteStream(), WeatherResponseAll.class);
        }
    }

    // 新方法：合并 VO
    public WeatherFullVO getWeatherFull(String city) throws IOException {
        WeatherResponseBase base = getWeatherByCityBase(city);
        WeatherResponseAll  all  = getWeatherByCityAll(city);

        WeatherFullVO vo = new WeatherFullVO();
        vo.setLives(base.getLives());
        vo.setForecasts(all.getForecasts());
        return vo;
    }

}
