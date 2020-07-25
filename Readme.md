# Getting Started

Below system config is required to run this project:

1). Java 8 and above.

2). Maven.

3). Erlang should be installed
	https://www.erlang.org/downloads
	
4). RabbitMQ server should be installed on your system.
	https://www.rabbitmq.com/install-windows.html
	
	After step 3 and 4, run below commands in command window to start RabbitMQ server.
	
	> Navigate to "sbin" folder in RabbitMQ server folder created in "Program Files".
	> rabbitmq-plugins enable rabbitmq_management
	
Then go to http://localhost:15672/ and login with below credentials:
Default username: guest
Default password: guest

Note:
<Hostname> in properties file is the system IP where RabbitMQ is installed.
Default <Port Number> is 5672 for RabbitMQ.

For Navigation, Exchange info and Queue creation look for <RabbitMq_Server_and_Queue_Setup.docx> in resource folder.

Considering you deploy the application on your local machine.
Below are the two end points:

Note: Change <hostname> and <portnumber> as per your system configuration.

1). Health check
	http://localhost:8080/students/health

2). Send message to the queue
	http://localhost:8080/students/sendMessage
	
	example payload:
	{
		"rollNo": "11",
		"studentName": "Sachin",
		"section": "C"
	}
	
Important Note:

In any failure scenario, you can throw any runtime exception. This will queue the message again.
In case, when you don't want the message to be queued again, you can throw AmqpRejectAndDontRequeueException(ex);


##############################################################################3