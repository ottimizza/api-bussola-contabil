package br.com.ottimizza.dashboard.repositories.annotation;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ottimizza.dashboard.models.Annotation;

@Repository
public interface AnnotationRepository extends JpaRepository<Annotation, BigInteger>{

//	Optional<Annotation> findById(BigInteger annotationid);

}
