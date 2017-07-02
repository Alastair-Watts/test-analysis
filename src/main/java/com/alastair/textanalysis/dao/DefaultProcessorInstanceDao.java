package com.alastair.textanalysis.dao;

import org.springframework.stereotype.Repository;

import com.alastair.textanalysis.model.Instance;

@Repository
public class DefaultProcessorInstanceDao implements ProcessorInstanceDao {

	public Instance findInstance(String documentName) {
		return null;
	}

	public void createInstance(String documentName) {
	}

}
