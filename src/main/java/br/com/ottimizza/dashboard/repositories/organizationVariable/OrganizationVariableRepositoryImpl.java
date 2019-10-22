package br.com.ottimizza.dashboard.repositories.organizationVariable;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;

import br.com.ottimizza.dashboard.dtos.UserDTO;
import br.com.ottimizza.dashboard.dtos.VariableDTO;
import br.com.ottimizza.dashboard.models.QOrganizationVariable;
import br.com.ottimizza.dashboard.models.QVariable;

@Repository
public class OrganizationVariableRepositoryImpl implements OrganizationVariableRepositoryCustom{

	@PersistenceContext
	EntityManager em;
	QVariable variable = QVariable.variable;
	QOrganizationVariable organizationVariable = QOrganizationVariable.organizationVariable;
	
	@Override
	public List<VariableDTO> findVariablesByOrganizationId(BigInteger organizationId, UserDTO userInfo) {
				
		JPAQuery<VariableDTO> query = new JPAQuery<VariableDTO>(em).from(organizationVariable)
				.innerJoin(variable).on(
						variable.id.eq(organizationVariable.variableId).and(organizationVariable.organizationId.eq(userInfo.getOrganization().getId())))
				.where(organizationVariable.organizationId.eq(organizationId));
		                
		query.select(Projections.constructor(
				VariableDTO.class, variable.companyId, variable.externalId, variable.name, variable.id, organizationVariable.organizationId, organizationVariable.accountingCode));
		
		return query.fetch();
	}

	@Override
	public List<VariableDTO> findVariablesByCompanyId(BigInteger companyId, UserDTO userInfo) {
		JPAQuery<VariableDTO> query = new JPAQuery<VariableDTO>(em).from(organizationVariable)
				.innerJoin(variable).on(
						variable.id.eq(organizationVariable.variableId).and(organizationVariable.organizationId.eq(userInfo.getOrganization().getId())))
				.where(variable.companyId.eq(companyId));
		                
		query.select(Projections.constructor(
				VariableDTO.class, variable.companyId, variable.externalId, variable.name, variable.id, organizationVariable.organizationId, organizationVariable.accountingCode));
		
		return query.fetch();
	}

	@Override
	public List<VariableDTO> findMissingByOrganizationId(BigInteger organizationId, UserDTO userInfo) {
		JPAQuery<VariableDTO> query = new JPAQuery<VariableDTO>(em).from(organizationVariable)
				.leftJoin(variable).on(
						variable.id.ne(organizationVariable.variableId).and(organizationVariable.organizationId.eq(userInfo.getOrganization().getId())))
				.where(organizationVariable.organizationId.eq(organizationId));
		                
		query.select(Projections.constructor(
				VariableDTO.class, variable.companyId, variable.externalId, variable.name, variable.id, organizationVariable.organizationId, organizationVariable.accountingCode));
		
		return query.fetch();
	}
}
