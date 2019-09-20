package br.com.ottimizza.dashboard.repositories.annotation;

import java.util.List;

import br.com.ottimizza.dashboard.dtos.AnnotationDTO;
import br.com.ottimizza.dashboard.models.Annotation;

public interface AnnotationRepositoryCustom {

	List<Annotation> findAnnotations(AnnotationDTO annotation);
}
