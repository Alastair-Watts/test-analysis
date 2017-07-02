package com.alastair.textanalysis.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class NextWordSet {

	@Id
	private ObjectId id;
	@Indexed(unique = true)
	private String documentName;
	private Integer index;

	public NextWordSet(String documentName, Integer index) {
		this.documentName = documentName;
		this.index = index;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

}
