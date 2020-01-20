package br.com.ottimizza.dashboard.repositories.organizationVariable;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;

import br.com.ottimizza.dashboard.domain.dtos.UserDTO;
import br.com.ottimizza.dashboard.domain.dtos.VariableDTO;
import br.com.ottimizza.dashboard.models.QOrganizationVariable;
import br.com.ottimizza.dashboard.models.QVariable;

@Repository
public class OrganizationVariableRepositoryImpl implements OrganizationVariableRepositoryCustom{

	@PersistenceContext
	EntityManager em;
	QVariable variable = QVariable.variable;
	QOrganizationVariable organizationVariable = QOrganizationVariable.organizationVariable;
	
	@Override
	public List<VariableDTO> findVariablesByCompanyId(VariableDTO filter, UserDTO userInfo) {
		JPAQuery<VariableDTO> query = new JPAQuery<VariableDTO>(em).from(organizationVariable)
				.innerJoin(variable).on(
						variable.id.eq(organizationVariable.variableId).and(variable.accountingId.eq(userInfo.getOrganization().getId())))
				.where(organizationVariable.organizationId.eq(filter.getCompanyId()));

		query.select(Projections.constructor(
				VariableDTO.class, organizationVariable.id, variable.accountingId, variable.variableCode, variable.name, variable.id, variable.scriptId, variable.originValue, variable.absoluteValue, 
								   organizationVariable.organizationId, organizationVariable.accountingCode));

		return query.fetch();
	}
	
	
//	@Override
//	public Page<VariableDTO> findVariablesByCompanyId(VariableDTO filter, Pageable pageable, UserDTO userInfo) {
//		long totalElements = 0;
//		JPAQuery<VariableDTO> query = new JPAQuery<VariableDTO>(em).from(organizationVariable)
//				.innerJoin(variable).on(
//						variable.id.eq(organizationVariable.variableId).and(variable.accountingId.eq(userInfo.getOrganization().getId())))
//				.where(organizationVariable.organizationId.eq(filter.getCompanyId()));
//
//		query.select(Projections.constructor(
//				VariableDTO.class, organizationVariable.id, variable.accountingId, variable.variableCode, variable.name, variable.id, variable.scriptId, variable.originValue, variable.absoluteValue, 
//								   organizationVariable.organizationId, organizationVariable.accountingCode));
//
//		totalElements = query.fetchCount();
//		query.limit(pageable.getPageSize());
//		query.offset(pageable.getPageSize() * pageable.getPageNumber());
//		
//		return new PageImpl<VariableDTO>(query.fetch(), pageable, totalElements);
//	}

	@Override
	public List<VariableDTO> findMissingByCompanyId(BigInteger companyId, UserDTO userInfo) {
		JPAQuery<VariableDTO> query = new JPAQuery<VariableDTO>(em).from(variable)
				.leftJoin(organizationVariable).on(
						organizationVariable.variableId.eq(variable.id).and(organizationVariable.organizationId.eq(companyId)))
				.where(organizationVariable.id.isNull().and(variable.accountingId.eq(userInfo.getOrganization().getId())));
				
		query.select(Projections.constructor(
				VariableDTO.class, organizationVariable.id, variable.accountingId, variable.variableCode, variable.name, variable.id, variable.scriptId, variable.originValue, variable.absoluteValue, 
								   organizationVariable.organizationId, variable.accountingCode));
		return query.fetch();
	}

	
}
