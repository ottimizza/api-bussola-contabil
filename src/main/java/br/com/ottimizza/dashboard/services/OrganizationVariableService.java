package br.com.ottimizza.dashboard.services;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.persistence.NoResultException;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import br.com.ottimizza.dashboard.models.OrganizationVariable;
import br.com.ottimizza.dashboard.models.Variable;
import br.com.ottimizza.dashboard.repositories.VariableRepository;
import br.com.ottimizza.dashboard.repositoriesorganizationVariable.OrganizationVariableRepository;

@Service
public class OrganizationVariableService {

	@Inject
	OrganizationVariableRepository repository;
	
	public OrganizationVariable save(OrganizationVariable organizationVariable) throws Exception {
		return repository.save(organizationVariable);
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

	public List<Variable> findVariableByOrganizationId(BigInteger organizationId) {
		return repository.findVariablesByOrganizationId(organizationId);
	}
}