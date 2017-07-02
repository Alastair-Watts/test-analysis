package com.alastair.textanalysis.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.alastair.textanalysis.dao.WordSetDao;
import com.alastair.textanalysis.model.WordSet;

@Service
public class DefaultDocumentParsingService implements DocumentParsingService {

	private WordSetDao wordSetDao;
	private Integer sizeOfPartition;

	public DefaultDocumentParsingService(WordSetDao wordSetDao,
			@Value("${string.collection.size}") Integer sizeOfPartition) {
		this.wordSetDao = wordSetDao;
		this.sizeOfPartition = sizeOfPartition;
	}

	@Override
	@Async
	public void parseDocument(String sourceFile) {
		Pattern specialCharacters = Pattern.compile("[^A-Za-z0-9]");

		URL systemResource = ClassLoader.getSystemResource(sourceFile);

		if (systemResource == null) {
			throw new IllegalArgumentException("Could not find source file with name: " + sourceFile);
		}

		try (Stream<String> allLines = Files.lines(Paths.get(systemResource.toURI()))) {
			Spliterator<String> spliterator = allLines.map(line -> Stream.of(line.split(" ")))
					.flatMap(Function.identity()).map(word -> specialCharacters.matcher(word).replaceAll(""))
					.map(String::toLowerCase).spliterator();
			for (int i = 0; true; i++) {
				List<String> currentList = new ArrayList<>(sizeOfPartition);
				while (currentList.size() < sizeOfPartition && spliterator.tryAdvance(currentList::add)) {
				}
				if (currentList.isEmpty()) {
					break;
				}
				wordSetDao.createWordSet(new WordSet(sourceFile, new ArrayList<>(currentList), i));
				currentList.clear();
			}
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
			throw new IllegalStateException("Error processing source file: " + sourceFile, e);
		}
	}

}
