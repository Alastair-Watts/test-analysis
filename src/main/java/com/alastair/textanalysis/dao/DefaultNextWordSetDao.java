package com.alastair.textanalysis.dao;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;

import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.alastair.textanalysis.model.NextWordSet;

@Repository
public class DefaultNextWordSetDao implements NextWordSetDao {

	private MongoTemplate template;

	public DefaultNextWordSetDao(MongoTemplate template) {
		this.template = template;
	}

	public NextWordSet findAndIncrement(String documentName) {
		Query query = new Query(Criteria.where("documentName").is(documentName));
		Update update = new Update().inc("index", 1);

		NextWordSet nextWordSet = template.findAndModify(query, update, options().returnNew(true), NextWordSet.class);
		return nextWordSet != null ? nextWordSet : initialisWordSet(documentName);
	}

	private NextWordSet initialisWordSet(String documentName) {
		NextWordSet nextWordSet = new NextWordSet(documentName, 0);
		template.save(nextWordSet);
		return nextWordSet;
	}

}
