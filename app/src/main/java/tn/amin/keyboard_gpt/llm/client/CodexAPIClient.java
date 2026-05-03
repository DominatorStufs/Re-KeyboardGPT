package tn.amin.keyboard_gpt.llm.client;

import org.json.JSONObject;
import org.reactivestreams.Publisher;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import tn.amin.keyboard_gpt.llm.LanguageModel;
import tn.amin.keyboard_gpt.llm.publisher.ExceptionPublisher;
import tn.amin.keyboard_gpt.llm.publisher.InternetRequestPublisher;

public class CodexAPIClient extends LanguageModelClient {

    @Override
    public Publisher<String> submitPrompt(String prompt, String systemMessage) {
        // CodexAPI FREE hai - API key ki zarurat nahi!
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

        String url = "https://chatbot.codexapi.workers.dev/?prompt=" + encodedPrompt + "&model=" + model;

        HttpURLConnection con;
        try {
            con = (HttpURLConnection) new URL(url).openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/json");
            con.setConnectTimeout(15000);
            con.setReadTimeout(30000);

            InternetRequestPublisher publisher = new InternetRequestPublisher(
                    (s, reader) -> {
                        String response = reader.lines().collect(Collectors.joining(""));
                        s.onNext(extractResponse(response));
                    },
                    (s, reader) -> {
                        String response = reader.lines().collect(Collectors.joining(""));
                        throw new IllegalArgumentException("CodexAPI Error: " + response);
                    });

            InputStream inputStream = sendRequest(con, "", publisher);
            publisher.setInputStream(inputStream);
            return publisher;
        } catch (Throwable t) {
            return new ExceptionPublisher(t);
        }
    }

    private String extractResponse(String raw) {
        try {
            JSONObject json = new JSONObject(raw);
            if (json.has("result"))   return json.getString("result");
            if (json.has("response")) return json.getString("response");
            if (json.has("text"))     return json.getString("text");
            if (json.has("content"))  return json.getString("content");
        } catch (Exception ignored) {}
        return raw.trim();
    }

    @Override
    public LanguageModel getLanguageModel() {
        return LanguageModel.CodexAPI;
    }
}
