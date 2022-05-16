package com.springboot.app.config;

import java.util.Collections;

import org.springframework.context.annotation.Configuration;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;

@Configuration
public class SwaggerConfig {
	private ApiInfo apiInfo() {
		return new ApiInfo(
					"Spring Boot App Rest APIs",
					"Spring Boot App Rest API Documentation using Swagger",
					"1",
					"Terms of Services",
					new Contact("subhankar", "www.psubhankar.com", "subhankar.panigra45@gmail.com")
					"License of API",
					"API License URL",
					Collections.emptyList()
				);
	}
}
