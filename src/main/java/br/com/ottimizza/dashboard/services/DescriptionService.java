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
import br.com.ottimizza.dashboard.domain.dtos.ScriptTypeDTO;
import br.com.ottimizza.dashboard.domain.mappers_description.DescriptionMapper;
import br.com.ottimizza.dashboard.models.Company;
import br.com.ottimizza.dashboard.models.Description;
import br.com.ottimizza.dashboard.models.ScriptType;
import br.com.ottimizza.dashboard.repositories.company.CompanyRepository;
import br.com.ottimizza.dashboard.repositories.description.DescriptionRepository;
import br.com.ottimizza.dashboard.repositories.script_type.ScriptTypeRepository;

@Service
public class DescriptionService {
	
	@Inject
	DescriptionRepository repository;

	@Inject
	CompanyRepository companyRepository;
	
	@Inject
	OAuthClient oauthClient;
	
	@Inject
    ScriptTypeRepository scriptTypeRepository;
	
	public DescriptionDTO save(DescriptionDTO descriptionDTO, String authorization) throws Exception {
		CompanyDTO filter = new CompanyDTO();
		filter.setAccountingId(descriptionDTO.getAccountingId());
				
		Company company = new Company();
		List<Company> companies = new ArrayList<Company>();
		if(descriptionDTO.getAccountingId() != null) companies = companyRepository.findAll(filter, null, null);
		DescriptionDTO dFiltro = new DescriptionDTO();

		if(!companies.isEmpty()) {
			// nao esta sendo tratado o array por que sempre vai ser chamado por cnpj (nao temos o accounting no OIC)
			company = companies.get(0);
		} else {
			try {
				filter = new CompanyDTO();
				filter.setCnpj(descriptionDTO.getCnpj());
				company = companyRepository.findAll(filter, null, null).get(0);
			} catch (Exception e) {	}
		}
		
		if (company.getId() != null) {
			if(company.getScriptId() 	 != null) descriptionDTO.setScriptId(company.getScriptId());
			if(company.getAccountingId() != null) descriptionDTO.setAccountingId(company.getAccountingId());
		
		} else { // se nao encontrar company busca organization(contabilidade) do account 
			OrganizationDTO organizationDto = new OrganizationDTO();
			
			List<OrganizationDTO> organizations = oauthClient.getOrganizationInfo(authorization, descriptionDTO.getCnpj().replaceAll("[^0-9]*", "")).getBody().getRecords();
			
			if(organizations.size() != 0) {
				organizationDto = organizations.get(0);
				if(organizationDto.getType() == 1) {
					descriptionDTO.setAccountingId(organizationDto.getId());
					descriptionDTO.setCnpj("");
				}
			}
			System.out.println(">>> A "+descriptionDTO.getKpiAlias()+" <> "+descriptionDTO.getScriptDescription());
			List<ScriptType> scripts = scriptTypeRepository.findAll(new ScriptTypeDTO(null, null, descriptionDTO.getDescription()));
			System.out.println(">>> B "+scripts.size());
			if(scripts.size() > 0) System.out.println(">>> C "+scripts.get(0).toString());
			if(descriptionDTO.getScriptDescription() != null) {
				if(scripts.size() > 0)		 descriptionDTO.setScriptId(scripts.get(0).getId());
				else if(scripts.size() == 0) descriptionDTO.setScriptId(scriptTypeRepository.save(new ScriptType(null, descriptionDTO.getAccountingId(), descriptionDTO.getScriptDescription())).getId());
			} else {
				scripts = scriptTypeRepository.findAll(new ScriptTypeDTO(null, null, "PADRAO"));
				if(scripts.size() > 0)		 descriptionDTO.setScriptId(scripts.get(0).getId());
				else if(scripts.size() == 0) descriptionDTO.setScriptId(scriptTypeRepository.save(new ScriptType(null, descriptionDTO.getAccountingId(), "PADRAO")).getId());
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
