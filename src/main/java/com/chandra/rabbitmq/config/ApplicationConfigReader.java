package com.chandra.rabbitmq.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class ApplicationConfigReader {
	
	@Value("${amqp.exchange.name}")
	private String exchangeName;
	
	@Value("${amqp.queue.name}")
	private String queueName;
	
	@Value("${amqp.routing.key}")
	private String routingKey;

	public String getExchangeName() {
		return exchangeName;
	}

	public String getQueueName() {
		return queueName;
	}

	public String getRoutingKey() {
		return routingKey;
	}
	
	
}
