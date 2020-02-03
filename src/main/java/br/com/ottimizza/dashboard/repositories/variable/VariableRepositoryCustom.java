package br.com.ottimizza.dashboard.repositories.variable;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.ottimizza.dashboard.domain.dtos.UserDTO;
import br.com.ottimizza.dashboard.domain.dtos.VariableDTO;
import br.com.ottimizza.dashboard.models.Variable;

public interface VariableRepositoryCustom { // VariableRepositoryImpl
	
	Variable findVariableByAccountingCode(String accountingCode, UserDTO userInfo);

	List<Variable> findVariablesByOrganizationId(BigInteger organizationId, UserDTO userInfo);

	Page<Variable> findVariableByOrganization(VariableDTO filter, Pageable pageable);
	
	Variable findByAccountIdKpiAliasScriptId(VariableDTO variableDto);

}
