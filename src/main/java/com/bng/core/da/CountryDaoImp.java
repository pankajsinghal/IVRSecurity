package com.bng.core.da;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bng.core.entity.Country;
import com.bng.core.entity.Operator;

@Transactional("global")
public class CountryDaoImp implements CountryDao {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Transactional
	public List<Country> getCountry() {
		Session session = sessionFactory.getCurrentSession();
    	List<Country> list = session.createQuery("from Country").list();
        return list;
	}

	@Transactional
	public List<Operator> getOperator() {
		Session session = sessionFactory.getCurrentSession();
		List<Operator> list = session.createQuery("from Operator").list();
		return list;
	}
}
