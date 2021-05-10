package com.asapp.backend.challenge.utils;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpUriRequestBase;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicHeader;
import spark.utils.StringUtils;

import java.io.IOException;
import java.util.Map;

public class ApiTestUtils {
    public static TestResponse request(String method, String path, String requestBody, Map<String, String> requestHeaders) {

        HttpClient client = HttpClientBuilder.create().build();
        HttpUriRequestBase httpUriRequestBase = null;
        if (method.equals("GET")) {
            httpUriRequestBase = new HttpGet("http://localhost:8085" + path);
        } else if (method.equals("POST")) {
            httpUriRequestBase = new HttpPost("http://localhost:8085" + path);
        }
        if (requestHeaders != null) {
            for (Map.Entry<String, String> c : requestHeaders.entrySet()) {
                httpUriRequestBase.setHeader(new BasicHeader(c.getKey(), c.getValue()));
            }
        }
        if (StringUtils.isNotEmpty(requestBody)) {
            StringEntity requestEntity = new StringEntity(
                    requestBody,
                    ContentType.APPLICATION_JSON);
            httpUriRequestBase.setEntity(requestEntity);
        }
        HttpResponse response = null;

        try {
            response = HttpClientBuilder.create().build().execute(httpUriRequestBase);
        } catch (IOException e) {
            e.printStackTrace();
        }

        TestResponse testResponse = null;
        try {
            testResponse = new TestResponse(response.getCode(), EntityUtils.toString(((CloseableHttpResponse) response).getEntity()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return testResponse;

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
