package com.alastair.textanalysis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import com.alastair.textanalysis.utils.Constants;

@SpringBootApplication
@Import(TextAnalysisConfig.class)
public class TextAnalysisApplication {

	private static final String DEFAULT_DOCUMENT_NAME = "dump.xml";

	public static void main(String[] args) {
		documentName = getInputProperty(args, Constants.FILE_LOCATION_PROPERTY);
		SpringApplication.run(TextAnalysisApplication.class, args);
	}

	private static String documentName;

	@Bean
	public String documentName() {
		return documentName != null ? documentName : DEFAULT_DOCUMENT_NAME;
	}

	private static String getInputProperty(String[] args, String propertyKey) {
		for (int i = 0; i < args.length; i++) {
			if (propertyKey.equalsIgnoreCase(args[i])) {
				return args[i + 1];
			}
		}
		throw new IllegalArgumentException("No source file provided");
	}
}
