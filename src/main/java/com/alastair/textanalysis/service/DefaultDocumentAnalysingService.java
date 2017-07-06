package com.alastair.textanalysis.service;

import org.springframework.stereotype.Service;

import com.alastair.textanalysis.dao.WordSetDao;
import com.alastair.textanalysis.dao.WordUseDao;
import com.alastair.textanalysis.model.WordSet;

@Service
public class DefaultDocumentAnalysingService implements DocumentAnalysingService {

	private WordSetDao wordSetDao;
	private WordUseDao wordUseDao;

	public DefaultDocumentAnalysingService(WordSetDao wordSetDao, WordUseDao wordUseDao) {
		this.wordSetDao = wordSetDao;
		this.wordUseDao = wordUseDao;
	}

	@Override
	public void analyseDocument(String sourceFile) {
		WordSet wordSet;
		while ((wordSet = nextWordSet(sourceFile)) != null) {
			wordSet.getWords().forEach(word -> wordUseDao.registerUse(word, sourceFile));
		}
	}

	private WordSet nextWordSet(String sourceFile) {
		return wordSetDao.findUnprocessedMarkProcessed(sourceFile);
	}
}
