package com.alastair.textanalysis.dao.it;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

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
		WordSet testWordSet = new WordSet(documentName, new ArrayList<>(), 5);
		wordSetDao.createWordSet(testWordSet);
		assertEquals(testWordSet, wordSetDao.getByIndex(documentName, 5));
	}
	
	@Test
	public void getsCorrectDocument() {
		String documentName = "Some.Name";
		WordSet testWordSet = new WordSet(documentName, new ArrayList<>(), 5);
		wordSetDao.createWordSet(testWordSet);
		wordSetDao.createWordSet(new WordSet(documentName, new ArrayList<>(), 12));
		wordSetDao.createWordSet(new WordSet(documentName + "thing", new ArrayList<>(), 5));
		wordSetDao.createWordSet(new WordSet("other.doc", new ArrayList<>(), 5));
		assertEquals(testWordSet, wordSetDao.getByIndex(documentName, 5));
	}

}
