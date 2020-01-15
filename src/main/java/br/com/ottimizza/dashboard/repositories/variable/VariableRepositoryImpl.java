package br.com.ottimizza.dashboard.repositories.variable;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQuery;

import br.com.ottimizza.dashboard.domain.dtos.UserDTO;
import br.com.ottimizza.dashboard.domain.dtos.VariableDTO;
import br.com.ottimizza.dashboard.models.QOrganizationVariable;
import br.com.ottimizza.dashboard.models.QVariable;
import br.com.ottimizza.dashboard.models.Variable;

@Repository
public class VariableRepositoryImpl implements VariableRepositoryCustom {
	
	@PersistenceContext
	EntityManager em;
	QVariable variable = QVariable.variable;
	QOrganizationVariable organizationVariable = QOrganizationVariable.organizationVariable;
	
	
	@Override
	public Variable findVariableByAccountingCode(String accountingCode, UserDTO userInfo) {
		JPAQuery<Variable> query = new JPAQuery<Variable>(em).from(variable)
				.where(variable.accountingCode.eq(accountingCode).and(variable.accountingId.eq(userInfo.getOrganization().getId())));
				
		return query.fetchFirst();
	}
	
	@Override
	public List<Variable> findVariablesByOrganizationId(BigInteger organizationId, UserDTO userInfo) {
				
		JPAQuery<Variable> query = new JPAQuery<Variable>(em).from(variable)
				.where(variable.accountingId.eq(organizationId)/*.and(variable.accountingId.eq(userInfo.getOrganization().getId()))*/);

//		JPAQuery<VariableDTO> query = new JPAQuery<VariableDTO>(em).from(organizationVariable)
//				.innerJoin(variable).on(
//						variable.id.eq(organizationVariable.variableId).and(variable.accountingId.eq(userInfo.getOrganization().getId())))
//				.where(organizationVariable.organizationId.eq(organizationId));
		                
//		query.select(Projections.constructor(
//				VariableDTO.class, organizationVariable.id, variable.accountingId, variable.externalId, variable.name, variable.id, organizationVariable.organizationId, organizationVariable.accountingCode));
		
		
		return query.fetch();
	}

	@Override
	public Page<Variable> findVariableByOrganization(VariableDTO filter, Pageable pageable) {
		long totalElements = 0;
		JPAQuery<Variable> query = new JPAQuery<Variable>(em).from(variable);
		
		if(filter.getAccountingId() != null)query.where(variable.accountingId.eq(filter.getAccountingId()));
		if(filter.getScriptId() != null)	query.where(variable.scriptId.eq(filter.getScriptId()));
		
		totalElements = query.fetchCount();
		query.limit(pageable.getPageSize());
		query.offset(pageable.getPageSize() * pageable.getPageNumber());
		
		return new PageImpl<Variable>(query.fetch(), pageable, totalElements);
	}

}
