package com.alastair.textanalysis.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.alastair.textanalysis.model.ProcessingStatus;
import com.alastair.textanalysis.model.WordSet;
import com.mongodb.DBObject;

@RunWith(MockitoJUnitRunner.class)
public class DefaultWordSetDaoTest {

	@Mock
	private MongoTemplate template;

	@InjectMocks
	private DefaultWordSetDao wordSetDao;

	@Test
	public void createWordSet_CallsSave() {
		String documentName = "a-document";
		WordSet wordSet = new WordSet(documentName, new LinkedList<>());
		wordSetDao.createWordSet(wordSet);
		Mockito.verify(template).save(wordSet);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void findUnprocessedMarkProcessed_SearchesCorrectly() {
		String documentName = "a-document";
		WordSet wordSet = new WordSet(documentName, new LinkedList<>());
		Mockito.when(
				template.findAndModify(Mockito.any(Query.class), Mockito.any(Update.class), Mockito.any(Class.class)))
				.thenReturn(wordSet);

		wordSetDao.findUnprocessedMarkProcessed(documentName);

		ArgumentCaptor<Query> query = ArgumentCaptor.forClass(Query.class);
		ArgumentCaptor<Update> update = ArgumentCaptor.forClass(Update.class);
		ArgumentCaptor<Class> clazz = ArgumentCaptor.forClass(Class.class);
		ArgumentCaptor<FindAndModifyOptions> options = ArgumentCaptor.forClass(FindAndModifyOptions.class);

		Mockito.verify(template).findAndModify(query.capture(), update.capture(), options.capture(), clazz.capture());

		assertEquals(WordSet.class, clazz.getValue());

		DBObject actualQuery = query.getValue().getQueryObject();
		assertEquals(2, actualQuery.keySet().size());
		assertEquals(documentName, actualQuery.get("documentName"));
		assertEquals(ProcessingStatus.UNPROCESSED, actualQuery.get("status"));

		DBObject actualUpdate = update.getValue().getUpdateObject();
		assertEquals(1, actualUpdate.keySet().size());
		assertTrue(actualUpdate.containsField("$set"));
	}
}
