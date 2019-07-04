package br.com.ottimizza.dashboard.graphql.mutations;

import javax.persistence.EntityManager;

import br.com.ottimizza.dashboard.models.Company;
import br.com.ottimizza.dashboard.models.QCompany;
import br.com.ottimizza.dashboard.repositories.company.CompanyRepository;
import io.leangen.graphql.annotations.GraphQLMutation;

public class CompanyMutation {

	private QCompany company = QCompany.company;
	private EntityManager em;
	private CompanyRepository companyRepository;
	
	public CompanyMutation(EntityManager em, CompanyRepository companyRepository) {
		this.em = em;
		this.companyRepository = companyRepository;
	}

	@GraphQLMutation //1
//	public Company createCompany(String cnpj, String name, @GraphQLRootContext AuthorizationContext context) { //2
	public Company createCompany(String cnpj, String name) { //2
	    Company newCompany = new Company(cnpj, name);
	    
	    companyRepository.save(newCompany);
	    return newCompany;
	}
}

