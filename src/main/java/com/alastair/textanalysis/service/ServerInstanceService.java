package com.alastair.textanalysis.service;

public interface ServerInstanceService {

	boolean isFirstInstance(String documentName);

	void createFirstInstance(String documentName);

}
