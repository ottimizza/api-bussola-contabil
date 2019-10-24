package br.com.ottimizza.dashboard.repositories.variable;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ottimizza.dashboard.models.Variable;

@Repository
public interface VariableRepository extends JpaRepository<Variable, BigInteger>, VariableRepositoryCustom{


}
