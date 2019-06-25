package br.com.ottimizza.dashboard.graphQL;

import java.math.BigInteger;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
// JPAQUERY
import com.querydsl.jpa.impl.JPAQuery;

import br.com.ottimizza.dashboard.models.Company;
import br.com.ottimizza.dashboard.models.QCompany;
import br.com.ottimizza.dashboard.repositories.company.CompanyRepository;
import io.leangen.graphql.annotations.GraphQLQuery;

public class CompanyResolver {

	private QCompany company = QCompany.company;

	private EntityManager em;
	private CompanyRepository companyRepository;

	public CompanyResolver(EntityManager em, CompanyRepository companyRepository) {
		this.em = em;
		this.companyRepository = companyRepository;
	}

	@GraphQLQuery
	public List<Company> findCompany(Company c) {
		JPAQuery<Company> query = new JPAQuery<Company>(em).from(company);

		if (c.getId() != null) {
			query.where(company.id.in(c.getId()));
		}

		return query.orderBy(company.name.asc()).fetch();
	}

	@GraphQLQuery
	public List<Company> findCompany(String name) {
		JPAQuery<Company> query = new JPAQuery<Company>(em).from(company);

		if (name != null) {
			query.where(company.name.contains(name));
		}

		return query.orderBy(company.name.asc()).fetch();
	}
	public long countCompany() {
		return companyRepository.count();
	}

}
