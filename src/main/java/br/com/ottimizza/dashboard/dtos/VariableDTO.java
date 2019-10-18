package br.com.ottimizza.dashboard.dtos;

import java.io.Serializable;
import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VariableDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	// from Variables
	private BigInteger companyId;		//empresa
	private String externalId;
	private String  name;
	
	private BigInteger variableId;		//

	//from organizationVariable
	private BigInteger organizationId;	//contabilidade
	private String  accountingCode;


}
