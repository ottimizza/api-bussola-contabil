package br.com.ottimizza.dashboard.repositories.variable;

import java.math.BigInteger;
import java.util.List;

import br.com.ottimizza.dashboard.domain.dtos.UserDTO;
import br.com.ottimizza.dashboard.models.Variable;

public interface VariableRepositoryCustom {
//	VariableRepositoryImpl
	Variable findVariableByAccountingCode(String accountingCode, UserDTO userInfo);

	List<Variable> findVariablesByOrganizationId(BigInteger organizationId, UserDTO userInfo);

}
