package com.alastair.textanalysis.service;

import org.springframework.stereotype.Service;

import com.alastair.textanalysis.dao.NextWordSetDao;
import com.alastair.textanalysis.dao.WordSetDao;
import com.alastair.textanalysis.dao.WordUseDao;
import com.alastair.textanalysis.model.NextWordSet;
import com.alastair.textanalysis.model.WordSet;

@Service
public class DefaultDocumentAnalysingService implements DocumentAnalysingService {

	private NextWordSetDao nextWordSetDao;
	private WordSetDao wordSetDao;
	private WordUseDao wordUseDao;

	public DefaultDocumentAnalysingService(NextWordSetDao nextWordSetDao, WordSetDao wordSetDao,
			WordUseDao wordUseDao) {
		this.nextWordSetDao = nextWordSetDao;
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
		NextWordSet nextWordInstruction;
		nextWordInstruction = nextWordSetDao.findAndIncrement(sourceFile);
		return wordSetDao.getByIndex(sourceFile, nextWordInstruction.getIndex());
	}
}
