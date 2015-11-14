package com.bng.core.da;

import java.util.List;

import com.bng.core.entity.Country;
import com.bng.core.entity.Operator;


public interface CountryDao {
	public List<Country> getCountry();

	public List<Operator> getOperator();
}
