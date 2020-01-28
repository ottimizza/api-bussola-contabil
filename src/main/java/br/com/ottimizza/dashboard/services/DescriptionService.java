package br.com.ottimizza.dashboard.services;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ottimizza.dashboard.domain.dtos.CompanyDTO;
import br.com.ottimizza.dashboard.domain.dtos.DescriptionDTO;
import br.com.ottimizza.dashboard.domain.mappers_description.DescriptionMapper;
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
		CompanyDTO filter = new CompanyDTO(null, null, null, null, descriptionDTO.getAccountingId(), null, null, null);
		Company company = new Company();
		List<Company> companies = companyRepository.findAll(filter, null, null);

		if(!companies.isEmpty()) {
			company = companies.get(0);
		} else {
			try {
				filter = new CompanyDTO(null, descriptionDTO.getCnpj(), null, null, null, null, null, null);
				company = companyRepository.findAll(filter, null, null).get(0);
//				if(company != null) {
//					company.setExternalId(descriptionDTO.getAccountingId());
//					company = companyRepository.save(company);
//				}
			} catch (Exception e) {	}
		}
		if (company != null && company.getAccountingId() == null) {
			
//			descriptionDTO.setAccountingId(company.getAccountingId());
			// cria tipo roteiro padrao
			// gravar fk na company
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

	@Transactional(rollbackFor = Exception.class)
	public List<DescriptionDTO> saveDescriptionList(DescriptionDTO descriptionDTO) throws Exception {
		List<Description> resultados = new ArrayList<>();
		List<Description> descriptions = descriptionDTO.getDescriptions().stream().map((o) -> {
			return DescriptionMapper.fromDto(o).toBuilder()
				.build();
		}).collect(Collectors.toList());
		
		repository.saveAll(descriptions).forEach(resultados::add);
		
		return DescriptionMapper.fromEntities(resultados);
	}	

	public Page<DescriptionDTO> returnDescriptionList(DescriptionDTO filter, int pageIndex, int pageSize, String authorization) {
		return repository.findByAccountingIdScriptType(filter, PageRequest.of(pageIndex, pageSize)).map(DescriptionDTO::descriptionToDto);
	}

	public DescriptionDTO updateByOrganizationIdScriptType(DescriptionDTO descriptionDTO){
		Description dp = repository.findByAccountingIdScriptType(descriptionDTO);
		return DescriptionDTO.descriptionToDto(repository.save(descriptionDTO.patch(dp)));
	}

}
