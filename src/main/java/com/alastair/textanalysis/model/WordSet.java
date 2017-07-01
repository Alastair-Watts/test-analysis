package com.alastair.textanalysis.model;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class WordSet {

	@Id
	private ObjectId id;
	private final String documentName;
	private final List<String> words;
	private final Integer index;

	public WordSet(String documentName, List<String> words, Integer index) {
		this.documentName = documentName;
		this.words = words;
		this.index = index;
	}

	public String getDocumentName() {
		return documentName;
	}

	public List<String> getWords() {
		return words;
	}

	public Integer getIndex() {
		return index;
	}
}
