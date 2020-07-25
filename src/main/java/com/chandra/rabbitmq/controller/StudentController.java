package com.chandra.rabbitmq.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chandra.rabbitmq.config.ApplicationConfigReader;
import com.chandra.rabbitmq.config.MessageSender;
import com.chandra.rabbitmq.dto.Student;
import com.chandra.rabbitmq.util.ApplicationConstant;


@RestController
@RequestMapping(path = "/students")
public class StudentController {

	private static final Logger log = LoggerFactory.getLogger(StudentController.class);

	private final RabbitTemplate rabbitTemplate;
	private ApplicationConfigReader applicationConfig;
	private MessageSender messageSender;

	public ApplicationConfigReader getApplicationConfig() {
		return applicationConfig;
	}

	@Autowired
	public void setApplicationConfig(ApplicationConfigReader applicationConfig) {
		this.applicationConfig = applicationConfig;
	}

	@Autowired
	public StudentController(final RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	public MessageSender getMessageSender() {
		return messageSender;
	}

	@Autowired
	public void setMessageSender(MessageSender messageSender) {
		this.messageSender = messageSender;
	}

	@RequestMapping(path = "/health", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> checkHealth() {
		
		return new ResponseEntity<String>("OK", HttpStatus.OK);
	}

	@RequestMapping(path = "/sendMessage", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> sendMessage(@RequestBody Student student) {

		String exchange = getApplicationConfig().getExchangeName();
		String routingKey = getApplicationConfig().getRoutingKey();
		
		/* Sending to Message Queue */
		try {
			messageSender.sendMessage(rabbitTemplate, exchange, routingKey, student);
			return new ResponseEntity<String>(ApplicationConstant.IN_QUEUE, HttpStatus.OK);
			
		} catch (Exception ex) {
			log.error("Exception occurred while sending message to the queue. Exception= {}", ex);
			return new ResponseEntity<String>(ApplicationConstant.MESSAGE_QUEUE_SEND_ERROR,	HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}


	
}
