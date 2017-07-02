package com.alastair.textanalysis.dao;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.alastair.textanalysis.model.Instance;

@Repository
public class DefaultProcessorInstanceDao implements ProcessorInstanceDao {

	private MongoTemplate template;

	public DefaultProcessorInstanceDao(MongoTemplate template) {
		this.template = template;
	}

	public Instance findInstance(String documentName) {
		Query query = new Query(Criteria.where("documentName").is(documentName));
		return template.findOne(query, Instance.class);
	}

	public void createInstance(String documentName) {
		template.save(new Instance(documentName));
	}

}
