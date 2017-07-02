package com.alastair.textanalysis.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Instance {

	@Id
	private ObjectId id;
	private final String documentName;

	public Instance(String documentName) {
		this.documentName = documentName;
	}

	public String getDocumentName() {
		return documentName;
	}
}
