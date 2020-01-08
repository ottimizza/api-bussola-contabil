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
	public List<VariableDTO> findVariablesByCompanyId(BigInteger companyId, UserDTO userInfo) {
		JPAQuery<VariableDTO> query = new JPAQuery<VariableDTO>(em).from(organizationVariable)
				.innerJoin(variable).on(
						variable.id.eq(organizationVariable.variableId).and(variable.accountingId.eq(userInfo.getOrganization().getId())))
				.where(organizationVariable.organizationId.eq(companyId));
		                
		query.select(Projections.constructor(
				VariableDTO.class, organizationVariable.id, variable.accountingId, variable.variableCode, variable.name,  
								   variable.id, organizationVariable.accountingCode, organizationVariable.organizationId));
		
		return query.fetch();
	}

	@Override
	public List<VariableDTO> findMissingByCompanyId(BigInteger companyId, UserDTO userInfo) {
		JPAQuery<VariableDTO> query = new JPAQuery<VariableDTO>(em).from(variable)
				.leftJoin(organizationVariable).on(
						organizationVariable.variableId.eq(variable.id).and(organizationVariable.organizationId.eq(companyId)))
				.where(organizationVariable.id.isNull().and(variable.accountingId.eq(userInfo.getOrganization().getId())));
				
		query.select(Projections.constructor(
				VariableDTO.class, organizationVariable.id, variable.accountingId, variable.variableCode, variable.name, variable.id, variable.scriptId, organizationVariable.organizationId));
//new VariableDTO(id, companyId, externalId, name, variableId, organizationId, accountingCode)
//new VariableDTO(id, companyId, variableCode, name, variableId, scriptId, organizationId)
		return query.fetch();
	}

}
