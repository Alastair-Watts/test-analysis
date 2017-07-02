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
public class DefaultDocumentAnalysingServiceTest {

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
		Mockito.when(nextWordSetDao.findAndIncrement(documentName)).thenReturn(new NextWordSet(documentName, index),
				new NextWordSet(documentName, index + 1));
		List<String> words = new ArrayList<>();
		words.add("FirstWord");
		words.add("SecondWord");
		words.add("ThirdWord");
		words.add("FourthWord");
		words.add("FirstWord");

		Mockito.when(wordSetDao.getByIndex(documentName, index)).thenReturn(new WordSet(documentName, words, index));

		documentAnalysingService.analyseDocument(documentName);

		InOrder inOrder = Mockito.inOrder(wordUseDao);

		inOrder.verify(wordUseDao).registerUse(words.get(0), documentName);
		inOrder.verify(wordUseDao).registerUse(words.get(1), documentName);
		inOrder.verify(wordUseDao).registerUse(words.get(2), documentName);
		inOrder.verify(wordUseDao).registerUse(words.get(3), documentName);
		inOrder.verify(wordUseDao).registerUse(words.get(4), documentName);
	}

	@Test
	public void analyseDocument_MultipleWordSets_AnalysesAll() {
		Integer index = 12;
		String documentName = "some.thing";
		Mockito.when(nextWordSetDao.findAndIncrement(documentName)).thenReturn(new NextWordSet(documentName, index),
				new NextWordSet(documentName, index + 1), new NextWordSet(documentName, index + 2));
		List<String> words = new ArrayList<>();
		words.add("FirstWord");
		words.add("SecondWord");
		words.add("ThirdWord");
		words.add("FourthWord");
		words.add("FirstWord");

		List<String> words2 = new ArrayList<>();
		words2.add("SixthWord");
		words2.add("SeventhWord");
		words2.add("EighthWord");
		words2.add("NinthWord");
		words2.add("TenthWord");

		Mockito.when(wordSetDao.getByIndex(documentName, index)).thenReturn(new WordSet(documentName, words, index));
		Mockito.when(wordSetDao.getByIndex(documentName, index + 1))
				.thenReturn(new WordSet(documentName, words2, index + 1));

		documentAnalysingService.analyseDocument(documentName);

		InOrder inOrder = Mockito.inOrder(wordUseDao);

		inOrder.verify(wordUseDao).registerUse(words.get(0), documentName);
		inOrder.verify(wordUseDao).registerUse(words.get(1), documentName);
		inOrder.verify(wordUseDao).registerUse(words.get(2), documentName);
		inOrder.verify(wordUseDao).registerUse(words.get(3), documentName);
		inOrder.verify(wordUseDao).registerUse(words.get(4), documentName);

		inOrder.verify(wordUseDao).registerUse(words2.get(0), documentName);
		inOrder.verify(wordUseDao).registerUse(words2.get(1), documentName);
		inOrder.verify(wordUseDao).registerUse(words2.get(2), documentName);
		inOrder.verify(wordUseDao).registerUse(words2.get(3), documentName);
		inOrder.verify(wordUseDao).registerUse(words2.get(4), documentName);
	}
}
