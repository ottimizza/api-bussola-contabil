package br.com.ottimizza.dashboard.services;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.persistence.NoResultException;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import br.com.ottimizza.dashboard.dtos.UserDTO;
import br.com.ottimizza.dashboard.dtos.VariableDTO;
import br.com.ottimizza.dashboard.models.Variable;
import br.com.ottimizza.dashboard.repositories.variable.VariableRepository;

@Service
public class VariableService {

	@Inject
	VariableRepository repository;

	public Variable save(Variable variable) throws Exception {
		return repository.save(variable);
	}

	public Optional<Variable> findById(BigInteger id) throws Exception {
		return repository.findById(id);
	}

	public List<Variable> findAll(){
		return repository.findAll();
	}
	
	public JSONObject updateById(Variable variable) throws NoResultException, Exception {
		JSONObject response = new JSONObject();
		try {
			Variable newVariable = new Variable();
			newVariable = repository.findById(variable.getId()).get();
			try {
				if (variable.getAccountingCode() != null) newVariable.setAccountingCode(variable.getAccountingCode());
				if (variable.getAccountingId() != null) 	  newVariable.setAccountingId(variable.getAccountingId());
				if (variable.getDescription() != null)	  newVariable.setDescription(variable.getDescription());
				if (variable.getExternalId() != null)	  newVariable.setExternalId(variable.getExternalId());
				if (variable.getName() != null) 		  newVariable.setName(variable.getName());

				repository.save(newVariable);
				
				response.put("status", "sucess");
				response.put("message", "Variável atualizada com sucesso!");
			} catch (Exception e) {
				response.put("status", "Error");
				response.put("message", "Houve um problema ao atualizar!");
				throw new Exception(response.toString());
			}
		} catch (Exception e) {
			response.put("status", "error");
			response.put("message", "Problema ao encontrar a variável!");
			throw new NoResultException(response.toString());
		}
		return response;
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

	public List<VariableDTO> findVariableByOrganizationId(BigInteger organizationId, UserDTO userInfo) {
		return repository.findVariablesByOrganizationId(organizationId, userInfo);
	}

}
