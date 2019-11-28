package br.com.ottimizza.dashboard.repositories.description;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQuery;

import br.com.ottimizza.dashboard.dtos.DescriptionDTO;
import br.com.ottimizza.dashboard.models.Description;
import br.com.ottimizza.dashboard.models.QDescription;

@Repository
public class DescriptionRepositoryImpl implements DescriptionRepositoryCustom {
	
	EntityManager em;
	private QDescription description = QDescription.description1;

	@Override
	public List<Description> findAll(DescriptionDTO descriptionDto) {
		
		JPAQuery<Description> query = new JPAQuery<Description>(em).from(description);
		if(descriptionDto.getId() != null)				query.where(description.id.eq(descriptionDto.getId()));
		if(descriptionDto.getKpiAlias() != null)		query.where(description.kpiAlias.eq(descriptionDto.getKpiAlias()));
		if(descriptionDto.getOrganizationId() != null)	query.where(description.organizationId.eq(descriptionDto.getOrganizationId()));
		if(descriptionDto.getDescription() != null)		query.where(description.description.eq(descriptionDto.getDescription()));
		
		return query.fetch();
	}
	
	
	

}
