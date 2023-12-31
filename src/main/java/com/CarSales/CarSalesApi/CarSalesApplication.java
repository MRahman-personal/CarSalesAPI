package com.CarSales.CarSalesApi;

import com.CarSales.CarSalesApi.Authentication.AuthenticationFilter;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.integration.outbound.PubSubMessageHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageHandler;

@SpringBootApplication
public class CarSalesApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarSalesApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean<AuthenticationFilter> authenticationFilterConfig(){
		FilterRegistrationBean<AuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new AuthenticationFilter());
		registrationBean.addUrlPatterns("/buyer/*");
		registrationBean.addUrlPatterns("/seller/*");
		registrationBean.addUrlPatterns("/admin/*");

		return registrationBean;
	}

	@Bean
	@ServiceActivator(inputChannel = "pubsubOutputChannel")
	public MessageHandler messageSender(PubSubTemplate pubsubTemplate) {
		return new PubSubMessageHandler(pubsubTemplate, "sale-confirmed");
	}
	@MessagingGateway(defaultRequestChannel = "pubsubOutputChannel")
	public interface PubsubOutboundGateway {
		void sendToPubsub(String text);
	}
}
