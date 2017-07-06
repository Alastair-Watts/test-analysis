package com.alastair.textanalysis.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.alastair.textanalysis.dao.WordSetDao;
import com.alastair.textanalysis.dao.WordUseDao;
import com.alastair.textanalysis.model.WordSet;

@RunWith(MockitoJUnitRunner.class)
public class DefaultDocumentAnalysingServiceTest {

	private String documentName = "some.thing";

	@Mock
	private WordSetDao wordSetDao;

	@Mock
	private WordUseDao wordUseDao;

	private DefaultDocumentAnalysingService documentAnalysingService;

	@Before
	public void setup() {
		documentAnalysingService = new DefaultDocumentAnalysingService(wordSetDao, wordUseDao, documentName);
	}

	@Test
	public void analyse_GetsNextSetOfWordsAndRegistersEach() {

		List<String> words = new ArrayList<>();
		words.add("FirstWord");
		words.add("SecondWord");
		words.add("ThirdWord");
		words.add("FourthWord");
		words.add("FirstWord");

		Mockito.when(wordSetDao.findUnprocessedMarkProcessed(documentName)).thenReturn(new WordSet(documentName, words))
				.thenReturn(null);

		documentAnalysingService.analyse();

		InOrder inOrder = Mockito.inOrder(wordUseDao);

		inOrder.verify(wordUseDao).registerUse(words.get(0), documentName);
		inOrder.verify(wordUseDao).registerUse(words.get(1), documentName);
		inOrder.verify(wordUseDao).registerUse(words.get(2), documentName);
		inOrder.verify(wordUseDao).registerUse(words.get(3), documentName);
		inOrder.verify(wordUseDao).registerUse(words.get(4), documentName);
	}
}
