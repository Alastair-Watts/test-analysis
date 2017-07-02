package com.alastair.textanalysis.dao;

import com.alastair.textanalysis.model.WordSet;

public interface WordSetDao {

	WordSet getByIndex(String documentName, Integer index);

	void createWordSet(WordSet wordSet);

}
