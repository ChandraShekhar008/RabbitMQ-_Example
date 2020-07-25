package com.chandra.rabbitmq.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.chandra.rabbitmq.dto.Student;

/**
 * Message Listener for RabbitMQ
 * 
 * Polls the data in the configured queue and process it.
 *
 */

@Service
public class MessageListener {

    private static final Logger log = LoggerFactory.getLogger(MessageListener.class);
    
    @Autowired
    ApplicationConfigReader applicationConfigReader;
    
    @Value("${message.retry.delay}")
    private long MESSAGE_RETRY_DELAY;

    
    /**
     * Message listener
     * @param Student a user defined object used for deserialization of message
     */
    @RabbitListener(queues = "${amqp.queue.name}")
    public void receiveMessage(final Student data) {
    	log.info("Received message: {} from queue.", data);
    	
    	try {
    		log.info("Making REST call to the API");
    		
    		//TODO: Code to make REST call/Process the data
    		
        	log.info("<< Exiting receiveMessage() after API call.");
    	} catch(HttpClientErrorException  ex) {
    		
    		if(ex.getStatusCode() == HttpStatus.NOT_FOUND) {
        		log.info("Delay...");
        		try {
    				Thread.sleep(MESSAGE_RETRY_DELAY);
    			} catch (InterruptedException e) { }
    			
    			log.info("Throwing exception so that message will be requed in the queue.");
    			// Throw application specific error
    			throw new RuntimeException();
    		} else {
    			throw new AmqpRejectAndDontRequeueException(ex); 
    		}
    		
    	} catch(Exception e) {
    		log.error("Internal server error occurred in API call. Bypassing message requeue {}", e);
    		throw new AmqpRejectAndDontRequeueException(e); 
    	}

    }

}
