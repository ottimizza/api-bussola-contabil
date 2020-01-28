package br.com.ottimizza.dashboard.repositories.organizationVariable;

import java.util.List;

import br.com.ottimizza.dashboard.domain.dtos.UserDTO;
import br.com.ottimizza.dashboard.domain.dtos.VariableDTO;

public interface OrganizationVariableRepositoryCustom { //OrganizationVariableRepositoryImpl

	List<VariableDTO> findVariablesByCompanyId(VariableDTO filter, UserDTO userInfo);

	List<VariableDTO> findMissingByCompanyId(VariableDTO filter, UserDTO userInfo);

}
