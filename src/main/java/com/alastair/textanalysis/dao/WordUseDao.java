package com.alastair.textanalysis.dao;

import java.util.List;

import com.alastair.textanalysis.model.WordUsage;

public interface WordUseDao {

	void registerUse(String word, String document);

	WordUsage getUsage(String word, String documents);

	List<WordUsage> getMostUsed(String document);

	List<WordUsage> getLeastUsed(String document);
}
