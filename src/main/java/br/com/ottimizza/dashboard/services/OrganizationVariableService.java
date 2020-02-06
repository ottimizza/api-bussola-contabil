package br.com.ottimizza.dashboard.services;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.persistence.NoResultException;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import br.com.ottimizza.dashboard.domain.dtos.UserDTO;
import br.com.ottimizza.dashboard.domain.dtos.VariableDTO;
import br.com.ottimizza.dashboard.models.OrganizationVariable;
import br.com.ottimizza.dashboard.models.Variable;
import br.com.ottimizza.dashboard.repositories.organizationVariable.OrganizationVariableRepository;
import br.com.ottimizza.dashboard.repositories.variable.VariableRepository;

@Service
public class OrganizationVariableService {

	@Inject
	OrganizationVariableRepository repository;
	
	@Inject
	VariableRepository variableRepository;
	
	
	public VariableDTO save(VariableDTO variableDto, UserDTO userInfo) throws Exception {
		System.out.println(">>> s1 "+variableDto.toString());
		OrganizationVariable organizationVariable = VariableDTO.variableDtoToOrganizationVariable(variableDto);

		VariableDTO filter = new VariableDTO();
		filter.setCompanyId(variableDto.getCompanyId());
		filter.setScriptId(variableDto.getScriptId());
		filter.setVariableCode(variableDto.getVariableCode());
		
		System.out.println(">>> s2 "+filter.toString());
		
		List<OrganizationVariable> orgVariables = repository.findOrganizationVariable(filter, userInfo);

		if(orgVariables.size() == 0) {
			filter = new VariableDTO();
			filter.setAccountingCode(variableDto.getAccountingCode());
			filter.setAccountingId(variableDto.getAccountingId());
			
//			List<Variable> existantVariable = variableRepository.findAll(filter, userInfo);
			
//			 if(existantVariable.size() == 0) {
				System.out.println(">>> s3d "+organizationVariable.toString());
				organizationVariable = repository.save(organizationVariable);
				System.out.println(">>> s3e "+organizationVariable.toString());
				return VariableDTO.organizationVariableToVariableDto(organizationVariable);
//			}
			
		}else if(orgVariables.size() > 0) {
			System.out.println(">>> s4a "+orgVariables.get(0).toString());
			organizationVariable.setId(orgVariables.get(0).getId());
			organizationVariable = repository.save(organizationVariable);
			return VariableDTO.organizationVariableToVariableDto(organizationVariable);
		}

		return null;
	}
	
	public Optional<OrganizationVariable> findById(BigInteger id) throws Exception {
		return repository.findById(id);
	}
	
	public List<OrganizationVariable> findAll(){
		return repository.findAll();
	}
	
	public JSONObject delete(BigInteger id) throws Exception {
		JSONObject response = new JSONObject();
		try {
            repository.deleteById(id);
            response.put("status", "Success");
            response.put("message", "Excluído com sucesso!");
        } catch (Exception e) {
            response.put("status", "Error");
            response.put("message", "Houve um problema ao excluir!");
            return response;
        }
		return response;
	}

	public JSONObject updateById(OrganizationVariable organizationVariable) throws NoResultException, Exception {
		JSONObject response = new JSONObject();
		try {
			OrganizationVariable newOrgVariable = new OrganizationVariable();
			newOrgVariable = repository.findById(organizationVariable.getId()).get();
			try {
				if (organizationVariable.getAccountingCode() != null) newOrgVariable.setAccountingCode(organizationVariable.getAccountingCode());
				if (organizationVariable.getOrganizationId() != null) newOrgVariable.setOrganizationId(organizationVariable.getOrganizationId());
				if (organizationVariable.getVariableId() != null) 	  newOrgVariable.setVariableId(organizationVariable.getVariableId());
				
				repository.save(newOrgVariable);
				
				response.put("status", "Success");
				response.put("message", "Atualizada com sucesso!");
			} catch (Exception e) {
				response.put("status", "Error");
				response.put("message", "Houve um problema ao atualizar!");
				throw new Exception(response.toString());
			}
		} catch (Exception e) {
			response.put("status", "Error");
			response.put("message", "Problema ao encontrar a variável!");
			throw new NoResultException(response.toString());
		}
		return response;
	}
	
	public List<VariableDTO> findVariableByCompanyId(VariableDTO filter, UserDTO userInfo) {
		return repository.findVariablesByCompanyId(filter, userInfo);
	}
	
	public List<VariableDTO> findMissingByOrganizationId(VariableDTO filter, UserDTO userInfo) {
		return repository.findMissingByCompanyId(filter, userInfo);
	}
	
}