package com.alastair.textanalysis.dao;

import org.springframework.stereotype.Repository;

import com.alastair.textanalysis.model.WordSet;

@Repository
public class DefaultWordSetDao implements WordSetDao {

	public WordSet getByIndex(Integer index) {
		return null;
	}

	@Override
	public void createWordSet(WordSet capture) {
		// TODO Auto-generated method stub
		
	}

}