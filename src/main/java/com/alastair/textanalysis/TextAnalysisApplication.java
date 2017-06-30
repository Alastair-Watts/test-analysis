package com.alastair.textanalysis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.ContextConfiguration;

@SpringBootApplication
@ContextConfiguration(classes = TextAnalysisConfig.class)
public class TextAnalysisApplication {

	public static void main(String[] args) {
		SpringApplication.run(TextAnalysisApplication.class, args);
	}
}
