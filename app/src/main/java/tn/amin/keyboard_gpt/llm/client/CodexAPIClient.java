package tn.amin.keyboard_gpt.llm.client;

import org.json.JSONObject;
import org.reactivestreams.Publisher;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

import tn.amin.keyboard_gpt.llm.LanguageModel;
import tn.amin.keyboard_gpt.llm.LanguageModelField;
import tn.amin.keyboard_gpt.llm.publisher.ExceptionPublisher;
import tn.amin.keyboard_gpt.llm.publisher.InternetRequestPublisher;

public class CodexAPIClient extends LanguageModelClient {
    @Override
    public Publisher<String> submitPrompt(String prompt, String systemMessage) {
        if (getApiKey() == null || getApiKey().isEmpty()) {
            return LanguageModelClient.MISSING_API_KEY_PUBLISHER;
        }

        if (systemMessage == null) {
            systemMessage = getDefaultSystemMessage();
        }

        // CodexAPI endpoint - no base URL needed, direct to worker endpoint
        String url = "https://chatbot.codexapi.workers.dev/?prompt=" + 
                     java.net.URLEncoder.encode(prompt, java.nio.charset.StandardCharsets.UTF_8) + 
                     "&model=" + getSubModel();
        
        HttpURLConnection con;
        try {
            con = (HttpURLConnection) new URL(url).openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");

            InternetRequestPublisher publisher = new InternetRequestPublisher(
                    (s, reader) -> {
                        String response = reader.lines().collect(Collectors.joining(""));
                        JSONObject responseJson = new JSONObject(response);
                        
                        if (responseJson.has("result")) {
                            s.onNext(responseJson.getString("result"));
                        } else if (responseJson.has("response")) {
                            s.onNext(responseJson.getString("response"));
                        } else {
                            s.onNext(response);
                        }
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

    @Override
    public LanguageModel getLanguageModel() {
        return LanguageModel.CodexAPI;
    }
}