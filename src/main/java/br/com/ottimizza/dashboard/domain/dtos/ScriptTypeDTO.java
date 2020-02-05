package br.com.ottimizza.dashboard.domain.dtos;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import br.com.ottimizza.dashboard.models.ScriptType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScriptTypeDTO {
	
	private BigInteger id;
	private BigInteger accounting;
	private String description;
	
	public static ScriptTypeDTO entityToDto(ScriptType entity) {

		ScriptTypeDTO dto = new ScriptTypeDTO();
		if(entity.getId() != null)			dto.setId(entity.getId());
		if(entity.getAccounting() != null)	dto.setAccounting(entity.getAccounting());
		if(entity.getDescription() != null)	dto.setDescription(entity.getDescription());
		
		return dto;
	}
	public static List<ScriptTypeDTO> entityToDto(List<ScriptType> entities){
		return entities.stream().map(ScriptTypeDTO::entityToDto).collect(Collectors.toList());
	}
	
	public static ScriptType dtoToEntity(ScriptTypeDTO dto) {

		ScriptType entity = new ScriptType();
		if(dto.getId() != null)				entity.setId(dto.getId());
		if(dto.getAccounting() != null)		entity.setAccounting(dto.getAccounting());
		if(dto.getDescription() != null)	entity.setDescription(dto.getDescription());
		
		return entity;
	}
	public static List<ScriptType> dtoToEntity(List<ScriptTypeDTO> dtos){
		return dtos.stream().map(ScriptTypeDTO::dtoToEntity).collect(Collectors.toList());
	}
	
	public ScriptType patch(ScriptType scriptType) {
		
		if (this.id != null)  			scriptType.setId(this.id);
		if (this.accounting != null)	scriptType.setAccounting(this.accounting);
		if (this.description != null)	scriptType.setDescription(this.description);
		return scriptType;
	}

}
