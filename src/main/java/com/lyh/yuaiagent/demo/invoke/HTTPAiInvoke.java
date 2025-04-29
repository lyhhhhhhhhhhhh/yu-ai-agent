package com.lyh.yuaiagent.demo.invoke;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

public class HTTPAiInvoke {
    public static void main(String[] args) {
        // 替换成你的 DashScope API Key
        String apiKey = TestAPIKey.API_KEY;

        // 构建请求体
        JSONObject json = new JSONObject();
        json.put("model", "qwen-plus");

        JSONObject input = new JSONObject();
        JSONArray messages = new JSONArray();

        JSONObject systemMessage = new JSONObject();
        systemMessage.put("role", "system");
        systemMessage.put("content", "You are a helpful assistant.");

        JSONObject userMessage = new JSONObject();
        userMessage.put("role", "user");
        userMessage.put("content", "你是谁？");

        messages.add(systemMessage);
        messages.add(userMessage);
        input.put("messages", messages);

        json.put("input", input);

        JSONObject parameters = new JSONObject();
        parameters.put("result_format", "message");
        json.put("parameters", parameters);

        // 发送 POST 请求
        HttpResponse response = HttpRequest.post("https://dashscope.aliyuncs.com/api/v1/services/aigc/text-generation/generation")
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .body(json.toString()) // JSON格式的请求体
                .timeout(5000) // 超时5秒
                .execute();

        // 打印响应
        System.out.println(response.body());
    }
}