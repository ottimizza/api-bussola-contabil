package br.com.ottimizza.dashboard.repositories.annotation;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ottimizza.dashboard.models.Annotation;

public interface AnnotationRepository extends JpaRepository<Annotation, BigInteger>{

	Optional<Annotation> findById(BigInteger annotationid);

}
