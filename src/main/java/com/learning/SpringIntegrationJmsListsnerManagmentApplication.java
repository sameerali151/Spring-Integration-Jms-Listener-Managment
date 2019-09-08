package com.learning;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.naming.NamingException;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.jms.connection.CachingConnectionFactory;

@SpringBootApplication
@EnableIntegration
@ImportResource({"classpath:integration.xml"})
public class SpringIntegrationJmsListsnerManagmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringIntegrationJmsListsnerManagmentApplication.class, args);
	}

    public static final String IN_QUEUE = "in.queue";
    public static final String OUT_QUEUE = "out.queue";
    
    @Bean
    public Queue inJMSQ() {
        return new ActiveMQQueue(IN_QUEUE);
    }
    
    @Bean
    public Queue outJMSQ() {
        return new ActiveMQQueue(OUT_QUEUE);
    }
    
    @Bean(name = "jmsConnectionFactory")
	public ConnectionFactory getCachingConnectionFactory()	throws IllegalArgumentException, NamingException, JMSException {
		CachingConnectionFactory connFactory = new CachingConnectionFactory();
		connFactory.setTargetConnectionFactory(getConnectionFactory());
		connFactory.setSessionCacheSize(10);
		return connFactory;
	}

	@Bean
	public ConnectionFactory getConnectionFactory() throws NumberFormatException, JMSException {
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		return factory;
	}
    
}
