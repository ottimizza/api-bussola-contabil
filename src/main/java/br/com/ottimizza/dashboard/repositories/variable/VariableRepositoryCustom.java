package br.com.ottimizza.dashboard.repositories.variable;

import br.com.ottimizza.dashboard.dtos.UserDTO;
import br.com.ottimizza.dashboard.models.Variable;

public interface VariableRepositoryCustom {
	
	Variable findVariableByAccountingCode(String accountingCode, UserDTO userInfo);

}
