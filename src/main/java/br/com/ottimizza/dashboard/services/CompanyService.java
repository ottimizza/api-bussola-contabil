package br.com.ottimizza.dashboard.services;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.persistence.NoResultException;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import br.com.ottimizza.dashboard.client.OAuthClient;
import br.com.ottimizza.dashboard.domain.dtos.CompanyDTO;
import br.com.ottimizza.dashboard.domain.dtos.OrganizationDTO;
import br.com.ottimizza.dashboard.domain.dtos.UserDTO;
import br.com.ottimizza.dashboard.models.Company;
import br.com.ottimizza.dashboard.models.Kpi;
import br.com.ottimizza.dashboard.models.KpiDetail;
import br.com.ottimizza.dashboard.repositories.company.CompanyRepository;
import br.com.ottimizza.dashboard.repositories.kpi.KpiRepository;
import br.com.ottimizza.dashboard.repositories.kpi_detail.KpiDetailRepository;
import br.com.ottimizza.dashboard.utils.StringUtil;

@Service
public class CompanyService {

    @Inject
    private CompanyRepository repository;
    
    @Inject
    private KpiRepository kpiRepository;
    
    @Inject
    private KpiDetailRepository kpiDetailRepository;
    
    @Inject
    private OAuthClient oauthCliente;
    
    public Company save(Company company)throws Exception{
        return repository.save(company);
    }
    
    public Optional<Company> findById(BigInteger idCompany)throws Exception{
        return repository.findById(idCompany);
    }
    
    public List<Company> findByListCNPJ(List<String> cnpj)throws Exception{
        return repository.findCompaniesByCNPJ(cnpj);
    }
    
    public Company findByCnpj(String cnpj) throws Exception {
        return repository.findCompanyByCnpj(cnpj);
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
        
        List<Kpi> kpis = kpiRepository.findKpisByCNPJ(cnpjs);
        if(kpis.size() == 0) {
            response.put("status","Not found");
            response.put("message","sem registros para excluir!");
            return response;            	
        }
        
        try {
            for (Kpi kpi : kpis) {
            	// mudar delete details pra nativequery como pra melhor desempenho
            	List<KpiDetail> details = kpi.getKpiDetail();
            	for (KpiDetail kpiDetail : details) {
            		kpiDetailRepository.delete(kpiDetail);
				}
            	kpiRepository.delete(kpi);
            }

            response.put("status","Success");
            response.put("message","Excluído informações com sucesso!");
        } catch (Exception e) {
            response.put("status","Error");
            response.put("message","Houve um problema ao excluir!");
            return response;
        }
        return response;
    }

	public Company patch(CompanyDTO companyDTO, UserDTO userInfo) throws Exception {
		Company current = findByCnpj(StringUtil.formatCnpj(companyDTO.getCnpj()));
		current = companyDTO.patch(current);
		
		return repository.save(current);
	}
	
	public List<OrganizationDTO> findOrganizationInfo(String authorization, OrganizationDTO filter) throws Exception {
		String cnpj = StringUtils.leftPad(filter.getCnpj().replaceAll("\\D", ""), 14, "0");
		List<OrganizationDTO> dtos = oauthCliente.getOrganizationInfo(authorization, cnpj).getBody().getRecords();
		return dtos;
	}

	public List<CompanyDTO> findAll(CompanyDTO filter, String authorization) {
		// TODO Auto-generated method stub
		return null;
	}
    
}
