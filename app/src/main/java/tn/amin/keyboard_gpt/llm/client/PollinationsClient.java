package tn.amin.keyboard_gpt.llm.client;

import org.reactivestreams.Publisher;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import tn.amin.keyboard_gpt.llm.LanguageModel;
import tn.amin.keyboard_gpt.llm.publisher.ExceptionPublisher;

public class PollinationsClient extends LanguageModelClient {

    public static final String[] AVAILABLE_MODELS = {
    "openai",           // GPT-4o (Default)
    "openai-large",     // GPT-4o Large
    "openai-reasoning", // o1 Reasoning
    "llama",            // Llama 3.3 70B
    "llamalight",       // Llama 3.1 8B
    "gemini",           // Gemini 2.0 Flash
    "gemini-thinking",  // Gemini 2.0 Thinking
    "deepseek",         // DeepSeek V3
    "deepseek-r1",      // DeepSeek R1
    "mistral",          // Mistral Small
    "qwen-coder"        // Qwen Coder
};

    @Override
    public Publisher<String> submitPrompt(String prompt, String systemMessage) {
        String model = getSubModel();
        if (model == null || model.isEmpty()) {
            model = "openai";
        }

        String encodedPrompt;
        String encodedSystem;
        try {
            encodedPrompt = java.net.URLEncoder.encode(prompt, StandardCharsets.UTF_8.name());
            encodedSystem = (systemMessage != null && !systemMessage.isEmpty())
                    ? java.net.URLEncoder.encode(systemMessage, StandardCharsets.UTF_8.name())
                    : "";
        } catch (Exception e) {
            return new ExceptionPublisher(e);
        }

        String urlStr = "https://text.pollinations.ai/" + encodedPrompt
                + "?model=" + model
                + (encodedSystem.isEmpty() ? "" : "&system=" + encodedSystem);

        return subscriber -> {
            new Thread(() -> {
                try {
                    HttpURLConnection con = (HttpURLConnection) new URL(urlStr).openConnection();
                    con.setRequestMethod("GET");
                    con.setRequestProperty("Accept", "text/plain");
                    con.setConnectTimeout(15000);
                    con.setReadTimeout(60000);

                    int responseCode = con.getResponseCode();
                    BufferedReader reader;
                    if (responseCode == 200) {
                        reader = new BufferedReader(new InputStreamReader(
                                con.getInputStream(), StandardCharsets.UTF_8));
                    } else {
                        reader = new BufferedReader(new InputStreamReader(
                                con.getErrorStream(), StandardCharsets.UTF_8));
                    }

                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line).append("\n");
                    }
                    reader.close();

                    String result = sb.toString().trim();
                    if (result.isEmpty()) {
                        result = "No response from Pollinations";
                    }

                    subscriber.onNext(result);
                    subscriber.onComplete();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }).start();
        };
    }

    @Override
    public LanguageModel getLanguageModel() {
        return LanguageModel.Pollinations;
    }
}
