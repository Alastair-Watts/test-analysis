package com.alastair.textanalysis.dao;

import org.springframework.stereotype.Repository;

import com.alastair.textanalysis.model.NextWordSet;

@Repository
public class DefaultNextWordSetDao implements NextWordSetDao {

	public NextWordSet findAndIncrement(String docName) {
		return null;
	}

}
