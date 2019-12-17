package br.com.ottimizza.dashboard.repositories.organizationVariable;

import java.math.BigInteger;
import java.util.List;

import br.com.ottimizza.dashboard.domain.dtos.UserDTO;
import br.com.ottimizza.dashboard.domain.dtos.VariableDTO;

public interface OrganizationVariableRepositoryCustom {

	List<VariableDTO> findVariablesByCompanyId(BigInteger companyId, UserDTO userInfo);

	List<VariableDTO> findMissingByCompanyId(BigInteger companyId, UserDTO userInfo);
	
//	OrganizationVariable saveOrganizationVariable(OrganizationVariable organizationVariable);


}
