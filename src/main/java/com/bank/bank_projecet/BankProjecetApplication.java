package com.bank.bank_projecet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@EnableScheduling
@OpenAPIDefinition(
	info = @Info(
		title = "Bank System App",
		description = "Backend Rest Api's for Bank",
		version = "V1.0",
		contact = @Contact(
			name = "Ahmed AbdelMoniem",
			email = "ahmedabdelminim@gmail.com",
			url = "https://github.com/AhmedAbdElminim/Bank-System"
		),
		license = @License(
			name = "Ahmed AbdelMoniem",
			url = "https://github.com/AhmedAbdElminim/Bank-System"
		)
	),
	externalDocs=@ExternalDocumentation(
		description = "The Java Bank App Documentation",
		url = "https://github.com/AhmedAbdElminim/Bank-System"
	)
	
)
public class BankProjecetApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankProjecetApplication.class, args);
	}

}
