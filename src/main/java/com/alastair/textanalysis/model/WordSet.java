package com.alastair.textanalysis.model;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@CompoundIndex(def = "{documentName: 1, index: 1}, { unique: true}")
public class WordSet {

	@Id
	private ObjectId id;
	private final String documentName;
	private final List<String> words;
	private final ProcessingStatus status;

	public WordSet(String documentName, List<String> words) {
		this.documentName = documentName;
		this.words = words;
		this.status = ProcessingStatus.UNPROCESSED;
	}

	public String getDocumentName() {
		return documentName;
	}

	public List<String> getWords() {
		return words;
	}

	public ProcessingStatus getStatus() {
		return status;
	}
}
