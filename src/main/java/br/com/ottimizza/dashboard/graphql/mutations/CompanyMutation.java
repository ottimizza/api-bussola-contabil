package br.com.ottimizza.dashboard.graphql.mutations;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import br.com.ottimizza.dashboard.models.Company;
import br.com.ottimizza.dashboard.models.Kpi;
import br.com.ottimizza.dashboard.models.QCompany;
import br.com.ottimizza.dashboard.repositories.company.CompanyRepository;
import br.com.ottimizza.dashboard.repositories.kpi.KpiRepository;
import br.com.ottimizza.dashboard.repositories.kpi_detail.KpiDetailRepository;
import br.com.ottimizza.dashboard.services.KpiService;
import io.leangen.graphql.annotations.GraphQLMutation;

public class CompanyMutation {
	

	private EntityManager em;
	private QCompany company = QCompany.company;
	private KpiDetailRepository kpiDetailRepository;
	private KpiRepository kpiRepository;
	private CompanyRepository companyRepository;

	private KpiService kpiService;
	
	public CompanyMutation(EntityManager em, KpiDetailRepository kpiDetailRepository, KpiRepository kpiRepository, CompanyRepository companyRepository, KpiService kpiService) {
		this.em = em;
		this.kpiDetailRepository = kpiDetailRepository;
		this.kpiRepository = kpiRepository;
		this.companyRepository = companyRepository;
		this.kpiService = kpiService;
	}

	@GraphQLMutation(name = "createCompany")
	public Company createCompany(String cnpj, String name, List<Kpi> kpis) {
		
		Company newCompany = new Company(); 
		newCompany.setCnpj(cnpj);
	    newCompany.setName(name);
	    newCompany = companyRepository.save(newCompany);
	    
	    if(kpis != null)
    		for (Kpi kpi : kpis) {
    			try { Kpi newKpi = kpiService.createKpi(newCompany.getId(), kpi); }
    			catch (Exception e) {System.out.printf("Nao foi possivel criar KPI { kpiAlias: %s, title: %s }", kpi.getKpiAlias(), kpi.getTitle());}
		    }
	    
	    Optional<Company> optCompany = companyRepository.findById(newCompany.getId());
	    if (optCompany != null) newCompany = optCompany.get();
	    
	    //buscar new company pelo id do new company
	    return newCompany;
	}
//		System.out.println(">>>1 ");
//	    Company newCompany = new Company(); 
//	    
//	    if(kpis.equals(null) && !kpis.isEmpty()) {
//	    	System.out.println(">>>3 ");
//		    
//		    newCompany.setCnpj(cnpj);
//		    newCompany.setName(name);
//		    newCompany = companyRepository.save(newCompany);
//			
//		    for (Kpi kpi : kpis) {
//		    	System.out.println(">>>4 ");
//			    
//		    	try {
//			    	KpiMutation kpiMut = new KpiMutation(em, kpiDetailRepository, kpiRepository, companyRepository);
//			    	kpiMut.createKpi(newCompany.getId(), kpi);
//		    	}catch (Exception e) {
//		    		System.out.println("<><> "+e.getMessage());
//				}
//		    }
//	    } else {
//	    	System.out.println(">>>2 ");
//		    
//		    newCompany.setCnpj(cnpj);
//		    newCompany.setName(name);
//		    return companyRepository.save(newCompany);
//	    
//	    }
////	    companyRepository.save(newCompany);
//	    return newCompany;	
//	}
	
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
