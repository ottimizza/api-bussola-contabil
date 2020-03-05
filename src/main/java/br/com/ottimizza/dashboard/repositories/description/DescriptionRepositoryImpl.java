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
import br.com.ottimizza.dashboard.models.QCompany;
import br.com.ottimizza.dashboard.models.QDescription;
import br.com.ottimizza.dashboard.utils.StringUtil;

@Repository
public class DescriptionRepositoryImpl implements DescriptionRepositoryCustom {
	
	@PersistenceContext
	EntityManager em;
	
//	@Inject
//    CompanyService service;
	
	private QDescription description = QDescription.description1;

	private QCompany company = QCompany.company;
			
	@Override
	public List<Description> findAll(DescriptionDTO filter) {

		JPAQuery<Description> query = new JPAQuery<Description>(em).from(description);
		
//		if (filter.getCnpj() != null) {
//			query.innerJoin(company).on(company.cnpj.eq(description.cnpj));
//			query.where(company.cnpj.eq(StringUtil.formatCnpj(filter.getCnpj())));
//		}

		if (filter.getKpiAlias() != null)		query.where(description.kpiAlias.eq(filter.getKpiAlias()));
		if (filter.getAccountingId() != null)	query.where(description.accountingId.eq(filter.getAccountingId()));
		if (filter.getScriptId() != null) 		query.where(description.scriptId.eq(filter.getScriptId()));

		if (filter.getId() != null)				query.where(description.id.eq(filter.getId()));
		if (filter.getDescription() != null) 	query.where(description.description.eq(filter.getDescription()));
		if (filter.getTitle() != null) 			query.where(description.title.eq(filter.getTitle()));
		if (filter.getGraphOrder() != null) 	query.where(description.graphOrder.eq(filter.getGraphOrder()));
		if (filter.getChartType() != null) 		query.where(description.chartType.eq(filter.getChartType()));
		
		if (filter.getVisible() != null) 		query.where(description.visible.eq(filter.getVisible())); 

		return query.orderBy(description.graphOrder.asc()).fetch();

	}

	@Override
	public Page<Description> findDescriptions(DescriptionDTO filter, Pageable pageable) {

		long totalDescriptions = 0;

		JPAQuery<Description> query = new JPAQuery<Description>(em).from(description);
		
		if (filter.getCnpj() != null) {
			query.innerJoin(company).on(company.cnpj.eq(description.cnpj));
			query.where(company.cnpj.eq(StringUtil.formatCnpj(filter.getCnpj())));
		}

		if (filter.getId() != null)				query.where(description.id.eq(filter.getId()));
		if (filter.getAccountingId() != null)	query.where(description.accountingId.eq(filter.getAccountingId()));
		if (filter.getKpiAlias() != null)		query.where(description.kpiAlias.eq(filter.getKpiAlias()));
		if (filter.getDescription() != null) 	query.where(description.description.eq(filter.getDescription()));
		if (filter.getScriptId() != null) 		query.where(description.scriptId.eq(filter.getScriptId()));
		if (filter.getTitle() != null) 			query.where(description.title.eq(filter.getTitle()));
		if (filter.getGraphOrder() != null) 	query.where(description.graphOrder.eq(filter.getGraphOrder()));
		if (filter.getChartType() != null) 		query.where(description.chartType.eq(filter.getChartType()));
		
		if (filter.getVisible() != null) 		query.where(description.visible.eq(filter.getVisible())); 

		totalDescriptions = query.fetchCount();
		query.limit(pageable.getPageSize());
		query.offset(pageable.getPageSize() * pageable.getPageNumber());

		return new PageImpl<Description>(query.orderBy(description.graphOrder.asc()).fetch(), pageable, totalDescriptions);
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
