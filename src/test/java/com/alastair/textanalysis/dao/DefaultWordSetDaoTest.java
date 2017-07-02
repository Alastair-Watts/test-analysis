package com.alastair.textanalysis.dao;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

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
		WordSet wordSet = new WordSet(documentName, new LinkedList<>(), 5);
		wordSetDao.createWordSet(wordSet);
		Mockito.verify(template).save(wordSet);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void getByIndex_SearchesCorrectly() {
		String documentName = "a-document";
		int index = 5;
		WordSet wordSet = new WordSet(documentName, new LinkedList<>(), index);
		Mockito.when(template.findOne(Mockito.any(Query.class), Mockito.any(Class.class))).thenReturn(wordSet);

		wordSetDao.getByIndex(documentName, index);

		ArgumentCaptor<Query> query = ArgumentCaptor.forClass(Query.class);
		ArgumentCaptor<Class> clazz = ArgumentCaptor.forClass(Class.class);
		Mockito.verify(template).findOne(query.capture(), clazz.capture());

		assertEquals(WordSet.class, clazz.getValue());

		DBObject actualQuery = query.getValue().getQueryObject();
		assertEquals(2, actualQuery.keySet().size());
		assertEquals(documentName, actualQuery.get("documentName"));
		assertEquals(index, actualQuery.get("index"));
	}
}
