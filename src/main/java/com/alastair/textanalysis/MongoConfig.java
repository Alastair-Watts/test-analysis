package com.alastair.textanalysis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

@Configuration
public class MongoConfig extends AbstractMongoConfiguration {

	@Autowired
	private String mongoLocation;

	@Override
	protected String getDatabaseName() {
		return "db";
	}

	@Override
	public Mongo mongo() throws Exception {
		return new MongoClient(mongoLocation);
	}

}
