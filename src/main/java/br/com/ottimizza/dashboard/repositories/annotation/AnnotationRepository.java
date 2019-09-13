package br.com.ottimizza.dashboard.repositories.annotation;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.ottimizza.dashboard.models.Annotation;

@Repository
public interface AnnotationRepository extends JpaRepository<Annotation, BigInteger>, AnnotationRepositoryCustom {

	// nao mexer
	@Query(" SELECT a FROM Annotation a WHERE id = :id ")
	Annotation findAnnotationById(@Param("id") BigInteger id);

	Annotation findByKpiAlias(String kpiAlias);

}
