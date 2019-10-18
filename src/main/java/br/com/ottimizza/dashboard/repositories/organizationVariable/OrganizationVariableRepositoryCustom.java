package br.com.ottimizza.dashboard.repositories.organizationVariable;

import java.math.BigInteger;
import java.security.Principal;
import java.util.List;

import br.com.ottimizza.dashboard.dtos.VariableDTO;
import br.com.ottimizza.dashboard.models.Variable;

public interface OrganizationVariableRepositoryCustom {

	List<VariableDTO> findVariablesByOrganizationId(BigInteger organizationId, Principal principal);

}
