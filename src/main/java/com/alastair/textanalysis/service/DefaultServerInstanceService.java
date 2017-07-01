package com.alastair.textanalysis.service;

import org.springframework.stereotype.Service;

@Service
public class DefaultServerInstanceService implements ServerInstanceService {

	@Override
	public boolean isFirstInstance() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void createFirstInstance() {
		// TODO Auto-generated method stub

	}

}
