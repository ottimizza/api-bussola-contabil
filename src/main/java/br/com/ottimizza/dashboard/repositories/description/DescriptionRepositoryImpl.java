package br.com.ottimizza.dashboard.repositories.description;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQuery;

import br.com.ottimizza.dashboard.domain.dtos.DescriptionDTO;
import br.com.ottimizza.dashboard.models.Company;
import br.com.ottimizza.dashboard.models.Description;
import br.com.ottimizza.dashboard.models.QCompany;
import br.com.ottimizza.dashboard.models.QDescription;
import br.com.ottimizza.dashboard.services.CompanyService;
import br.com.ottimizza.dashboard.utils.StringUtil;

@Repository
public class DescriptionRepositoryImpl implements DescriptionRepositoryCustom {
	
	@PersistenceContext
	EntityManager em;
	
	@Inject
    CompanyService service;
	
	private QDescription description = QDescription.description1;

	private QCompany company = QCompany.company;
			
	@Override
	public List<Description> findAll(DescriptionDTO descriptionDto) {

		JPAQuery<Description> query = new JPAQuery<Description>(em).from(description);
		if(descriptionDto.getId() != null)				query.where(description.id.eq(descriptionDto.getId()));
		if(descriptionDto.getKpiAlias() != null)		query.where(description.kpiAlias.eq(descriptionDto.getKpiAlias()));
		if(descriptionDto.getAccountingId() != null)	query.where(description.accountingId.eq(descriptionDto.getAccountingId()));
		if(descriptionDto.getDescription() != null)		query.where(description.description.eq(descriptionDto.getDescription()));
		if(descriptionDto.getScriptId() != null)		query.where(description.scriptId.eq(descriptionDto.getScriptId()));
		
		return query.fetch();
	}

	@Override
	public Page<Description> findByAccountingIdScriptType(DescriptionDTO descriptionDTO, Pageable pageable) {

		JPAQuery<Description> query = new JPAQuery<Description>(em).from(description);
		
		if (descriptionDTO.getCnpj() != null) {
			query.innerJoin(company).on(company.cnpj.eq(description.cnpj));
		}
		
		Company company2 = new Company();
		long totalDescriptions = 0;

		if (descriptionDTO.getId() != null)				query.where(description.id.eq(descriptionDTO.getId()));
		if (descriptionDTO.getAccountingId() != null)	query.where(description.accountingId.eq(descriptionDTO.getAccountingId()));
		if (descriptionDTO.getKpiAlias() != null)		query.where(description.kpiAlias.eq(descriptionDTO.getKpiAlias()));
		if (descriptionDTO.getDescription() != null) 	query.where(description.description.eq(descriptionDTO.getDescription()));
		if (descriptionDTO.getScriptId() != null) 		query.where(description.scriptId.eq(descriptionDTO.getScriptId()));
		if (descriptionDTO.getTitle() != null) 			query.where(description.title.eq(descriptionDTO.getTitle()));
		if (descriptionDTO.getGraphOrder() != null) 	query.where(description.graphOrder.eq(descriptionDTO.getGraphOrder()));
		if (descriptionDTO.getChartType() != null) 		query.where(description.chartType.eq(descriptionDTO.getChartType()));
		
		if (descriptionDTO.getCnpj() != null) {
					
			try {
				company2 = service.findByCnpj(StringUtil.formatCnpj(descriptionDTO.getCnpj()));
				if(company2 != null) {
					query.where(description.scriptId.eq(company2.getScriptId()));
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			query.where(company.cnpj.eq(StringUtil.formatCnpj(descriptionDTO.getCnpj())));
		}
			
			//query.where(description.cnpj.eq(StringUtil.formatCnpj(descriptionDTO.getCnpj())));
		if (descriptionDTO.getVisible() != null) 		query.where(description.visible.eq(descriptionDTO.getVisible())); 

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
