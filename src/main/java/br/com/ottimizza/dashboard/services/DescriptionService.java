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

import br.com.ottimizza.dashboard.client.OAuthClient;
import br.com.ottimizza.dashboard.domain.dtos.CompanyDTO;
import br.com.ottimizza.dashboard.domain.dtos.DescriptionDTO;
import br.com.ottimizza.dashboard.domain.dtos.OrganizationDTO;
import br.com.ottimizza.dashboard.domain.dtos.UserDTO;
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
	
	@Inject
	OAuthClient oauthClient;
	
	
	public DescriptionDTO save(DescriptionDTO descriptionDTO, String authorization) throws Exception {
		CompanyDTO filter = new CompanyDTO();
		filter.setAccountingId(descriptionDTO.getAccountingId());
		
		Company company = new Company();
		List<Company> companies = new ArrayList<Company>();
		if(descriptionDTO.getAccountingId() != null) companies = companyRepository.findAll(filter, null, null);
		DescriptionDTO dFiltro = new DescriptionDTO();

		if(!companies.isEmpty()) {
			company = companies.get(0);
		} else {
			try {
				filter = new CompanyDTO();
				filter.setCnpj(descriptionDTO.getCnpj());
				company = companyRepository.findAll(filter, null, null).get(0);
			} catch (Exception e) {	}
		}
		System.out.println(">>> C nao"+ descriptionDTO.getCnpj());
		if (company != null) {	
			System.out.println(">>> C nao"+ company.getName());
			if(company.getScriptId() 	 != null) descriptionDTO.setScriptId(company.getScriptId());
			if(company.getAccountingId() != null) descriptionDTO.setAccountingId(company.getAccountingId());
		
		} else { // se nao encontrar company busca organization(contabilidade) do account 
			System.out.println(">>> C sim ");
			OrganizationDTO organizationDto = oauthClient.getOrganizationInfo(authorization, descriptionDTO.getCnpj().replaceAll("[^0-9]*", "")).getBody().getRecord();
			System.out.println(">>> D " + organizationDto.getName()+" - "+ organizationDto.getType()+" - " + organizationDto.getId());
			if(organizationDto.getType() == 1) {
				descriptionDTO.setAccountingId(organizationDto.getId());
				descriptionDTO.setCnpj("");
				descriptionDTO.setScriptId(null);
				System.out.println(">>> E "+descriptionDTO.getTitle()+" - "+descriptionDTO.getCnpj()+" - "+descriptionDTO.getScriptId()+" - "+descriptionDTO.getCnpj());
			}
		}
		
		if(descriptionDTO.getAccountingId() != null && descriptionDTO.getKpiAlias() != null && descriptionDTO.getScriptId() != null) {

			dFiltro.setAccountingId(descriptionDTO.getAccountingId());
			dFiltro.setScriptId(descriptionDTO.getScriptId());
			dFiltro.setKpiAlias(descriptionDTO.getKpiAlias());

			try {
				Description description = repository.findAll(dFiltro).get(0);
				if (description != null) descriptionDTO.setId(description.getId());
			} catch (Exception e) { }
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
	public List<DescriptionDTO> updateDescriptionList(DescriptionDTO descriptionDTO) throws Exception {
		List<DescriptionDTO> ListDesc = new ArrayList<>();

		for(DescriptionDTO d: descriptionDTO.getDescriptions()){
//			DescriptionDTO filterD = new DescriptionDTO(null, d.getAccountingId(), d.getKpiAlias(), null, null, d.getScriptId(), null, null, null, null, null);
//			Description dFilter = repository.findByAccountingIdScriptType(filterD);
//			d.setId(dFilter.getId());
			
			ListDesc.add(d);
		} 
		List<Description> resultados = new ArrayList<>();
		List<Description> descriptions = ListDesc.stream().map((o) -> {
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
