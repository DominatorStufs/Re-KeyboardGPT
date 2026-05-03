package tn.amin.keyboard_gpt.llm.client;

import org.json.JSONObject;
import org.reactivestreams.Publisher;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import tn.amin.keyboard_gpt.llm.LanguageModel;
import tn.amin.keyboard_gpt.llm.publisher.ExceptionPublisher;

public class CodexAPIClient extends LanguageModelClient {

    @Override
    public Publisher<String> submitPrompt(String prompt, String systemMessage) {
        String model = getSubModel();
        if (model == null || model.isEmpty()) {
            model = "gpt-5";
        }

        String encodedPrompt;
        try {
            encodedPrompt = java.net.URLEncoder.encode(prompt, StandardCharsets.UTF_8.name());
        } catch (Exception e) {
            return new ExceptionPublisher(e);
        }

        String urlStr = "https://chatbot.codexapi.workers.dev/?prompt=" + encodedPrompt + "&model=" + model;

        return subscriber -> {
            new Thread(() -> {
                try {
                    HttpURLConnection con = (HttpURLConnection) new URL(urlStr).openConnection();
                    con.setRequestMethod("GET");
                    con.setRequestProperty("Accept", "application/json");
                    con.setConnectTimeout(15000);
                    con.setReadTimeout(30000);

                    int responseCode = con.getResponseCode();
                    BufferedReader reader;
                    if (responseCode == 200) {
                        reader = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
                    } else {
                        reader = new BufferedReader(new InputStreamReader(con.getErrorStream(), StandardCharsets.UTF_8));
                    }

                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                    reader.close();

                    String result = extractResponse(sb.toString());
                    subscriber.onNext(result);
                    subscriber.onComplete();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }).start();
        };
    }

    private String extractResponse(String raw) {
        try {
            JSONObject json = new JSONObject(raw);
            if (json.has("answer"))   return json.getString("answer");
            if (json.has("result"))   return json.getString("result");
            if (json.has("response")) return json.getString("response");
            if (json.has("text"))     return json.getString("text");
            if (json.has("content"))  return json.getString("content");
            if (json.has("message"))  return json.getString("message");
            if (json.has("output"))   return json.getString("output");
        } catch (Exception ignored) {}
        return raw.trim();
    }

    @Override
    public LanguageModel getLanguageModel() {
        return LanguageModel.CodexAPI;
    }
}
