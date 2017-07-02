package com.alastair.textanalysis.dao.it;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alastair.textanalysis.dao.WordSetDao;
import com.alastair.textanalysis.model.WordSet;

public class DefaultWordSetDaoIT extends DaoIT {

	@Autowired
	private WordSetDao wordSetDao;

	@Test
	public void createCanBeRead() {
		String documentName = "Some.Name";
		Integer index = 5;
		String[] words = new String[] { "this", "was", "a", "generic", "sentence" };
		WordSet testWordSet = new WordSet(documentName, Arrays.asList(words), index);
		wordSetDao.createWordSet(testWordSet);
		WordSet actual = wordSetDao.getByIndex(documentName, index);
		assertEquals(documentName, actual.getDocumentName());
		assertEquals(index, actual.getIndex());
		assertArrayEquals(words, actual.getWords().toArray(new String[0]));
	}

	@Test
	public void getsCorrectDocument() {
		String documentName = "Some.Name";
		Integer index = 5;
		String[] words = new String[] { "this", "was", "a", "generic", "sentence" };
		WordSet testWordSet = new WordSet(documentName, Arrays.asList(words), index);

		wordSetDao.createWordSet(testWordSet);
		wordSetDao.createWordSet(new WordSet(documentName, new ArrayList<>(), 12));
		wordSetDao.createWordSet(new WordSet(documentName + "thing", new ArrayList<>(), 5));
		wordSetDao.createWordSet(new WordSet("other.doc", new ArrayList<>(), 5));

		WordSet actual = wordSetDao.getByIndex(documentName, index);
		assertEquals(documentName, actual.getDocumentName());
		assertEquals(index, actual.getIndex());
		assertArrayEquals(words, actual.getWords().toArray(new String[0]));
	}

}
