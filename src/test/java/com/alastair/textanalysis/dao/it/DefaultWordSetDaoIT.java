package com.alastair.textanalysis.dao.it;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alastair.textanalysis.dao.WordSetDao;
import com.alastair.textanalysis.model.ProcessingStatus;
import com.alastair.textanalysis.model.WordSet;

public class DefaultWordSetDaoIT extends DaoIT {

	@Autowired
	private WordSetDao wordSetDao;

	@Test
	public void createCanBeRead() {
		String documentName = "Some.Name";
		String[] words = new String[] { "this", "was", "a", "generic", "sentence" };
		WordSet testWordSet = new WordSet(documentName, Arrays.asList(words));
		wordSetDao.createWordSet(testWordSet);
		WordSet actual = wordSetDao.findUnprocessedMarkProcessed(documentName);
		assertEquals(documentName, actual.getDocumentName());
		assertEquals(ProcessingStatus.PROCESSED, actual.getStatus());
		assertArrayEquals(words, actual.getWords().toArray(new String[0]));
	}

	@Test
	public void getsCorrectDocument() {
		String documentName = "Some.Name";
		String[] words = new String[] { "this", "was", "a", "generic", "sentence" };
		WordSet testWordSet = new WordSet(documentName, Arrays.asList(words));

		wordSetDao.createWordSet(testWordSet);
		wordSetDao.createWordSet(new WordSet(documentName, new ArrayList<>()));
		wordSetDao.createWordSet(new WordSet(documentName + "thing", new ArrayList<>()));
		wordSetDao.createWordSet(new WordSet("other.doc", new ArrayList<>()));

		WordSet actual = wordSetDao.findUnprocessedMarkProcessed(documentName);
		assertEquals(documentName, actual.getDocumentName());
		assertArrayEquals(words, actual.getWords().toArray(new String[0]));
		
		assertNotNull(wordSetDao.findUnprocessedMarkProcessed(documentName));
		assertNull(wordSetDao.findUnprocessedMarkProcessed(documentName));
	}

}
