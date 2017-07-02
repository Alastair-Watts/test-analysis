package com.alastair.textanalysis.dao.it;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.alastair.textanalysis.TextAnalysisConfig;
import com.alastair.textanalysis.dao.DefaultProcessorInstanceDao;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { TextAnalysisConfig.class })
@ActiveProfiles(profiles = "noRunner")
public class DefaultProcessorInstanceDaoIT {

	@Autowired
	private DefaultProcessorInstanceDao processorInstanceDao;

	@Test
	public void createInstance_CallsSave() {
		String documentName = "a.document";
		assertNull(processorInstanceDao.findInstance(documentName));
		processorInstanceDao.createInstance(documentName);
		assertNotNull(processorInstanceDao.findInstance(documentName));
	}
}
