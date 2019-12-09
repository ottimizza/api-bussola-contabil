package br.com.ottimizza.dashboard.repositories.script_type;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.ottimizza.dashboard.models.ScriptType;

@Repository
public interface ScriptTypeRepository extends PagingAndSortingRepository<ScriptType, BigInteger>, ScriptTypeRepositoryCustom {

	Optional<ScriptType> findById(BigInteger id);

}
