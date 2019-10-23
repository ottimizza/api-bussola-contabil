package br.com.ottimizza.dashboard.repositories.organizationVariable;

import java.math.BigInteger;
import java.util.List;

import br.com.ottimizza.dashboard.dtos.UserDTO;
import br.com.ottimizza.dashboard.dtos.VariableDTO;

public interface OrganizationVariableRepositoryCustom {

	List<VariableDTO> findVariablesByOrganizationId(BigInteger organizationId, UserDTO userInfo);

	List<VariableDTO> findVariablesByCompanyId(BigInteger companyId, UserDTO userInfo);

	List<VariableDTO> findMissingByOrganizationId(BigInteger companyId, UserDTO userInfo);
	
//	OrganizationVariable saveOrganizationVariable(OrganizationVariable organizationVariable);


}
