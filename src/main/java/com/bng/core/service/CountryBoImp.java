package com.bng.core.service;

import java.util.List;

import com.bng.core.da.CountryDao;
import com.bng.core.entity.Country;
import com.bng.core.entity.Operator;

public class CountryBoImp implements CountryBo {
	private CountryDao countryDao;

	public void setCountryDao(CountryDao CountryDao) {
		this.countryDao = CountryDao;
	}

	public List<Country> getCountry(){
		return countryDao.getCountry();
	}

	public List<Operator> getOperator() {
	
		return countryDao.getOperator();
	}
}
