package com.alastair.textanalysis.controller.it;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.web.client.TestRestTemplate;

import com.alastair.textanalysis.controller.ResultsController;
import com.alastair.textanalysis.dao.WordUseDao;
import com.alastair.textanalysis.model.WordUsage;

public class ResultsControllerIT extends BaseIT {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private WordUseDao wordUseDao;

	@Autowired
	private String documentName;

	@Test
	public void getLeastAndMostUsed_SelectionOfWordsAdded_GetsCorrectResult() {
		String mostCommonWord = "and";
		String middleCommonWord = "batter";
		String leastCommonWord = "turquoise";

		int mostCommonCount = 12;
		int middleCommonCount = 8;
		int leastCommonCount = 3;
		for (int i = 0; i < mostCommonCount; i++) {
			wordUseDao.registerUse(mostCommonWord, documentName);
		}
		for (int i = 0; i < middleCommonCount; i++) {
			wordUseDao.registerUse(middleCommonWord, documentName);
		}
		for (int i = 0; i < leastCommonCount; i++) {
			wordUseDao.registerUse(leastCommonWord, documentName);

		}

		WordUsage maxWord = restTemplate
				.getForObject("http://localhost:" + port + "/" + ResultsController.CHALLENGE_MAX, WordUsage.class);
		assertEquals(mostCommonWord, maxWord.getWord());
		assertEquals(mostCommonCount, maxWord.getCount().intValue());
		WordUsage minWord = restTemplate
				.getForObject("http://localhost:" + port + "/" + ResultsController.CHALLENGE_MIN, WordUsage.class);
		assertEquals(leastCommonWord, minWord.getWord());
		assertEquals(leastCommonCount, minWord.getCount().intValue());
	}

}
