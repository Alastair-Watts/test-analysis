package com.alastair.textanalysis.dao.it;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alastair.textanalysis.dao.NextWordSetDao;

public class DefaultNextWordSetDaoIT extends DaoIT {

	@Autowired
	private NextWordSetDao nextWordSetDao;

	@Test
	public void nextWordSetCreatesAndIncrement() {
		String documentName = "This.file";
		assertEquals(0, nextWordSetDao.findAndIncrement(documentName).getIndex().intValue());
		assertEquals(1, nextWordSetDao.findAndIncrement(documentName).getIndex().intValue());
		assertEquals(2, nextWordSetDao.findAndIncrement(documentName).getIndex().intValue());
		assertEquals(3, nextWordSetDao.findAndIncrement(documentName).getIndex().intValue());
		assertEquals(4, nextWordSetDao.findAndIncrement(documentName).getIndex().intValue());
	}
}
