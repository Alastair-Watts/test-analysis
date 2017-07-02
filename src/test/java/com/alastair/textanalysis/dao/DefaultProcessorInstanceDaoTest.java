package com.alastair.textanalysis.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import com.alastair.textanalysis.model.Instance;

@RunWith(MockitoJUnitRunner.class)
public class DefaultProcessorInstanceDaoTest {

	@Mock
	private MongoTemplate mongoTemplate;

	@InjectMocks
	private DefaultProcessorInstanceDao processorInstanceDao;

	@Test
	public void createInstance_CallsSave() {
		String documentName = "a.document";
		processorInstanceDao.createInstance(documentName);
		ArgumentCaptor<Instance> instance = ArgumentCaptor.forClass(Instance.class);
		Mockito.verify(mongoTemplate, Mockito.times(1)).save(instance.capture());
		assertEquals(documentName, instance.getValue().getDocumentName());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void findInstance_CallsFind() {
		String documentName = "a.document";
		processorInstanceDao.findInstance(documentName);
		ArgumentCaptor<Query> query = ArgumentCaptor.forClass(Query.class);
		@SuppressWarnings("rawtypes")
		ArgumentCaptor<Class> clazz = ArgumentCaptor.forClass(Class.class);
		Mockito.verify(mongoTemplate, Mockito.times(1)).findOne(query.capture(), clazz.capture());
		assertEquals(1, query.getValue().getQueryObject().keySet().size());
		assertTrue(query.getValue().getQueryObject().containsField("documentName"));
		assertEquals(documentName, query.getValue().getQueryObject().get("documentName"));
		assertEquals(Instance.class, clazz.getValue());
	}
}
