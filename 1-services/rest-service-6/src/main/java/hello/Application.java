package hello;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;

import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.integration.annotation.IntegrationComponentScan;

@SpringBootApplication

@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableAsync
@IntegrationComponentScan
public class Application {
	
	final static String queueName = "rest-service-queue-1";

    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange("rest-service-exchange-1");
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(queueName);
    }
    
    @Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		RabbitTemplate template = new RabbitTemplate(connectionFactory);
		template.setRoutingKey(queueName);
		return template;
	}
    
    
    
    @Bean
	public ScheduledProducer scheduledProducer() {
		return new ScheduledProducer();
	}

	@Bean
	public BeanPostProcessor postProcessor() {
		return new ScheduledAnnotationBeanPostProcessor();
	}
	
	static class ScheduledProducer {

		@Autowired
		private volatile RabbitTemplate rabbitTemplate;

		private final AtomicInteger counter = new AtomicInteger();

//		@Scheduled(fixedRate = 3000)
//		public void sendMessage() {
//			rabbitTemplate.convertAndSend("Hello World " + counter.incrementAndGet());
//		}
		
		public void sendMessage(String msg) {
			rabbitTemplate.convertAndSend(Application.queueName, msg + ". " + counter.incrementAndGet());
		}
	}
    
    

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String args[]) {
		SpringApplication.run(Application.class);
	}
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
}