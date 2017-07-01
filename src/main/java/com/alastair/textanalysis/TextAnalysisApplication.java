package com.alastair.textanalysis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(TextAnalysisConfig.class)
public class TextAnalysisApplication {

	public static void main(String[] args) {
		SpringApplication.run(TextAnalysisApplication.class, args);
	}
}
