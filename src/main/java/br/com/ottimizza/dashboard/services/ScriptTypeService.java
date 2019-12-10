package br.com.ottimizza.dashboard.services;

import java.math.BigInteger;
import java.util.List;

import javax.inject.Inject;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import br.com.ottimizza.dashboard.dtos.ScriptTypeDTO;
import br.com.ottimizza.dashboard.models.ScriptType;
import br.com.ottimizza.dashboard.repositories.script_type.ScriptTypeRepository;

@Service
public class ScriptTypeService {
	
	@Inject
	ScriptTypeRepository repository;

	public ScriptTypeDTO save(ScriptTypeDTO dto) throws Exception {
		ScriptType entity = ScriptTypeDTO.dtoToEntity(dto);
		return ScriptTypeDTO.entityToDto(repository.save(entity));
	}
	
	public JSONObject delete(BigInteger id) throws Exception {
		JSONObject response = new JSONObject();
        try {
            repository.deleteById(id);
            response.put("status", "Success");
            response.put("message", "Roteiro excluído com sucesso!");
        } catch (Exception e) {
            response.put("status", "Error");
            response.put("message", "Houve um problema ao excluir!");
            return response;
        }
        return response;
	}
	
	public ScriptType findById(BigInteger id) {
		return repository.findById(id).orElse(null);
	}

	public List<ScriptTypeDTO> findAll(ScriptTypeDTO dto) {
		return ScriptTypeDTO.entityToDto(repository.findAll(dto));
	}

	public ScriptTypeDTO patch(BigInteger id, ScriptTypeDTO dto) throws Exception {
		ScriptType current = findById(id);
		return ScriptTypeDTO.entityToDto(repository.save(dto.patch(current)));
	}
}
