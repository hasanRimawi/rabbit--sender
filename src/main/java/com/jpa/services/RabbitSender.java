package com.jpa.services;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitSender {
//	final static String  queueName = "spring-boot";
	final static String topicExchangeName = "spring-boot-exchange";
	@Autowired
	private AmqpTemplate rabbitTemplate;

	public void send(String routingKey, String whatever) {
		rabbitTemplate.convertAndSend(topicExchangeName, routingKey, "I'm the transported message: " + whatever);
	}
}
