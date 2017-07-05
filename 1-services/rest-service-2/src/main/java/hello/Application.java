package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.messaging.MessageChannel;

@SpringBootApplication

@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableAsync
@IntegrationComponentScan
public class Application implements CommandLineRunner{
	
	private static final Logger log = LoggerFactory.getLogger(Application.class);
	
	@Autowired
	private ProductRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {

		repository.deleteAll();

		// save a couple of Products
		repository.save(new Product("p1", 6.0));
		repository.save(new Product("p2", 3.6));
		
		// fetch all Products
		log.info("Products found with findAll():");
		log.info("-------------------------------");
		for (Product p : repository.findAll()) {
			log.info(p.toString());
		}

	}
}
