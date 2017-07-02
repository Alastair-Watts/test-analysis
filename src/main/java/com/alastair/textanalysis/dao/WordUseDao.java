package com.alastair.textanalysis.dao;

import com.alastair.textanalysis.model.WordUsage;

public interface WordUseDao {

	void registerUse(String word, String document);

	WordUsage getUsage(String word, String documents);

	WordUsage getMostUsed(String document);

	WordUsage getLeastUsed(String document);
}
