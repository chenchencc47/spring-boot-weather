package com.mnls.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class SuggestService {

    // ① 把申请到的 key 写配置（application.yml）
    @Value("${kimi.key}")
    private String kimiKey;

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * 调 Kimi 生成生活建议
     * @param city   城市
     * @param weather 天气现象
     * @param temp   温度
     * @return 一句纯文本建议
     */
    public String getSuggest(String city, String weather, String temp) throws IOException {
        String prompt = String.format("请你用一句话给「%s」的市民提供生活建议（穿衣/出行/运动），当前天气「%s」%s℃。",
                city, weather, temp);

        // ② 标准 OpenAI 格式
        Map<String, Object> body = Map.of(
                "model", "moonshot-v1-8k",
                "messages", List.of(          // ← 必须是数组
                        Map.of("role", "user", "content", prompt)
                )
        );

        Request req = new Request.Builder()
                .url("https://api.moonshot.cn/v1/chat/completions")
                .addHeader("Authorization", "Bearer " + kimiKey)
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create(mapper.writeValueAsBytes(body)))
                .build();

        try (Response resp = client.newCall(req).execute()) {
            if (!resp.isSuccessful()) throw new IOException("Kimi 调用失败 " + resp);
            String json = resp.body().string();
            // 提取回复内容
            return mapper.readTree(json)
                    .path("choices").get(0)
                    .path("message")
                    .path("content").asText();
        }
    }
}