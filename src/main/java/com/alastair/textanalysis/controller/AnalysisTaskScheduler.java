package com.alastair.textanalysis.controller;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alastair.textanalysis.service.DocumentAnalysingService;

@Component
public class AnalysisTaskScheduler {

	private DocumentAnalysingService documentAnalysingService;

	public AnalysisTaskScheduler(DocumentAnalysingService documentAnalysingService) {
		this.documentAnalysingService = documentAnalysingService;
	}

	@Scheduled(fixedRate = 100L)
	public void analyseAWordSet() {
		documentAnalysingService.analyse();
	}
}
