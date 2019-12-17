package br.com.ottimizza.dashboard.services;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.persistence.NoResultException;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import br.com.ottimizza.dashboard.domain.dtos.KpiTitleAndValueDTO;
import br.com.ottimizza.dashboard.models.Company;
import br.com.ottimizza.dashboard.models.Kpi;
import br.com.ottimizza.dashboard.models.KpiShort;
import br.com.ottimizza.dashboard.repositories.company.CompanyRepository;
import br.com.ottimizza.dashboard.repositories.kpi.KpiRepository;
import br.com.ottimizza.dashboard.repositories.kpi.kpi_short.KpiShortRepository;

@Service
public class KpiService {

    @Inject
    private KpiRepository repository;

    @Inject
    private KpiShortRepository kpiShortRepository;
    
    @Inject
    private CompanyRepository companyRepository;

    
    public Kpi save(Kpi kpi) throws Exception {
        return repository.save(kpi);
    }

    public Optional<Kpi> findById(BigInteger idKpi) throws Exception {
        return repository.findById(idKpi);
    }

    public List<KpiShort> findByListCNPJ(List<String> cnpj) throws Exception {
        return kpiShortRepository.findKpisByCNPJ(cnpj);
    }

    public JSONObject updateById(BigInteger idKpi, Kpi kpi) throws NoResultException, Exception {
        JSONObject response = new JSONObject();
        try {
        	
            kpi.setId(repository.findById(idKpi).get().getId());
            repository.save(kpi);

            response.put("status", "sucess");
            response.put("message", "Atualizado kpi com sucesso!");

        } catch (NoResultException e0) {
            response.put("status", "error");
            response.put("message", "Problema ao encontrar a kpi!");
            throw new NoResultException(response.toString());
        } catch (Exception e1) {
            response.put("status", "Error");
            response.put("message", "Houve um problema ao atualizar!");
            throw new Exception(response.toString());
        }
        return response;
    }

	public JSONObject delete(BigInteger idKpi) throws Exception {
        JSONObject response = new JSONObject();
        try {
            repository.deleteById(idKpi);
            response.put("status", "sucess");
            response.put("message", "Excluído kpi com sucesso!");
        } catch (Exception e) {
            response.put("status", "Error");
            response.put("message", "Houve um problema ao excluir!");
            return response;
        }
        return response;
    }
	
	public Kpi createKpi(BigInteger companyId, Kpi kpi) {
		Kpi newKpi = new Kpi();
		Company c = new Company();
		Optional<Company> optionalCompany = companyRepository.findById(companyId);

		try {
			c = optionalCompany.get();
			newKpi.setCompany(c);
			newKpi.setTitle(kpi.getTitle());
			newKpi.setGraphType(kpi.getGraphType());
			newKpi.setColumnX0Label(kpi.getColumnX0Label());
			newKpi.setLabel(kpi.getLabel());
		
			newKpi.setKpiAlias(kpi.getKpiAlias());
			newKpi.setSubtitle(kpi.getSubtitle());
			newKpi.setDescription(kpi.getDescription());
			newKpi.setVisible(kpi.getVisible());
			newKpi.setLabel2(kpi.getLabel2());
			newKpi.setLabel3(kpi.getLabel3());
			newKpi.setLabel4(kpi.getLabel4());
		} catch (Exception e) { 
			//new NoSuchElementException(); 
		}
		return repository.save(newKpi);	
	}

	public KpiTitleAndValueDTO kpiValue(BigInteger companyId) throws Exception {

		KpiTitleAndValueDTO response = repository.findKpiDTOByCompanyId(companyId);
		response.setKpiAlias("07");
		return response;
	}


}
