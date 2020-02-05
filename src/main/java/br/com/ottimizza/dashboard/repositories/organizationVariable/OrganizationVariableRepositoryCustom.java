package br.com.ottimizza.dashboard.repositories.organizationVariable;

import java.util.List;

import br.com.ottimizza.dashboard.domain.dtos.UserDTO;
import br.com.ottimizza.dashboard.domain.dtos.VariableDTO;
import br.com.ottimizza.dashboard.models.OrganizationVariable;

public interface OrganizationVariableRepositoryCustom { //OrganizationVariableRepositoryImpl

	List<VariableDTO> findVariablesByCompanyId(VariableDTO filter, UserDTO userInfo);

	List<VariableDTO> findMissingByCompanyId(VariableDTO filter, UserDTO userInfo);
	
	List<OrganizationVariable> findOrganizationVariable(VariableDTO filter, UserDTO userInfo);


}
