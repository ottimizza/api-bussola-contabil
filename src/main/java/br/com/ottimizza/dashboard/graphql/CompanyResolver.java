package br.com.ottimizza.dashboard.graphql;

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
	public List<Company> findCompany(Company filter) {
		JPAQuery<Company> query = new JPAQuery<Company>(em).from(company);
		
		if (filter.getId() != null) {
			query.where(company.id.in(filter.getId()));
		}
		
		if (filter.getName() != null) {
			query.where(company.name.contains(filter.getName()));
		}

		if (filter.getCnpj() != null) {
			query.where(company.cnpj.eq(filter.getCnpj()));
		}

		return query.orderBy(company.name.asc()).fetch();
	}
	
//	@GraphQLQuery
//	public List<String> findCompany(String email) {
//		
//		List<String> cnpjs = sf.getCNPJbyEmail(email);
//		return cnpjs;
//	}

	public long countCompany() {
		return companyRepository.count();
	}

}
