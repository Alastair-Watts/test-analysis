package com.alastair.textanalysis.dao;

import com.alastair.textanalysis.model.NextWordSet;

public interface NextWordSetDao {

	NextWordSet findAndIncrement();

}
