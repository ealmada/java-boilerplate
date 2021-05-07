package com.asapp.backend.challenge;

import spark.utils.IOUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

import static org.assertj.core.api.Fail.fail;

public class ApiTestUtils {
    public static TestResponse request(String method, String path, String requestBody, Map<String, String> requestHeaders) {
        try {
            URL url = new URL("http://localhost:8085" + path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestMethod(method);

            if (requestHeaders != null) {
                requestHeaders.entrySet().stream().forEach(c -> connection.setRequestProperty(c.getKey(), c.getValue()));
            }

            String encoding3 = Base64.getEncoder().encodeToString(
                    requestBody.getBytes(StandardCharsets.UTF_8));
            connection.setRequestProperty("body", encoding3);
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            if (requestBody != null) {
                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = requestBody.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }
            }

            connection.connect();
            //String body = connection.getResponseMessage();
            String body = "";
            if (connection.getResponseCode() != 401 || connection.getResponseCode() != 404) {
                body = IOUtils.toString(connection.getInputStream());
            }
            return new TestResponse(connection.getResponseCode(), body);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Sending request failed: " + e.getMessage());
            return null;
        }
    }

    public static class TestResponse {

        public final String body;
        public final int status;

        public TestResponse(int status, String body) {
            this.status = status;
            this.body = body;
        }

    }
}
