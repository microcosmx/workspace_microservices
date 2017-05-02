package com.trainticket.verificationcode;

import java.util.Arrays;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;


@SpringBootApplication
@EnableDiscoveryClient

public class VerificationCodeApplication{

	public static void main(String[] args) {
		SpringApplication.run(VerificationCodeApplication.class, args);
	}

	
}
