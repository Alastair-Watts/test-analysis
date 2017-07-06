package com.alastair.textanalysis.dao.it;

import static org.junit.Assert.assertEquals;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.alastair.textanalysis.dao.WordUseDao;
import com.alastair.textanalysis.model.WordUsage;

@Rollback
public class DefaultWordUseDaoIT extends DaoIT {

	@Autowired
	private WordUseDao wordUseDao;

	private final String commonWord = "and";
	private final String uncommonWord = "discombobulate";
	private String document1;
	private String document2;

	@Before
	public void setup() {
		document1 = RandomStringUtils.random(5) + "." + RandomStringUtils.random(3);
		document2 = RandomStringUtils.random(10) + "." + RandomStringUtils.random(3);
		wordUseDao.registerUse(commonWord, document1);
		wordUseDao.registerUse(uncommonWord, document1);
		wordUseDao.registerUse(commonWord, document1);
		wordUseDao.registerUse(commonWord, document1);

		wordUseDao.registerUse(commonWord, document2);
		wordUseDao.registerUse(uncommonWord, document2);
		wordUseDao.registerUse(uncommonWord, document2);
		wordUseDao.registerUse(uncommonWord, document2);
		wordUseDao.registerUse(commonWord, document2);
	}

	@Test
	public void registerUse_MostUsedCorrect() {
		assertEquals(commonWord, wordUseDao.getMostUsed(document1).get(0).getWord());
		assertEquals(uncommonWord, wordUseDao.getMostUsed(document2).get(0).getWord());
	}

	@Test
	public void registerUse_LeastUsedCorrect() {
		assertEquals(commonWord, wordUseDao.getMostUsed(document1).get(0).getWord());
		assertEquals(uncommonWord, wordUseDao.getMostUsed(document2).get(0).getWord());
	}

	@Test
	public void registerUse_GetUsageCorrect() {
		WordUsage document1CommonWordUsage = wordUseDao.getUsage(commonWord, document1);
		assertEquals(3, document1CommonWordUsage.getCount().intValue());
		assertEquals(commonWord, document1CommonWordUsage.getWord());
		assertEquals(document1, document1CommonWordUsage.getDocumentName());

		WordUsage document2CommonWordUsage = wordUseDao.getUsage(commonWord, document2);
		assertEquals(2, document2CommonWordUsage.getCount().intValue());
		assertEquals(commonWord, document2CommonWordUsage.getWord());
		assertEquals(document2, document2CommonWordUsage.getDocumentName());

		WordUsage document1UncommonWordUsage = wordUseDao.getUsage(uncommonWord, document1);
		assertEquals(1, document1UncommonWordUsage.getCount().intValue());
		assertEquals(uncommonWord, document1UncommonWordUsage.getWord());
		assertEquals(document1, document1UncommonWordUsage.getDocumentName());

		WordUsage document2UncommonWordUsage = wordUseDao.getUsage(uncommonWord, document2);
		assertEquals(3, document2UncommonWordUsage.getCount().intValue());
		assertEquals(uncommonWord, document2UncommonWordUsage.getWord());
		assertEquals(document2, document2UncommonWordUsage.getDocumentName());
	}
}
