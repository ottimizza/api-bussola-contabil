package br.com.ottimizza.dashboard.graphql.queries;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;

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
	public List<Company> findCompany(BigInteger id, String cnpj, String name) {
		JPAQuery<Company> query = new JPAQuery<Company>(em).from(company);
		
		if (id != null) 
			query.where(company.id.in(id));
		
		if (name != null) 
			query.where(company.name.toUpperCase().contains(name.toUpperCase()));
		
		if (cnpj != null) 
			query.where(company.cnpj.eq(cnpj));

		return query.orderBy(company.name.asc()).fetch();
	}
	
}
