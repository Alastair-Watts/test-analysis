package com.alastair.textanalysis.dao.it;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alastair.textanalysis.dao.ProcessorInstanceDao;

public class DefaultProcessorInstanceDaoIT extends DaoIT {

	@Autowired
	private ProcessorInstanceDao processorInstanceDao;

	@Test
	public void createInstanceSuccessfullyPersisted() {
		String documentName = "a.document";
		assertNull(processorInstanceDao.findInstance(documentName));
		processorInstanceDao.createInstance(documentName);
		assertNotNull(processorInstanceDao.findInstance(documentName));
	}
}
