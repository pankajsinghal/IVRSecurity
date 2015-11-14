package com.bng.core.service;

import java.util.List;

import com.bng.core.entity.Country;
import com.bng.core.entity.Operator;

public interface CountryBo {
	
	public List<Country> getCountry();
	public List<Operator> getOperator();
}
