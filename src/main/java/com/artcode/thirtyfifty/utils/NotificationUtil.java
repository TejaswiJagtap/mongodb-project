package com.artcode.thirtyfifty.utils;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

public class NotificationUtil {

	private static final Logger log = LoggerFactory.getLogger(NotificationUtil.class);

	final static String FIREBASE_SERVER_KEY = "AAAAAiXeeA0:APA91bHGLsQ1rzO1EmfaxdBcers7I9qx6VxsD7otu67Gv1WNr4U74fqAK80fWOjUZ2eCK1V5hPk-TMCyfJ7rsxPvz9LQO7wxElYxLcq0eP6vO5VFaOj8SjeprGewwZ3sxxPf3e8V87mz";
	final static String FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send";

	public static void sendNotification(String title, String body, String firebase, String imageUrl)
			throws JSONException, IOException {

		JSONObject data = new JSONObject();
		JSONObject message = new JSONObject();
		JSONObject json = new JSONObject();

		try {
			message.put("title", title);
			message.put("body", body);
			message.put("color", "#53c4bc");
			message.put("clickAction", "notification_click");

			if (imageUrl != null) {
				message.put("image", imageUrl);

			}

			data.put("title", title);
			data.put("body", body);

			json.put("data", data);
			json.put("notification", message);
			json.put("to", firebase);

			//// checking the payload json size .
			String payload = json.toString();
			int payloadLength = payload.length();
			System.out.println(payloadLength + " bytes");

			try {
				if (firebase != null && !firebase.isEmpty()) {
					String response = callToFcmServer(json);
					System.out.println(json);
					log.info("notification response: {}", response);
				}
			} catch (Exception e) {
				log.error("send Notification : " + e.getMessage());
				e.printStackTrace();
			}

		} catch (Exception e) {
			log.error("executor service error : " + e.getMessage());
			e.printStackTrace();
		}
	}

	public static String callToFcmServer(JSONObject message) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.set("Authorization", "key=" + FIREBASE_SERVER_KEY);
			httpHeaders.set("Content-Type", "application/json");
			HttpEntity<String> httpEntity = new HttpEntity<>(message.toString(), httpHeaders);
			return restTemplate.postForObject(FIREBASE_API_URL, httpEntity, String.class);
		} catch (Exception e) {
			log.error("callToFcmServer error : " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
}
