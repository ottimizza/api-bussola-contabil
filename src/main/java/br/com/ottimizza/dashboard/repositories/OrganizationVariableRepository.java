package br.com.ottimizza.dashboard.repositories;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ottimizza.dashboard.models.OrganizationVariable;

@Repository
public interface OrganizationVariableRepository extends JpaRepository<OrganizationVariable, BigInteger>{

}
