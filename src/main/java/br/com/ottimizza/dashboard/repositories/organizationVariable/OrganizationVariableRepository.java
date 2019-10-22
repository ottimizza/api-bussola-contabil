package br.com.ottimizza.dashboard.repositories.organizationVariable;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ottimizza.dashboard.dtos.UserDTO;
import br.com.ottimizza.dashboard.dtos.VariableDTO;
import br.com.ottimizza.dashboard.models.OrganizationVariable;

@Repository
public interface OrganizationVariableRepository extends JpaRepository<OrganizationVariable, BigInteger>, OrganizationVariableRepositoryCustom {

//	List<VariableDTO> findVariablesByCompanyId(BigInteger companyId, UserDTO userInfo);


}
