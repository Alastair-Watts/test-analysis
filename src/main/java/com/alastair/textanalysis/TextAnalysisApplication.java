package com.alastair.textanalysis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import com.alastair.textanalysis.utils.Constants;

@SpringBootApplication
@Import({ TextAnalysisConfig.class, MongoConfig.class })
public class TextAnalysisApplication {

	private static final String DEFAULT_DOCUMENT_NAME = "dump.xml";
	private static final String MONGO_LOCATION = "localhost:27017";
	private static final String SERVER_ID = "a-server";

	public static void main(String[] args) {
		documentName = getInputProperty(args, Constants.FILE_LOCATION_PROPERTY);
		mongoLocation = getInputProperty(args, Constants.DATABASE_LOCATION_PROPERTY);
		serverId = getInputProperty(args, Constants.SERVER_ID_PROPERTY);
		SpringApplication.run(TextAnalysisApplication.class, args);
	}

	private static String documentName;
	private static String mongoLocation;
	private static String serverId;

	@Bean
	public String documentName() {
		return documentName != null ? documentName : DEFAULT_DOCUMENT_NAME;
	}

	@Bean
	public String mongoLocation() {
		return mongoLocation != null ? mongoLocation : MONGO_LOCATION;
	}

	@Bean
	public String serverId() {
		return serverId != null ? serverId : SERVER_ID;
	}

	private static String getInputProperty(String[] args, String propertyKey) {
		for (int i = 0; i < args.length; i++) {
			if (propertyKey.equalsIgnoreCase(args[i])) {
				return args[i + 1];
			}
		}
		return null;
	}
}
