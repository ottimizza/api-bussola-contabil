package br.com.ottimizza.dashboard.graphql;

import java.math.BigInteger;

import javax.persistence.EntityManager;

import br.com.ottimizza.dashboard.models.Company;
import br.com.ottimizza.dashboard.models.Kpi;
import br.com.ottimizza.dashboard.models.QCompany;
import br.com.ottimizza.dashboard.models.QKpi;
import br.com.ottimizza.dashboard.repositories.company.CompanyRepository;
import br.com.ottimizza.dashboard.repositories.kpi.KpiRepository;
import io.leangen.graphql.annotations.GraphQLMutation;

public class Mutation {

	private QCompany company = QCompany.company;
	private EntityManager em;
	private CompanyRepository companyRepository;

	private QKpi kpi = QKpi.kpi;
	private KpiRepository kpiRepository;

	public Mutation(EntityManager em, CompanyRepository companyRepository) {
		this.em = em;
		this.companyRepository = companyRepository;
	}

	@GraphQLMutation //1
	public Company createCompany(Company filter) { //2
	    Company newCompany = new Company(filter.getCnpj(), filter.getName());
	    
	    companyRepository.save(newCompany);
	    return newCompany;
	}
	
	@GraphQLMutation(name = "editKpi")
	public Kpi editKpi(BigInteger id, String title) {
		Kpi kpi = kpiRepository.findById(id);
		kpi.setTitle(title);
		
		return kpiRepository.save(kpi);
	}

}
