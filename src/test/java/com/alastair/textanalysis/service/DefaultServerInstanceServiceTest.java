package com.alastair.textanalysis.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.alastair.textanalysis.dao.ProcessorInstanceDao;
import com.alastair.textanalysis.model.Instance;

@RunWith(MockitoJUnitRunner.class)
public class DefaultServerInstanceServiceTest {

	@Mock
	private ProcessorInstanceDao processorInstanceDao;

	@InjectMocks
	private DefaultServerInstanceService serverInstanceService;

	@Test
	public void isFirstInstance_FindsInstanceCreated_ReturnsFalse() {
		String documentName = "some.doc";
		Mockito.when(processorInstanceDao.findInstance(documentName)).thenReturn(new Instance(documentName));
		assertFalse(serverInstanceService.isFirstInstance(documentName));
	}

	@Test
	public void isFirstInstance_FindsNoInstance_ReturnsTrue() {
		String documentName = "some.doc";
		Mockito.when(processorInstanceDao.findInstance(documentName)).thenReturn(null);
		assertTrue(serverInstanceService.isFirstInstance(documentName));
	}

	@Test
	public void createFirstInstance_CallsDao() {
		String documentName = "some.doc";
		serverInstanceService.createFirstInstance(documentName);
		Mockito.verify(processorInstanceDao).createInstance(documentName);
	}
}
