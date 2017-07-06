package com.alastair.textanalysis.dao;

import com.alastair.textanalysis.model.WordSet;

public interface WordSetDao {

	WordSet findUnprocessedMarkProcessed(String documentName);

	void createWordSet(WordSet wordSet);

}
