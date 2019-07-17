package br.com.ottimizza.dashboard.graphql.mutations;

import java.util.List;

import javax.persistence.EntityManager;

import org.json.JSONObject;

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

//	@GraphQLMutation(name = "createCompany")
//	public Company createCompany(Company filter) {
//	    Company newCompany = new Company();  
//	    newCompany.setCnpj(filter.getCnpj());
//	    newCompany.setName(filter.getName());
//	    newCompany.setKpis(filter.getKpis());
//	    companyRepository.save(newCompany);
//	    return newCompany;	
//	}
	
	@GraphQLMutation(name = "createCompany")
	public Company createCompany(Company filter) {
	    companyRepository.save(filter);
	    return filter;	
	}
	
	
	@GraphQLMutation(name = "createCompanies")
	public JSONObject createCompanies(List<Company> companies) {
		JSONObject result = new JSONObject();
		for (Company company : companies) {
			try{
				companyRepository.save(company);
				result.put(company.getCnpj(), "OK");
			}catch (Exception e) {
				result.put(company.getCnpj(), "NAO INSERIDO");
			}
		}
		return result;
	}
	

}
