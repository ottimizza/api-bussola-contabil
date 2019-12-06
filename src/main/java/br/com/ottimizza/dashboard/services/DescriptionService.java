package br.com.ottimizza.dashboard.services;

import java.math.BigInteger;
import java.util.List;

import javax.inject.Inject;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import br.com.ottimizza.dashboard.dtos.CompanyDTO;
import br.com.ottimizza.dashboard.dtos.DescriptionDTO;
import br.com.ottimizza.dashboard.models.Company;
import br.com.ottimizza.dashboard.models.Description;
import br.com.ottimizza.dashboard.repositories.company.CompanyRepository;
import br.com.ottimizza.dashboard.repositories.description.DescriptionRepository;

@Service
public class DescriptionService {
	
	@Inject
	DescriptionRepository repository;

	@Inject
	CompanyRepository companyRepository;
	
	public DescriptionDTO save(DescriptionDTO descriptionDTO) throws Exception {
		CompanyDTO filter = new CompanyDTO(null, descriptionDTO.getOrganizationId(), null);
		Company company = new Company();
		List<Company> companies = companyRepository.findAll(filter, null, null);
		
		if(companies != null) {
			company = companies.get(0);
		} else {
			try {
				filter = new CompanyDTO(descriptionDTO.getCnpj(), null, null);
				company = companyRepository.findAll(filter, null, null).get(0);
				if(company != null) {
					company.setOrganizationId(descriptionDTO.getOrganizationId());
					companyRepository.save(company);
				}
			} catch (Exception e) {	}
		}

		Description description = DescriptionDTO.dtoToDescription(descriptionDTO);
		return DescriptionDTO.descriptionToDto(repository.save(description));
	}
	
	public JSONObject delete(BigInteger descriptionId) throws Exception {
		JSONObject response = new JSONObject();
        try {
            repository.deleteById(descriptionId);
            response.put("status", "success");
            response.put("message", "Descrição excluída com sucesso!");
        } catch (Exception e) {
            response.put("status", "Error");
            response.put("message", "Houve um problema ao excluir!");
            return response;
        }
        return response;
	}
	
	public Description findById(BigInteger id) {
		return repository.findById(id).orElse(null);
	}

	public List<DescriptionDTO> findAll(DescriptionDTO descriptionDto) {
		return DescriptionDTO.descriptionToDto(repository.findAll(descriptionDto));
	}

	public DescriptionDTO patch(BigInteger id, DescriptionDTO descriptionDTO) throws Exception {
		Description current = findById(id);
		return DescriptionDTO.descriptionToDto(repository.save(descriptionDTO.patch(current)));
	}
	

}
