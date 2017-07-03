package com.alastair.textanalysis.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.alastair.textanalysis.dao.WordUseDao;
import com.alastair.textanalysis.model.WordUsage;

@RunWith(MockitoJUnitRunner.class)
public class ResultsControllerTest {

	@Mock
	private WordUseDao wordUseDao;
	private String documentName = "SomeDocument";
	private ResultsController resultsController;

	@Before
	public void setup() {
		resultsController = new ResultsController(wordUseDao, documentName);
	}

	@Test
	public void mostUsedWord_CallsDao() {
		WordUsage value = new WordUsage(documentName, "WORD");
		when(wordUseDao.getMostUsed(documentName)).thenReturn(value);
		assertEquals(value, resultsController.mostUsedWord());
	}

	@Test
	public void leastUsedWord_CallsDao() {
		WordUsage value = new WordUsage(documentName, "WORD");
		when(wordUseDao.getLeastUsed(documentName)).thenReturn(value);
		assertEquals(value, resultsController.leastUsedWord());
	}

}
