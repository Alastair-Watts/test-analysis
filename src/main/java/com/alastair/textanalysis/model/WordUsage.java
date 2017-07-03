package com.alastair.textanalysis.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@CompoundIndex(def = "{documentName: 1, word: 1}, { unique: true}")
public class WordUsage {

	@Id
	private ObjectId id;
	private final String documentName;
	private final String word;
	private Long count;

	public WordUsage(String documentName, String word) {
		this.documentName = documentName;
		this.word = word;
		count = 0L;
	}

	public WordUsage() {
		//no args constructor for Spring Rest Controller
		this.documentName = "";
		this.word = "";
		count = 0L;
	}

	public Long getCount() {
		return count;
	}

	public String getWord() {
		return word;
	}

	public String getDocumentName() {
		return documentName;
	}
}
