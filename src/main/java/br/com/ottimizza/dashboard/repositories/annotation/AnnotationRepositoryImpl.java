package br.com.ottimizza.dashboard.repositories.annotation;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQuery;

import br.com.ottimizza.dashboard.dtos.AnnotationDTO;
import br.com.ottimizza.dashboard.models.Annotation;
import br.com.ottimizza.dashboard.models.QAnnotation;

@Repository
public class AnnotationRepositoryImpl implements AnnotationRepositoryCustom{
	
	@PersistenceContext
	EntityManager em;
	
	private QAnnotation annotation = QAnnotation.annotation;
	
	@Override
	public List<Annotation> findAnnotations(AnnotationDTO annotationDTO) {
		
		JPAQuery<Annotation> query = new JPAQuery<Annotation>(em).from(annotation);
		if(annotationDTO.getOrganizationId() != null)	query.where(annotation.organizationId.eq(annotationDTO.getOrganizationId()));
		if(annotationDTO.getCreateAt() != null)			query.where(annotation.createAt.eq(annotationDTO.getCreateAt()));
		if(annotationDTO.getCreatedBy() != null)		query.where(annotation.createdBy.eq(annotationDTO.getCreatedBy()));
		if(annotationDTO.getKpiAlias() != null)			query.where(annotation.kpiAlias.eq(annotationDTO.getKpiAlias()));
		if(annotationDTO.getDescription() != null)		query.where(annotation.description.eq(annotationDTO.getDescription()));
			
		return query.fetch();
	}    
}
