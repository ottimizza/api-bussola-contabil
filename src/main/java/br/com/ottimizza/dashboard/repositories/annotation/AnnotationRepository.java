package br.com.ottimizza.dashboard.repositories.annotation;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.ottimizza.dashboard.models.Annotation;

public interface AnnotationRepository extends JpaRepository<Annotation, BigInteger>, AnnotationRepositoryCustom {

	@Query(" SELECT a FROM Annotation a WHERE id = :id ")
	Annotation findAnnotationById(@Param("id") BigInteger id);

	Annotation findByKpiAlias(String kpiAlias);

	@Query(" DELETE a FROM Annotation a WHERE id = :id ")
	Annotation deleteAnnotationById(@Param("id") BigInteger id);

}
