package com.alastair.textanalysis.service;

import org.springframework.stereotype.Service;

import com.alastair.textanalysis.dao.WordSetDao;
import com.alastair.textanalysis.dao.WordUseDao;
import com.alastair.textanalysis.model.WordSet;

@Service
public class DefaultDocumentAnalysingService implements DocumentAnalysingService {

	private WordSetDao wordSetDao;
	private WordUseDao wordUseDao;
	private String documentName;

	public DefaultDocumentAnalysingService(WordSetDao wordSetDao, WordUseDao wordUseDao, String documentName) {
		this.wordSetDao = wordSetDao;
		this.wordUseDao = wordUseDao;
		this.documentName = documentName;
	}

	@Override
	public void analyse() {
		WordSet wordSet = wordSetDao.findUnprocessedMarkProcessed(documentName);
		wordSet.getWords().forEach(word -> wordUseDao.registerUse(word, documentName));
	}
}
