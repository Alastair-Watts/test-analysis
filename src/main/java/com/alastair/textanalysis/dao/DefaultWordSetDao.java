package com.alastair.textanalysis.dao;

import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.alastair.textanalysis.model.ProcessingStatus;
import com.alastair.textanalysis.model.WordSet;

@Repository
public class DefaultWordSetDao implements WordSetDao {

	private MongoTemplate template;

	public DefaultWordSetDao(MongoTemplate template) {
		this.template = template;
	}

	@Override
	public void createWordSet(WordSet wordSet) {
		template.save(wordSet);
	}

	@Override
	public WordSet findUnprocessedMarkProcessed(String documentName) {
		Query query = new Query(Criteria.where("documentName").is(documentName));
		query.addCriteria(Criteria.where("status").is(ProcessingStatus.UNPROCESSED));
		query.limit(1);

		Update update = new Update();
		update.set("status", ProcessingStatus.PROCESSED);
		return template.findAndModify(query, update, FindAndModifyOptions.options().returnNew(true), WordSet.class);
	}
}
