package com.alastair.textanalysis.controller;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.alastair.textanalysis.service.DocumentAnalysingService;

@RunWith(MockitoJUnitRunner.class)
public class AnalysisTaskSchedulerTest {

	@Mock
	private DocumentAnalysingService documentAnalysingService;

	@InjectMocks
	private AnalysisTaskScheduler analysisTaskScheduler;

	@Test
	public void analyseAWordSet_CallsService() {
		analysisTaskScheduler.analyseAWordSet();
		verify(documentAnalysingService).analyse();
	}
}
