package com.alastair.textanalysis.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.alastair.textanalysis.model.WordUsage;
import com.mongodb.DBObject;

@RunWith(MockitoJUnitRunner.class)
public class DefaultWordUseDaoTest {

	@Mock
	private MongoTemplate template;

	@InjectMocks
	private DefaultWordUseDao wordUseDao;

	@SuppressWarnings("rawtypes")
	@Test
	public void registerUse_UpsertsUse() {
		String word = "Smiths";
		String documentName = "Documented";
		wordUseDao.registerUse(word, documentName);
		ArgumentCaptor<Query> query = ArgumentCaptor.forClass(Query.class);
		ArgumentCaptor<Update> update = ArgumentCaptor.forClass(Update.class);
		ArgumentCaptor<Class> entityClass = ArgumentCaptor.forClass(Class.class);
		Mockito.verify(template).upsert(query.capture(), update.capture(), entityClass.capture());

		DBObject queryObject = query.getValue().getQueryObject();

		assertEquals(2, queryObject.keySet().size());
		assertEquals(word, queryObject.get("word"));
		assertEquals(documentName, queryObject.get("documentName"));

		DBObject updateObject = update.getValue().getUpdateObject();

		assertEquals(1, updateObject.keySet().size());
		DBObject increment = (DBObject) updateObject.get("$inc");
		assertEquals(1, increment.keySet().size());
		assertEquals(1, increment.get("count"));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void getUsage_FindsOne() {
		String word = "FleetFoxes";
		String documentName = "Undocumented";
		WordUsage expectedUsage = new WordUsage(documentName, word);
		Mockito.when(template.findOne(Mockito.any(Query.class), Mockito.any(Class.class))).thenReturn(expectedUsage);

		assertEquals(expectedUsage, wordUseDao.getUsage(word, documentName));

		ArgumentCaptor<Query> query = ArgumentCaptor.forClass(Query.class);
		ArgumentCaptor<Class> entityClass = ArgumentCaptor.forClass(Class.class);
		Mockito.verify(template).findOne(query.capture(), entityClass.capture());

		DBObject queryObject = query.getValue().getQueryObject();

		assertEquals(2, queryObject.keySet().size());
		assertEquals(word, queryObject.get("word"));
		assertEquals(documentName, queryObject.get("documentName"));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getUsage_NoUsage_ReturnsEmptyUsage() {
		String word = "MotionCitySoundtrack";
		String documentName = "d.io";
		Mockito.when(template.findOne(Mockito.any(Query.class), Mockito.any(Class.class))).thenReturn(null);

		WordUsage usage = wordUseDao.getUsage(word, documentName);
		assertEquals(word, usage.getWord());
		assertEquals(documentName, usage.getDocumentName());
		assertEquals(0, usage.getCount().intValue());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void getMostUsed_FiltersOnDocumentName() {
		String word = "Gotye";
		String documentName = "d.io";
		WordUsage expectedUsage = new WordUsage(documentName, word);
		Mockito.when(template.findOne(Mockito.any(Query.class), Mockito.any(Class.class))).thenReturn(expectedUsage);

		WordUsage usage = wordUseDao.getMostUsed(documentName);
		assertEquals(expectedUsage, usage);

		ArgumentCaptor<Query> query = ArgumentCaptor.forClass(Query.class);
		ArgumentCaptor<Class> entityClass = ArgumentCaptor.forClass(Class.class);
		Mockito.verify(template).findOne(query.capture(), entityClass.capture());

		DBObject queryObject = query.getValue().getQueryObject();

		assertEquals(1, queryObject.keySet().size());
		assertEquals(documentName, queryObject.get("documentName"));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void getLeastUsed_FiltersOnDocumentName() {
		String word = "Panic!AtTheDisco";
		String documentName = "d.io";
		WordUsage expectedUsage = new WordUsage(documentName, word);
		Mockito.when(template.findOne(Mockito.any(Query.class), Mockito.any(Class.class))).thenReturn(expectedUsage);

		WordUsage usage = wordUseDao.getLeastUsed(documentName);
		assertEquals(expectedUsage, usage);

		ArgumentCaptor<Query> query = ArgumentCaptor.forClass(Query.class);
		ArgumentCaptor<Class> entityClass = ArgumentCaptor.forClass(Class.class);
		Mockito.verify(template).findOne(query.capture(), entityClass.capture());

		DBObject queryObject = query.getValue().getQueryObject();

		assertEquals(1, queryObject.keySet().size());
		assertEquals(documentName, queryObject.get("documentName"));
	}
}
