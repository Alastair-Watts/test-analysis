package com.alastair.textanalysis.runner;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.alastair.textanalysis.service.DocumentAnalysingService;
import com.alastair.textanalysis.service.DocumentParsingService;
import com.alastair.textanalysis.service.ServerInstanceService;
import com.alastair.textanalysis.utils.Constants;

@RunWith(MockitoJUnitRunner.class)
public class TextAnalysisRunnerTest {

	private static final String SOURCE_FILE = "filename.extension";

	@Mock
	private ServerInstanceService instanceService;

	@Mock
	private DocumentParsingService parsingService;

	@Mock
	private DocumentAnalysingService analysisService;

	@InjectMocks
	private TextAnalysisRunner textAnalysisRunner;

	@Test
	public void run_AnotherInstanceRunning_CreatesInstanceRecordParsesThenAnalyses() throws Exception {
		Mockito.when(instanceService.isFirstInstance()).thenReturn(true);

		InOrder inOrder = Mockito.inOrder(instanceService, parsingService, analysisService);

		List<String> args = new ArrayList<>();
		args.add(Constants.FILE_LOCATION_PROPERTY);
		args.add(SOURCE_FILE);

		textAnalysisRunner.run(args.toArray(new String[0]));

		inOrder.verify(instanceService, Mockito.times(1)).createFirstInstance();
		inOrder.verify(parsingService, Mockito.times(1)).parseDocument(SOURCE_FILE);
		inOrder.verify(analysisService, Mockito.times(1)).analyseDocument(SOURCE_FILE);
	}

	@Test
	public void run_NoInstanceRunning_StartsAnalysis() throws Exception {
		Mockito.when(instanceService.isFirstInstance()).thenReturn(false);

		InOrder inOrder = Mockito.inOrder(instanceService, parsingService, analysisService);

		List<String> args = new ArrayList<>();
		args.add(Constants.FILE_LOCATION_PROPERTY);
		args.add(SOURCE_FILE);

		textAnalysisRunner.run(args.toArray(new String[0]));

		inOrder.verify(instanceService, Mockito.times(0)).createFirstInstance();
		inOrder.verify(parsingService, Mockito.times(0)).parseDocument(SOURCE_FILE);
		inOrder.verify(analysisService, Mockito.times(1)).analyseDocument(SOURCE_FILE);
	}

	@Test
	public void run_MultipleArgumentsPassed_Analyses() throws Exception {
		Mockito.when(instanceService.isFirstInstance()).thenReturn(false);

		InOrder inOrder = Mockito.inOrder(instanceService, parsingService, analysisService);

		List<String> args = new ArrayList<>();
		args.add(Constants.DATABASE_LOCATION_PROPERTY);
		args.add("somehost:1234");
		args.add(Constants.FILE_LOCATION_PROPERTY);
		args.add(SOURCE_FILE);
		args.add(Constants.SERVER_ID_PROPERTY);
		args.add("SuperServer5000");

		textAnalysisRunner.run(args.toArray(new String[0]));

		inOrder.verify(instanceService, Mockito.times(0)).createFirstInstance();
		inOrder.verify(parsingService, Mockito.times(0)).parseDocument(SOURCE_FILE);
		inOrder.verify(analysisService, Mockito.times(1)).analyseDocument(SOURCE_FILE);
	}

	@Test(expected = IllegalArgumentException.class)
	public void run_NoSourceFilename_ThrowsException() throws Exception {
		Mockito.when(instanceService.isFirstInstance()).thenReturn(false);

		List<String> args = new ArrayList<>();

		textAnalysisRunner.run(args.toArray(new String[0]));
	}
}
