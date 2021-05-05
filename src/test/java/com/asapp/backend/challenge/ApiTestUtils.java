package com.asapp.backend.challenge;

import spark.utils.IOUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Fail.fail;

public class ApiTestUtils {
    public static TestResponse request(String method, String path, String requestBody) {
        try {
            URL url = new URL("http://localhost:8085" + path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestMethod(method);
            connection.setRequestProperty("body",requestBody);
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            if(requestBody!=null) {
                try(OutputStream os = connection.getOutputStream()) {
                    byte[] input = requestBody.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }
            }

            connection.connect();
            String body = IOUtils.toString(connection.getInputStream());
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

        public Map<String,String> json() {
            //return new Gson().fromJson(body, HashMap.class);
            return new HashMap<>();
        }
    }
}
