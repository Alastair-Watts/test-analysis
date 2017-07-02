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

	@Autowired
	public TextAnalysisRunner(ServerInstanceService instanceService, DocumentParsingService parsingService,
			DocumentAnalysingService analysisService) {
		this.instanceService = instanceService;
		this.parsingService = parsingService;
		this.analysisService = analysisService;
	}

	@Override
	public void run(String... args) throws Exception {
		String sourceFile = getSourceFileName(args);

		if (instanceService.isFirstInstance(sourceFile)) {
			instanceService.createFirstInstance(sourceFile);
			parsingService.parseDocument(sourceFile);
		}
		analysisService.analyseDocument(sourceFile);
	}

	private String getSourceFileName(String[] args) {
		for (int i = 0; i < args.length; i++) {
			if (Constants.FILE_LOCATION_PROPERTY.equalsIgnoreCase(args[i])) {
				return args[i + 1];
			}
		}
		throw new IllegalArgumentException("No source file provided");
	}
}
