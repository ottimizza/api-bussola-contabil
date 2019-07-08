package br.com.ottimizza.dashboard.graphql.mutations;

import javax.persistence.EntityManager;

import br.com.ottimizza.dashboard.models.Company;
import br.com.ottimizza.dashboard.models.QCompany;
import br.com.ottimizza.dashboard.repositories.company.CompanyRepository;
import io.leangen.graphql.annotations.GraphQLMutation;

public class CompanyMutation {

	private EntityManager em;
	private QCompany company = QCompany.company;
	private CompanyRepository companyRepository;

	public CompanyMutation(EntityManager em, CompanyRepository companyRepository) {
		this.em = em;
		this.companyRepository = companyRepository;
	}

	@GraphQLMutation //1
	public Company createCompany(Company filter) { //2
	    Company newCompany = new Company(filter.getCnpj(), filter.getName());
	    
	    companyRepository.save(newCompany);
	    return newCompany;	
	}
	
	

}
