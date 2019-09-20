package br.com.ottimizza.dashboard.services;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.persistence.NoResultException;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import br.com.ottimizza.dashboard.models.Company;
import br.com.ottimizza.dashboard.models.Kpi;
import br.com.ottimizza.dashboard.models.KpiDetail;
import br.com.ottimizza.dashboard.repositories.company.CompanyRepository;
import br.com.ottimizza.dashboard.repositories.kpi.KpiRepository;
import br.com.ottimizza.dashboard.repositories.kpi_detail.KpiDetailRepository;

@Service
public class CompanyService {

    @Inject
    private CompanyRepository repository;
    
    @Inject
    private KpiRepository kpiRepository;
    
    @Inject
    private KpiDetailRepository kpiDetailRepository;
    
    public Company save(Company company)throws Exception{
        return repository.save(company);
    }
    
    public Optional<Company> findById(BigInteger idCompany)throws Exception{
        return repository.findById(idCompany);
    }
    
    public List<Company> findByListCNPJ(List<String> cnpj)throws Exception{
        return repository.findCompaniesByCNPJ(cnpj);
    }
    
    public JSONObject updateById(BigInteger idCompany, Company company) throws Exception{
        JSONObject response = new JSONObject();
        try {
            Optional<Company> companyOptional = repository.findById(idCompany);
            
            if(!companyOptional.isPresent()) throw new NoResultException();
                
            company.setId(idCompany);
            repository.save(company);
            
            response.put("status","sucess");
            response.put("message","Atualizado empresa com sucesso!");
            
        } catch (NoResultException e0) {
            response.put("status","error");
            response.put("message","Problema ao encontrar a empresa!");
            throw new NoResultException(response.toString()) ;
        } catch (Exception e1) {
            response.put("status","Error");
            response.put("message","Houve um problema ao atualizar!");
            throw new Exception(response.toString()) ;
        }
        return response;
    }
    
    public JSONObject delete(BigInteger idCompany)throws Exception{
        JSONObject response = new JSONObject();
        try {
            repository.deleteById(idCompany);
            response.put("status","sucess");
            response.put("message","Excluído empresa com sucesso!");
        } catch (Exception e) {
            response.put("status","Error");
            response.put("message","Houve um problema ao excluir!");
            return response;
        }
        return response;
    }
    
    public JSONObject deleteAllInformationByCNPJ(String cnpj) throws Exception{
        JSONObject response = new JSONObject();
        List<String> cnpjs = new ArrayList<>();
        cnpjs.add(cnpj);
        
        try {
            List<Kpi> kpis = kpiRepository.findKpisByCNPJ(cnpjs);
            
            List<KpiDetail> kpiDetails = kpiDetailRepository.findKpiDetailsByCNPJ(cnpjs);
            System.out.println(" >>> 2 => "+kpis.size()+" - "+kpiDetails.size());
            
            for (KpiDetail kpiDetail : kpiDetails) {
            	kpiDetailRepository.delete(kpiDetail);
            }
            
            for (Kpi kpi : kpis) {
                kpiRepository.delete(kpi);
            }
            System.out.println(" >>> 5 => "+kpis.size() + " / " + kpiDetails.size());
            
            
            response.put("status","sucess");
            response.put("message","Excluído informações com sucesso!");
        } catch (Exception e) {
            response.put("status","Error");
            response.put("message","Houve um problema ao excluir!");
            
            return response;
        }
        
        return response;
    }
    
    
    
}
