package com.mnls.controller;

import com.mnls.service.SuggestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/ai")
public class AiController {

    @Autowired
    private SuggestService suggestService;

    /**
     * 根据天气获取 Kimi 生活建议
     * GET /ai/suggest?city=广州&weather=晴&temp=32
     */
    @GetMapping("/suggest")
    public Map<String, String> suggest(@RequestParam String city,
                                       @RequestParam String weather,
                                       @RequestParam String temp) throws IOException {
        String advice = suggestService.getSuggest(city, weather, temp);
        return Map.of("advice", advice);
    }
}