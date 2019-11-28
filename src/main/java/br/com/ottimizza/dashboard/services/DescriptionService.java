package br.com.ottimizza.dashboard.services;

import java.math.BigInteger;
import java.util.List;

import javax.inject.Inject;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import br.com.ottimizza.dashboard.dtos.DescriptionDTO;
import br.com.ottimizza.dashboard.models.Description;
import br.com.ottimizza.dashboard.repositories.description.DescriptionRepository;

@Service
public class DescriptionService {
	
	@Inject
	DescriptionRepository repository;
	
	@Inject
	DescriptionDTO dto;
	
	//*******************************
	//receber e devolver somente DTO
	//*******************************
	
	
	
	public DescriptionDTO save(DescriptionDTO descriptionDTO) throws Exception {
		Description description = dto.dtoToDescription(descriptionDTO);
		return dto.descriptionToDto(repository.save(description));
	}
	
	public JSONObject delete(BigInteger descriptionId) {
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
	
	public List<DescriptionDTO> findAll(DescriptionDTO descriptionDto, String authorization) {
		return dto.descriptionToDto(repository.findAll(descriptionDto));
	}


}
