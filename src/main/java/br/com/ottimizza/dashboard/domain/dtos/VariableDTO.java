package br.com.ottimizza.dashboard.domain.dtos;

import java.io.Serializable;
import java.math.BigInteger;

import br.com.ottimizza.dashboard.models.Variable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VariableDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	// from Variables
	
	private BigInteger id;				//empresa
	
	private BigInteger companyId;		//empresa
	private String variableCode;
	private String  name;
	
	private BigInteger variableId;

	//from script
	private BigInteger scriptId;
	
	//from organizationVariable
	private BigInteger accountingId;	//contabilidade  accounting
	
	public Variable variableDtoToVariable(VariableDTO variableDto) {
		Variable variable = new Variable();

		if(variableDto.getId() != null)				variable.setId(variableDto.getId());
		if(variableDto.getVariableCode() != null)	variable.setVariableCode(variableDto.getVariableCode());
		if(variableDto.getName() != null)			variable.setName(variableDto.getName());
		if(variableDto.getScriptId() != null)		variable.setScriptId(variableDto.getScriptId());
		
		return variable;
	}

	public VariableDTO variableToVariableDto(Variable variable) {
		VariableDTO variableDto = new VariableDTO();
		
		if(variable.getId() != null)			variableDto.setId(variable.getId());
		if(variable.getVariableCode() != null)	variableDto.setVariableCode(variable.getVariableCode());
		if(variable.getName() != null)			variableDto.setName(variable.getName());
		if(variable.getScriptId() != null)		variableDto.setScriptId(variable.getScriptId());

		return variableDto;
	}
}
