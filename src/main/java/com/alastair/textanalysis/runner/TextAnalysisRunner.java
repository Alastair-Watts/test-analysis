package com.alastair.textanalysis.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.alastair.textanalysis.service.DocumentAnalysingService;
import com.alastair.textanalysis.service.DocumentParsingService;
import com.alastair.textanalysis.service.ServerInstanceService;
import com.alastair.textanalysis.utils.Constants;

@Component
@Profile("!noRunner")
public class TextAnalysisRunner implements CommandLineRunner {

	private ServerInstanceService instanceService;
	private DocumentParsingService parsingService;
	private DocumentAnalysingService analysisService;
	private String documentName;

	@Autowired
	public TextAnalysisRunner(ServerInstanceService instanceService, DocumentParsingService parsingService,
			DocumentAnalysingService analysisService, String documentName) {
		this.instanceService = instanceService;
		this.parsingService = parsingService;
		this.analysisService = analysisService;
		this.documentName = documentName;
	}

	@Override
	public void run(String... args) throws Exception {
		if (instanceService.isFirstInstance(documentName)) {
			instanceService.createFirstInstance(documentName);
			parsingService.parseDocument(documentName);
		}
		analysisService.analyseDocument(documentName);
	}

}
