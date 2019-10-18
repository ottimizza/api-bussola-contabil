package br.com.ottimizza.dashboard.repositoriesorganizationVariable;

import java.math.BigInteger;
import java.util.List;

import br.com.ottimizza.dashboard.models.Variable;

public interface OrganizationVariableRepositoryCustom {

	List<Variable> findVariablesByOrganizationId(BigInteger organizationId);

}
