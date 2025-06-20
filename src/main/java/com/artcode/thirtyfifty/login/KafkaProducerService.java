package com.artcode.thirtyfifty.login;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

	 private final KafkaTemplate<String, ForgotPasswordEvent> kafkaTemplate;

	    public KafkaProducerService(KafkaTemplate<String, ForgotPasswordEvent> kafkaTemplate) {
	    	super();
	        this.kafkaTemplate = kafkaTemplate;
	    }

	    public void sendForgotPasswordEvent(String topic, ForgotPasswordEvent event) {
	        kafkaTemplate.send(topic, event);
	        System.out.println("Kafka Event Sent: " + event.getEmail());
	    }
}
