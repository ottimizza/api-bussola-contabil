package br.com.ottimizza.dashboard.repositories.variable;

import java.math.BigInteger;
import java.util.List;

import br.com.ottimizza.dashboard.dtos.UserDTO;
import br.com.ottimizza.dashboard.dtos.VariableDTO;
import br.com.ottimizza.dashboard.models.Variable;

public interface VariableRepositoryCustom {
	
	Variable findVariableByAccountingCode(String accountingCode, UserDTO userInfo);

	List<Variable> findVariablesByOrganizationId(BigInteger organizationId, UserDTO userInfo);

}
