package br.com.ottimizza.dashboard.repositories.description;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQuery;

import br.com.ottimizza.dashboard.domain.dtos.DescriptionDTO;
import br.com.ottimizza.dashboard.models.Description;
import br.com.ottimizza.dashboard.models.QDescription;
import br.com.ottimizza.dashboard.utils.StringUtil;

@Repository
public class DescriptionRepositoryImpl implements DescriptionRepositoryCustom {
	
	@PersistenceContext
	EntityManager em;
	
	private QDescription description = QDescription.description1;

	@Override
	public List<Description> findAll(DescriptionDTO descriptionDto) {

		JPAQuery<Description> query = new JPAQuery<Description>(em).from(description);
		if(descriptionDto.getId() != null)				query.where(description.id.eq(descriptionDto.getId()));
		if(descriptionDto.getKpiAlias() != null)		query.where(description.kpiAlias.eq(descriptionDto.getKpiAlias()));
		if(descriptionDto.getAccountingId() != null)	query.where(description.accountingId.eq(descriptionDto.getAccountingId()));
		if(descriptionDto.getDescription() != null)		query.where(description.description.eq(descriptionDto.getDescription()));
//		inner
//		if(descriptionDto.getCnpj() != null)			query.where(company.cnpj.eq(descriptionDto.getCnpj()));

		return query.fetch();
	}

	@Override
	public Page<Description> findByAccountingIdScriptType(DescriptionDTO descriptionDTO, Pageable pageable) {

		JPAQuery<Description> query = new JPAQuery<Description>(em);
		query.from(description);

		long totalDescriptions = 0;

		if (descriptionDTO.getId() != null)				query.where(description.id.eq(descriptionDTO.getId()));
		if (descriptionDTO.getAccountingId() != null)	query.where(description.accountingId.eq(descriptionDTO.getAccountingId()));
		if (descriptionDTO.getKpiAlias() != null)		query.where(description.kpiAlias.eq(descriptionDTO.getKpiAlias()));
		if (descriptionDTO.getDescription() != null) 	query.where(description.description.eq(descriptionDTO.getDescription()));
		if (descriptionDTO.getScriptId() != null) 		query.where(description.scriptId.eq(descriptionDTO.getScriptId()));
		if (descriptionDTO.getTitle() != null) 			query.where(description.title.eq(descriptionDTO.getTitle()));
		if (descriptionDTO.getGraphOrder() != null) 	query.where(description.graphOrder.eq(descriptionDTO.getGraphOrder()));
		if (descriptionDTO.getChartType() != null) 		query.where(description.chartType.eq(descriptionDTO.getChartType()));
		if (descriptionDTO.getCnpj() != null) 			query.where(description.cnpj.eq(StringUtil.formatCnpj(descriptionDTO.getCnpj())));
		if (descriptionDTO.getVisible() != null) 		query.where(description.visible.eq(descriptionDTO.getVisible())); 

		totalDescriptions = query.fetchCount();
		query.limit(pageable.getPageSize());
		query.offset(pageable.getPageSize() *pageable.getPageNumber());

		return new PageImpl<Description>(query.fetch(), pageable, totalDescriptions);
	}	

	@Override
	public Description findByAccountingIdScriptType(DescriptionDTO descriptionDTO) {

		JPAQuery<Description> query = new JPAQuery<Description>(em);
		query.from(description);

		if (descriptionDTO.getAccountingId() != null)	query.where(description.accountingId.eq(descriptionDTO.getAccountingId()));
		if (descriptionDTO.getKpiAlias() != null)		query.where(description.kpiAlias.eq(descriptionDTO.getKpiAlias()));
		if (descriptionDTO.getScriptId() != null) 		query.where(description.scriptId.eq(descriptionDTO.getScriptId()));
		
		return query.fetchOne();
	}

}
