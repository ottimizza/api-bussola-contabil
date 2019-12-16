package br.com.ottimizza.dashboard.domain.dtos;

import java.io.Serializable;
import java.math.BigInteger;

import br.com.ottimizza.dashboard.models.Variable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class VariableDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	// from Variables
	
	private BigInteger id;		//empresa
	
	private BigInteger companyId;		//empresa
	private String externalId;
	private String  name;
	
	private BigInteger variableId;		//

	//from organizationVariable
	private BigInteger organizationId;	//contabilidade
	private String  accountingCode;
	
	public Variable variableDtoToVariable(VariableDTO variableDto) {
		Variable variable = new Variable();

		if(variableDto.getId() != null)				variable.setId(variableDto.getId());
		if(variableDto.getExternalId() != null)		variable.setExternalId(variableDto.getExternalId());
		if(variableDto.getName() != null)			variable.setName(variableDto.getName());
		if(variableDto.getAccountingCode() != null)	variable.setAccountingCode(variableDto.getAccountingCode());
		
		return variable;
	}

	public VariableDTO variableToVariableDto(Variable variable) {
		VariableDTO variableDto = new VariableDTO();
		
		if(variable.getId() != null)	variableDto.setId(variable.getId());
		if(variable.getExternalId() != null)	variableDto.setExternalId(variable.getExternalId());
		if(variable.getName() != null)	variableDto.setName(variable.getName());
		if(variable.getAccountingCode() != null)	variableDto.setAccountingCode(variable.getAccountingCode());

		return variableDto;
	}
}
