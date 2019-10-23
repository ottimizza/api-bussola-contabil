package br.com.ottimizza.dashboard.dtos;

import java.io.Serializable;
import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Data;
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
	
	
}
