package com.alastair.textanalysis.dao;

import com.alastair.textanalysis.model.WordSet;

public interface WordSetDao {

	WordSet getByIndex(Integer index);

}
