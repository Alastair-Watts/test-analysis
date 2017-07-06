package com.alastair.textanalysis.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.alastair.textanalysis.service.DocumentParsingService;
import com.alastair.textanalysis.service.ServerInstanceService;

@Component
@Profile("!noRunner")
public class TextAnalysisRunner implements CommandLineRunner {

	private ServerInstanceService instanceService;
	private DocumentParsingService parsingService;
	private String documentName;

	@Autowired
	public TextAnalysisRunner(ServerInstanceService instanceService, DocumentParsingService parsingService,
			String documentName) {
		this.instanceService = instanceService;
		this.parsingService = parsingService;
		this.documentName = documentName;
	}

	@Override
	public void run(String... args) throws Exception {
		if (instanceService.isFirstInstance(documentName)) {
			instanceService.createFirstInstance(documentName);
			parsingService.parseDocument(documentName);
		}
	}

}
