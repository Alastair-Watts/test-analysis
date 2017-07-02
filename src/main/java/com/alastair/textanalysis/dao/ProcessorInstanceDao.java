package com.alastair.textanalysis.dao;

import com.alastair.textanalysis.model.Instance;

public interface ProcessorInstanceDao {

	Instance findInstance(String documentName);

	void createInstance(String documentName);

}
