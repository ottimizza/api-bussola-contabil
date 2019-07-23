package br.com.ottimizza.dashboard.graphql.mutations;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;

import org.json.JSONObject;

import br.com.ottimizza.dashboard.models.Company;
import br.com.ottimizza.dashboard.models.Kpi;
import br.com.ottimizza.dashboard.models.QCompany;
import br.com.ottimizza.dashboard.repositories.company.CompanyRepository;
import br.com.ottimizza.dashboard.repositories.kpi.KpiRepository;
import br.com.ottimizza.dashboard.repositories.kpi_detail.KpiDetailRepository;
import io.leangen.graphql.annotations.GraphQLMutation;

public class CompanyMutation {

	private EntityManager em;
	private QCompany company = QCompany.company;
	private KpiDetailRepository kpiDetailRepository;
	private KpiRepository kpiRepository;
	private CompanyRepository companyRepository;

	public CompanyMutation(EntityManager em, KpiDetailRepository kpiDetailRepository, KpiRepository kpiRepository, CompanyRepository companyRepository) {
		this.em = em;
		this.kpiDetailRepository = kpiDetailRepository;
		this.kpiRepository = kpiRepository;
		this.companyRepository = companyRepository;
		
	}

	@GraphQLMutation(name = "createCompany")
	public Company createCompany(String cnpj, String name, List<Kpi> kpis) {
	    Company newCompany = new Company(); 
	    if(kpis.isEmpty()) {
		    newCompany.setCnpj(cnpj);
		    newCompany.setName(name);
		    return companyRepository.save(newCompany);
	    }
	    if(!kpis.isEmpty()) {
		    newCompany.setCnpj(cnpj);
		    newCompany.setName(name);
		    newCompany = companyRepository.save(newCompany);
			
		    for (Kpi kpi : kpis) {
		    	try {
			    	KpiMutation kpiMut = new KpiMutation(em, kpiDetailRepository, kpiRepository, companyRepository);
			    	kpiMut.createKpi(newCompany.getId(), kpi);
		    	}catch (Exception e) {
		    		System.out.println("<><> "+e.getMessage());
				}
		    }
	    }
	    companyRepository.save(newCompany);
	    return newCompany;	
	}
	
//	@GraphQLMutation(name = "createCompany")
//	public Company createCompany(Company filter) {
//	    companyRepository.save(filter);
//	    return filter;	
//	}
	
	
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
