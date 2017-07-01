package com.alastair.textanalysis.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.alastair.textanalysis.dao.NextWordSetDao;
import com.alastair.textanalysis.dao.WordSetDao;
import com.alastair.textanalysis.dao.WordUseDao;
import com.alastair.textanalysis.model.NextWordSet;
import com.alastair.textanalysis.model.WordSet;

@RunWith(MockitoJUnitRunner.class)
public class DocumentAnalysingServiceTest {

	@Mock
	private NextWordSetDao nextWordSetDao;

	@Mock
	private WordSetDao wordSetDao;

	@Mock
	private WordUseDao wordUseDao;

	@InjectMocks
	private DefaultDocumentAnalysingService documentAnalysingService;

	@Test
	public void analyseDocument_GetsNextSetOfWordsAndRegistersEach() {
		Integer index = 12;
		String documentName = "some.thing";
		Mockito.when(nextWordSetDao.findAndIncrement()).thenReturn(new NextWordSet(documentName, index));
		List<String> words = new ArrayList<>();
		words.add("FirstWord");
		words.add("SecondWord");
		words.add("ThirdWord");
		words.add("FourthWord");
		words.add("FirstWord");

		Mockito.when(wordSetDao.getByIndex(index)).thenReturn(new WordSet(documentName, words, index));

		documentAnalysingService.analyseDocument(documentName);

		InOrder inOrder = Mockito.inOrder(wordUseDao);

		inOrder.verify(wordUseDao).registerUse(words.get(0));
		inOrder.verify(wordUseDao).registerUse(words.get(1));
		inOrder.verify(wordUseDao).registerUse(words.get(2));
		inOrder.verify(wordUseDao).registerUse(words.get(3));
		inOrder.verify(wordUseDao).registerUse(words.get(4));
	}
}
