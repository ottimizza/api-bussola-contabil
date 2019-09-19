package br.com.ottimizza.dashboard.repositories;

import java.math.BigInteger;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.ottimizza.dashboard.models.Annotation;

@Repository
public interface AnnotationRepository extends JpaRepository<Annotation, BigInteger> {

	@Query(" SELECT a FROM Annotation a WHERE id = :id ")
	Annotation findAnnotationById(@Param("id") BigInteger id);

	List<Annotation> findByKpiAlias(String kpiAlias);

	@Modifying
	@Transactional
	@Query(" DELETE FROM Annotation WHERE id = :id ")
	void deleteAnnotationById(@Param("id") BigInteger id);

	
	@Query(" SELECT a FROM Annotation a WHERE organizationId = :organizationId AND kpiAlias = :kpiAlias order by createAt desc")
	List<Annotation> findAnnotationByCompanyAndKpiAlias(@Param("organizationId") String organizationId, @Param("kpiAlias") String kpiAlias);
	
}
