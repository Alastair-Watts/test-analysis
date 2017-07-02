package com.alastair.textanalysis.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.alastair.textanalysis.model.NextWordSet;

@RunWith(MockitoJUnitRunner.class)
public class DefaultNextWordSetDaoTest {

	@Mock
	private MongoTemplate template;

	@InjectMocks
	private DefaultNextWordSetDao nextWordSetDao;

	@SuppressWarnings("unchecked")
	@Test
	public void findAndIncrement_NoRecord_CreatesOne() {
		Mockito.when(template.findAndModify(Mockito.any(Query.class), Mockito.any(Update.class),
				Mockito.any(FindAndModifyOptions.class), Mockito.any(Class.class))).thenReturn(null);
		String documentName = "work.bat";
		NextWordSet nextWordSet = nextWordSetDao.findAndIncrement(documentName);

		Mockito.verify(template).save(nextWordSet);

		assertEquals(documentName, nextWordSet.getDocumentName());
		assertEquals(0, nextWordSet.getIndex().intValue());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void findAndIncrement_RecordExists_ReturnsRecord() {
		String documentName = "work.bat";
		NextWordSet nextWordSet = new NextWordSet(documentName, 12);
		Mockito.when(template.findAndModify(Mockito.any(Query.class), Mockito.any(Update.class),
				Mockito.any(FindAndModifyOptions.class), Mockito.any(Class.class))).thenReturn(nextWordSet);
		assertEquals(nextWordSet, nextWordSetDao.findAndIncrement(documentName));
	}
}
