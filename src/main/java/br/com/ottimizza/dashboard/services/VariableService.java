package br.com.ottimizza.dashboard.services;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.persistence.NoResultException;

import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.ottimizza.dashboard.domain.dtos.UserDTO;
import br.com.ottimizza.dashboard.domain.dtos.VariableDTO;
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
				if (variable.getScriptId() != null)		newVariable.setScriptId(variable.getScriptId());
				if (variable.getAccountingId() != null)	newVariable.setAccountingId(variable.getAccountingId());
				if (variable.getDescription() != null)	newVariable.setDescription(variable.getDescription());
				if (variable.getVariableCode() != null)	newVariable.setVariableCode(variable.getVariableCode());
				if (variable.getName() != null)			newVariable.setName(variable.getName());
				if (variable.getAccountingCode() != null) newVariable.setAccountingCode(variable.getAccountingCode());
				
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

	public List<Variable> findVariableByOrganizationId(BigInteger organizationId, UserDTO userInfo) {
		return repository.findVariablesByOrganizationId(organizationId, userInfo);
	}

	public Variable upsert(Variable variable) throws Exception {
		Variable var = new Variable();
		try{ var = repository.findById(variable.getId()).orElse(null); }
		catch (Exception e) { }
		
		if(variable.getId() != null && var != null) {
			
			var.setDescription((variable.getDescription() != null) ? variable.getDescription() : "");
			var.setScriptId((variable.getScriptId() != null) ? variable.getScriptId() : null);
			var.setAccountingCode((variable.getAccountingCode() != null) ? variable.getAccountingCode() : "");

			if(variable.getAccountingId() != null)	var.setAccountingId(variable.getAccountingId());
			if(variable.getVariableCode() != null)	var.setVariableCode(variable.getVariableCode());
			if(variable.getName() != null)			var.setName(variable.getName());
			if(variable.getScriptId() != null) 		var.setScriptId(variable.getScriptId());
			if(variable.getOriginValue() != null) 	var.setOriginValue(variable.getOriginValue());
			var.setAbsoluteValue(variable.isAbsoluteValue());

			return repository.save(var);
		}
		return repository.save(variable);
	}

	public Page<Variable> findVariableByOrganization(VariableDTO filter, int pageIndex, int pageSize, UserDTO userInfo) {
		return repository.findVariableByOrganization(filter, PageRequest.of(pageIndex, pageSize));
	}
	

}
