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
	    
	    if(kpis != null) {
    		for (Kpi kpi : kpis) {
    			try { Kpi newKpi = kpiService.createKpi(newCompany.getId(), kpi); }
    			catch (Exception e) {System.out.printf("Nao foi possivel criar KPI { kpiAlias: %s, title: %s }", kpi.getKpiAlias(), kpi.getTitle());}
		    }
	    }
	    Optional<Company> optCompany = companyRepository.findById(newCompany.getId());
	    if (optCompany != null) newCompany = optCompany.get();
	    
	    return newCompany;
	}	
	
	@GraphQLMutation(name = "editCompany")
	public Company editCompany(BigInteger id, String cnpj, String name) {
		Company company = new Company();
		Optional<Company> optCompany = companyRepository.findById(id);
		if(optCompany != null) {
			company = optCompany.get();
			company.setCnpj(cnpj);
			company.setName(name);
		}
		return companyRepository.save(company);
	}

}






















