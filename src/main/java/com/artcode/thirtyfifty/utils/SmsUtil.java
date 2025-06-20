package com.artcode.thirtyfifty.utils;

import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Slf4j
public class SmsUtil {

    private static final String APPLICATION_JSON = "application/json"; // Define constant

    static String URL;
    static String API_KEY;
    static String SENDER_ID;

    public SmsUtil(String url, String apiKey, String senderId) {
        SmsUtil.URL = url;
        SmsUtil.API_KEY = apiKey;
        SmsUtil.SENDER_ID = senderId;
    }

    @SuppressWarnings("deprecation")
    public static void sendSms(String templateId, String mobile, String message) {
        if (templateId == null || mobile == null || message == null) {
            throw new IllegalArgumentException("Arguments cannot be null.");
        }
        try {
            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse(APPLICATION_JSON);
            String requestBodyJson = "{\"template_id\":\"" + templateId + "\",\"sender\":\"" + SENDER_ID + "\",\"short_url\":\"0\",\"mobiles\":\"91" + mobile + "\",\"var\":\"" + message + "\"}";
            RequestBody body = RequestBody.create(mediaType, requestBodyJson);
            Request request = new Request.Builder()
                .url(URL)
                .post(body)
                .addHeader("accept", APPLICATION_JSON)
                .addHeader("content-type", APPLICATION_JSON)
                .addHeader("Authkey", API_KEY) // Pass the API key here
                .build();

            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new RuntimeException("Request failed with code " + response.code() + ": " + response.body().string());
            }
            log.info(response.message() + " success status : " + response.isSuccessful());
            response.close();
        } catch (Exception apiException) {
            throw new RuntimeException("Request failed: " + apiException.getMessage(), apiException);
        }
    }
}
