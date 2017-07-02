package com.alastair.textanalysis.service;

import org.springframework.stereotype.Service;

import com.alastair.textanalysis.dao.ProcessorInstanceDao;

@Service
public class DefaultServerInstanceService implements ServerInstanceService {

	private ProcessorInstanceDao processorInstanceDao;

	public DefaultServerInstanceService(ProcessorInstanceDao processorInstanceDao) {
		this.processorInstanceDao = processorInstanceDao;
	}

	@Override
	public boolean isFirstInstance(String documentName) {
		return processorInstanceDao.findInstance(documentName) == null;
	}

	@Override
	public void createFirstInstance(String documentName) {
		processorInstanceDao.createInstance(documentName);
	}

}
