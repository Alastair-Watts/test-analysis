package com.alastair.textanalysis.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.alastair.textanalysis.dao.WordUseDao;
import com.alastair.textanalysis.model.WordUsage;

@RestController
public class ResultsController {

	public static final String CHALLENGE_MIN = "challenge/min";
	public static final String CHALLENGE_MAX = "challenge/max";
	private WordUseDao wordUseDao;
	private String documentName;

	public ResultsController(WordUseDao wordUseDao, String documentName) {
		this.wordUseDao = wordUseDao;
		this.documentName = documentName;
	}

	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping(path = CHALLENGE_MAX, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public WordUsage mostUsedWord() {
		return wordUseDao.getMostUsed(documentName);
	}

	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping(path = CHALLENGE_MIN, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public WordUsage leastUsedWord() {
		return wordUseDao.getLeastUsed(documentName);
	}

}
