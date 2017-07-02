package com.alastair.textanalysis.service;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.alastair.textanalysis.dao.MaxWordSetDao;
import com.alastair.textanalysis.dao.WordSetDao;
import com.alastair.textanalysis.model.MaxWordSet;
import com.alastair.textanalysis.model.WordSet;

@RunWith(MockitoJUnitRunner.class)
public class DefaultDocumentParsingServiceTest {

	@Mock
	private MaxWordSetDao maxWordSetDao;

	@Mock
	private WordSetDao wordSetDao;

	private Integer sizeOfPartition = 10;

	private DocumentParsingService documentParsingService;

	@Before
	public void setup() {
		documentParsingService = new DefaultDocumentParsingService(wordSetDao, sizeOfPartition);
	}

	@Test
	public void parseDocument_SmallFile_StoresCorrectLists() {
		String sourceFile = "simple.test";
		Mockito.when(maxWordSetDao.findAndIncrement(sourceFile)).thenReturn(new MaxWordSet(sourceFile, 0),
				new MaxWordSet(sourceFile, 1));

		documentParsingService.parseDocument(sourceFile);

		ArgumentCaptor<WordSet> wordSetCaptor = ArgumentCaptor.forClass(WordSet.class);

		Mockito.verify(wordSetDao, Mockito.times(2)).createWordSet(wordSetCaptor.capture());

		List<WordSet> wordSets = wordSetCaptor.getAllValues();

		List<String> firstWordSet = wordSets.get(0).getWords();

		Assert.assertEquals("one", firstWordSet.get(0));
		Assert.assertEquals("two", firstWordSet.get(1));
		Assert.assertEquals("three", firstWordSet.get(2));
		Assert.assertEquals("four", firstWordSet.get(3));
		Assert.assertEquals("five", firstWordSet.get(4));
		Assert.assertEquals("six", firstWordSet.get(5));
		Assert.assertEquals("seven", firstWordSet.get(6));
		Assert.assertEquals("eight", firstWordSet.get(7));
		Assert.assertEquals("nine", firstWordSet.get(8));
		Assert.assertEquals("ten", firstWordSet.get(9));

		List<String> secondWordSet = wordSets.get(1).getWords();

		Assert.assertEquals("eleven", secondWordSet.get(0));
		Assert.assertEquals("twelve", secondWordSet.get(1));
		Assert.assertEquals("thirteen", secondWordSet.get(2));
		Assert.assertEquals("fourteen", secondWordSet.get(3));
		Assert.assertEquals("fifteen", secondWordSet.get(4));
		Assert.assertEquals("sixteen", secondWordSet.get(5));
		Assert.assertEquals("seventeen", secondWordSet.get(6));
		Assert.assertEquals("eighteen", secondWordSet.get(7));
		Assert.assertEquals("nineteen", secondWordSet.get(8));
		Assert.assertEquals("twenty", secondWordSet.get(9));

	}

	@Test
	public void parseDocument_LargeFile_StoresListsOfStrings() {
		documentParsingService.parseDocument("test.txt");

		ArgumentCaptor<WordSet> wordSetCaptor = ArgumentCaptor.forClass(WordSet.class);

		Mockito.verify(wordSetDao, Mockito.atLeastOnce()).createWordSet(wordSetCaptor.capture());

		assertTrue(wordSetCaptor.getAllValues().stream()
				.allMatch(wordSet -> "test.txt".equals(wordSet.getDocumentName())));
		assertTrue(
				wordSetCaptor.getAllValues().stream().allMatch(wordSet -> wordSet.getWords().size() <= sizeOfPartition));
	}
}
