package br.com.ottimizza.dashboard.dtos;

import java.math.BigInteger;

import lombok.Data;

@Data
public class VariableDTO {

	private BigInteger id;

	// from Variables
	private BigInteger companyId;		//empresa
	private String externalId;
	private String  name;
	
	private BigInteger variableId;		//

	//from organizationVariable
	private BigInteger organizationId;	//contabilidade
	private String  accountingCode;


}
