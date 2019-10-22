package br.com.ottimizza.dashboard.repositories.organizationVariable;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ottimizza.dashboard.models.OrganizationVariable;


public interface OrganizationVariableRepository extends JpaRepository<OrganizationVariable, BigInteger>, OrganizationVariableRepositoryCustom {



}
