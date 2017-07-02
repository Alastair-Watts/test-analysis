package com.alastair.textanalysis.dao;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

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
	public WordSet getByIndex(String documentName, Integer index) {
		Query query = new Query(Criteria.where("documentName").is(documentName));
		query.addCriteria(Criteria.where("index").is(index));
		return template.findOne(query, WordSet.class);
	}
}
