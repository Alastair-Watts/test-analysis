package com.alastair.textanalysis.dao;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.alastair.textanalysis.model.WordUsage;

@Repository
public class DefaultWordUseDao implements WordUseDao {

	private static final Direction MAX = Sort.Direction.DESC;
	private static final Direction MIN = Sort.Direction.ASC;
	private MongoTemplate template;

	public DefaultWordUseDao(MongoTemplate template) {
		this.template = template;
	}

	@Override
	public void registerUse(String word, String document) {
		Query query = withWordAndDocumentName(word, document);
		Update update = new Update();
		update.inc("count", 1);
		template.upsert(query, update, WordUsage.class);
	}

	@Override
	public WordUsage getUsage(String word, String document) {
		Query query = withWordAndDocumentName(word, document);
		WordUsage usage = wordUsage(query);
		return usage != null ? usage : new WordUsage(document, word);
	}

	@Override
	public List<WordUsage> getMostUsed(String document) {
		WordUsage wordUsage = wordUsage(document, MAX);
		return getByCount(wordUsage.getCount(), document);
	}

	@Override
	public List<WordUsage> getLeastUsed(String document) {
		WordUsage wordUsage = wordUsage(document, MIN);
		return getByCount(wordUsage.getCount(), document);
	}

	private List<WordUsage> getByCount(Long count, String document) {
		Query query = withDocumentName(document);
		query.addCriteria(Criteria.where("count").is(count));
		return template.find(query, WordUsage.class);
	}

	private WordUsage wordUsage(String document, Direction direction) {
		Query query = withDocumentName(document);
		query.with(new Sort(direction, "count"));
		return wordUsage(query);
	}

	private Query withDocumentName(String document) {
		Query query = new Query();
		query.addCriteria(Criteria.where("documentName").is(document));
		return query;
	}

	private Query withWordAndDocumentName(String word, String document) {
		Query query = withDocumentName(document);
		query.addCriteria(Criteria.where("word").is(word));
		return query;
	}

	private WordUsage wordUsage(Query query) {
		return template.findOne(query, WordUsage.class);
	}
}
